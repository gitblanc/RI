#-------------------------------------------------------------------------------
# Name:        Ejercicio1
# Purpose:
#
# Author:      UO285176🐧
#
# Created:     07/12/2022
# Copyright:   (c) UO285176 2022
# Licence:     Universidad de Oviedo
#
#-------------------------------------------------------------------------------
#
# - Genera un índice de tuits escritos en Español usando shingles de dos y tres
# términos. ✅
# - Escribe un script basado en el ejemplo de agregación que generaba “trending
# topics” y que pueda generar listas de 50 “trending topics” para cada hora en
# el dataset. ✅
# - El script debe procesar todos los “trending topics” para encontrar entidades
# asociadas a los mismos en Wikidata ✅
#-------------------------------------------------------------------------------

import json # Para poder trabajar con objetos JSON

from elasticsearch import Elasticsearch
from elasticsearch import helpers
import requests
from datetime import datetime
import time
import pickle

trending_topics = {} # diccionario de trending topics repetidos
trending_topics_sin_repetir = {} # diccionario de trending topics sin repetir


# Función que hace la petición POST a ES
def lanzarEscaneo():

    # Lanzamos la petición con la query correspondiente
    results = es.search(
        index="tweets-20090624-20090626-en_es-10percent-ejercicio1",
        body = {
                  "size": 0,
                  "query": {
                    "query_string": {
                      "query": "lang:es"
                    }
                  },
                  "aggs": {
                    "Trending topics per hour": {
                      "date_histogram": {
                        "field": "created_at",
                        "fixed_interval": "1h"
                      },
                      "aggs": {
                        "Trending topics": {
                          "significant_terms": {
                            "field": "text",
                            "size": 50,
                            "gnd": {}
                          }
                        }
                      }
                    }
                  }
                },
        request_timeout = 30
    )

    # Iteramos sobre los datos obtenidos de la petición POST

    for hit in results["aggregations"]["Trending topics per hour"]["buckets"]:
        for elem in hit["Trending topics"]["buckets"]:
            key = elem["key"] # clave del topic
            diaHora = hit["key_as_string"] # día del topic
            trending_topics.setdefault(key,[]).append({diaHora})
            if key not in trending_topics_sin_repetir: # Comprobamos que el topic no existiese previamente, para evitar duplicados
                trending_topics_sin_repetir.setdefault(key,[]).append({diaHora})


    print("Petición POST realizada...")
    print("Trending topics obtenidos...")


def buscarSinonimosWikidata():
    print("Buscando sinónimos para " + str(len(trending_topics_sin_repetir)) + " topics...")
    for key,value in trending_topics_sin_repetir.items():
        r = requests.get('https://www.wikidata.org/w/api.php?action=wbsearchentities&language=en&format=json&search=' + key)
        if r.status_code == 200:
            resultados = r.json()['search'] # Obtenemos todos los sinónimos
            aux = []
            for res in resultados:# los sinónimos que obtengasmos de wikidata
                aux.append(res['id'])
            trending_topics_sin_repetir[key] = aux
            time.sleep(2); # para durante 2 segundos entre petición y petición
        else:
            print("Ocurrió un error inesperado :(")
    print("Sinónimos:")


def mostrarDatosPorPantalla(dictionary):
    i = 1
    for topic,value in trending_topics.items():
        sameValue = dictionary[topic]
        print("$>",i,"|Topic:", topic,"- Date:",value,"- Sinónimos:",sameValue)
        i+=1


def main():

    # Password para el usuario 'elastic' generada por Elasticsearch
    #
    ELASTIC_PASSWORD = "oO0c9fuv6YGBBfWb=FHE"

    # Creamos el cliente y lo conectamos a nuestro servidor
    #
    global es

    es = Elasticsearch(
        "https://localhost:9200",
        ca_certs="./http_ca.crt",
        basic_auth=("elastic", ELASTIC_PASSWORD)
    )


    inicio = datetime.now()

    lanzarEscaneo() # Lanzamos el escaneo

    #buscarSinonimosWikidata() # Aquí obtenemos las entidades de cada término significativo

    # Guardamos los valores en un fichero externo para sólo hacer 1 vez las peticiones
    # --------------------------------------------------------------------------
    # Creamos un archivo pickle y dumpeamos todos los trending topics sin repetir
##    pickle_file = open('datos_ej1.pickle', 'wb')
##    pickle.dump(trending_topics_sin_repetir, pickle_file)
##    pickle_file.close()
    # --------------------------------------------------------------------------
    # Restauramos los datos pickled
    pickle_rest = open('datos_ej1.pickle', 'rb')
    trending_topics_sin_repetir = pickle.load(pickle_rest)
    # --------------------------------------------------------------------------


    mostrarDatosPorPantalla(trending_topics_sin_repetir) # Mostramos los resultados por consola


    fin = datetime.now()

    print("Tiempo requerido: ")
    print(fin-inicio)
    pickle_rest.close()

if __name__ == '__main__':
    main()
