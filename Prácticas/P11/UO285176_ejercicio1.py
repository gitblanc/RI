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
#-------------------------------------------------------------------------------

import json # Para poder trabajar con objetos JSON

from elasticsearch import Elasticsearch
from elasticsearch import helpers
import requests


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
        from datetime import datetime

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


def lanzarEscaneo():

    # Lanzamos el escaneo con la query correspondiente
    results = helpers.scan(es,
        index="tweets-20090624-20090626-en_es-10percent-ejercicio1",
        query =
                {
                  "size": 0,
                  "query": {
                    "query_string": {
                      "query": "\"farrah fawcett\" AND lang:es" # En esta query, puedes sustituir el
                                                                # término farrah fawcett por cualquier otro
                    }
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
                          },
                          "aggs": {
                            "Most significant terms": { # Sacamos los términos más significativos
                              "significant_terms": {
                                "field": "text"
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
    i= 0
    for hit in results:
        if i == 0:
            print(hit)
##            text = hit["_source"]["text"]
##            print(text)
            i+=1

    token = "michael jackson"
    # Hacemos la petición a WikiData en base al token (el término más significativo)
    r = requests.get('https://www.wikidata.org/w/api.php?action=wbsearchentities&language=en&format=json&search=' + token)
    if r.status_code == 200:
        print("JSON:")
        resultados = r.json()['search'] # Obtenemos todos los sinónimos

        for result in resultados:
            url = result['concepturi']
            print(url)
    else:
        print("Ocurrió un error inesperado :(")


##    f=open("ejercicio1.txt","wb")
##
##    # Iteramos sobre los resultados, no es preciso preocuparse de las
##    # conexiones consecutivas que hay que hacer con el servidor ES
##    for hit in results:
##        text = hit["_source"]["text"]
##
##        # Para visualizar mejor los tuits se sustituyen los saltos de línea
##        # por espacios en blanco *y* se añade un salto de línea tras cada tuit
##        text = text.replace("\n"," ")+"\n"
##        f.write(text.encode("UTF-8"))
##
##    f.close()
##
##    print("Comprueba el fichero ejercicio1.txt")


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

    lanzarEscaneo()

if __name__ == '__main__':
    main()
