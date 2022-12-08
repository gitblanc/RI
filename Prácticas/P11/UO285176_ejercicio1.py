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

terminos_significativos = []
entidades = []


# Aquí indexaremos los documentos en bloques
def procesarLineas(lineas):
  jsonvalue = []

  for linea in lineas:
    datos = json.loads(linea) # Para acceder al diccionario

    ident = datos["id_str"]

    if datos["lang"]=="es": # Comprobamos que estén escritos en español
        datos["_index"] = "tweets-20090624-20090626-en_es-10percent-ejercicio1"
        datos["_id"] = ident

        jsonvalue.append(datos)

  num_elementos = len(jsonvalue)
  resultado = helpers.bulk(es,jsonvalue,chunk_size=num_elementos,request_timeout=200)
  # Habría que procesar el resultado para ver que todo vaya bien...

  global contador

  contador += num_elementos
  print(contador)


# Aquí generaremos el índice de tuits en Español con shingles de 2 y 3 términos
def generarIndice():

        inicio = datetime.now()
        # Creamos el índice
        #
        # Si no se crea explícitamente se crea al indexar el primer documento
        #
        # Debemos crearlo puesto que el mapeado por defecto (mapping) de algunos
        # campos, no es satisfactorio.
        #
        # ignore=400 hace que se ignore el error de índice ya existente
        #

        argumentos={
            "settings": {
                "analysis": {
                    "filter": {
                        "bi_tri_gramas": { # shingles de 2 y 3 términos
                            "type":"shingle",
                            "min_shingle_size":2,
                            "max_shingle_size":3
                        },
                        "estematizacion_spanish": { # estematización en Español
                            "type":"stemmer",
                            "name":"spanish"
                        }
                    },
                    "analyzer": {
                        "analizador_personalizado": {
                            "tokenizer":"standard",
                            "filter":["lowercase","estematizacion_spanish","bi_tri_gramas"]
                        }
                    }
                }
            },
            "mappings": {
                "properties": {
                    "created_at": {
                      "type":"date",
                      "format": "EEE MMM dd HH:mm:ss Z yyyy"
                    },
                    "text": {
                        "type":"text",
                        "analyzer":"analizador_personalizado",
                        "fielddata": "true"
                    }
                }
            }
        }

        es.indices.create(index="tweets-20090624-20090626-en_es-10percent-ejercicio1",ignore=400,body=argumentos)

        # Ahora se indexan los documentos.
        # Leemos el fichero en grandes bloques
        #
        global contador
        contador = 0

        tamano = 40*1024*1024 # Para leer 40MB, tamaño estimado de manera experimental
        fh = open("tweets-20090624-20090626-en_es-10percent.ndjson", 'rt')
        lineas = fh.readlines(tamano)
        while lineas:
          procesarLineas(lineas)
          lineas = fh.readlines(tamano)
        fh.close()

        fin = datetime.now()

        print(fin-inicio)

# Función que hace la petición POST a ES con el término que le pases
def lanzarEscaneo(termino):

    # Lanzamos la petición con la query correspondiente
    results = es.search(
        index="tweets-20090624-20090626-en_es-10percent-ejercicio1",
        body = {
                  "size": 0,
                  "query": {
                    "query_string": {
                      "query": "\"" + termino + "\" AND lang:es"
                    }
                  },
                  "aggs": {
                    "Most significant terms": { # Sacamos los términos más significativos
                        "significant_terms": {
                            "field": "text"
                        },
                        "aggs": {
                        "Trending topics por hora": {
                          "date_histogram": {
                            "field": "created_at",
                            "fixed_interval": "1h" # Intervalo de 1h
                          },
                          "aggs": {
                            "Trending topics": {
                              "significant_terms": {
                                "field": "text",
                                "size": 50, # Tamaño de la lista de 50 trending topics
                                "gnd": {}
                              }
                            }
                          }
                        }
                      }
                    }
                }
        }
    )

    # Iteramos sobre los datos obtenidos de la petición POST

    for hit in results["aggregations"]["Most significant terms"]["buckets"]:
        for topic in hit["Trending topics por hora"]["buckets"]:
            for key in topic["Trending topics"]["buckets"]:
                if existeEnLaColeccion(key["key"]) == False:
                    # vamos almacenando tuplas con los términos más significativos si no estaban ya almacenados y sus horas
                    terminos_significativos.append((key["key"],topic["key_as_string"]))
    print("Petición POST realizada...")


def existeEnLaColeccion(key):
    for term in terminos_significativos:
        # if term == key or key in term: # Opción 1
          if term == key: # Opción 2
            return True # ya existe
    return False


def buscarSinonimosWikidata():
    print("Esperado = " + str(len(terminos_significativos)*5) + " resultados o menor...")

    for tk in terminos_significativos:
        token = tk[0]
        # Hacemos la petición a WikiData en base al token (el término más significativo)
        r = requests.get('https://www.wikidata.org/w/api.php?action=wbsearchentities&language=en&format=json&search=' + token)
        if r.status_code == 200:
            resultados = r.json()['search'] # Obtenemos todos los sinónimos

            for result in resultados: # obtenemos las entidades de cada uno de los sinónimos
                entity = result['id']
                entidades.append((tk,entity))

        else:
            print("Ocurrió un error inesperado :(")
    print("Entidades cargadas...")

def mostrarDatosPorPantalla():
    i = 0
    print("     Término\t\t\tHora\t\t\t\t\t\tEntidad")
    for en in entidades:
        print("$> " + str(i) + " " + str(en[0][0]) + " - " + str(en[0][1]) + " - " + str(en[1]))
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

    # generarIndice() # <- llamar sólo 1 vez

    inicio = datetime.now()

    lanzarEscaneo("farrah fawcett") # Aquí le pasamos el termino a buscar
    buscarSinonimosWikidata() # Aquí obtenemos las entidades de cada término significativo

    fin = datetime.now()

    mostrarDatosPorPantalla() # Mostramos los resultados por consola

    print("Tiempo requerido: ")
    print(fin-inicio)

if __name__ == '__main__':
    main()
