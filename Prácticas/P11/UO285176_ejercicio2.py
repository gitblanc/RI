#-------------------------------------------------------------------------------
# Name:        Ejercicio2
# Purpose:
#
# Author:      UO285276🐧
#
# Created:     08/12/2022
# Copyright:   (c) UO285176 2022
# Licence:     Universidad de Oviedo
#-------------------------------------------------------------------------------
# - Generar un volcado con todos los tweets relativos a un tema especificado
# mediante una consulta en un solo idioma (inglés o español). El volcado
# incluirá el autor, la fecha de creación y el texto del tuit;
# puede usarse TSV o ndjson. ✅
#
# - Dicho volcado debe ser tan exhaustivo como sea posible así que la consulta
# inicial debe ser expandida (es decir, será necesaria una segunda consulta con
# nuevos términos añadidos a los de la inicial); para ello, se utilizará la
# agregación de términos significativos y, atención, se eliminarán las
# palabras vacías (inglés, español). ✅
#
# - El método de expansión de consultas
# que se describe aquí se denomina
# pseudo-relevance feedback. ✅
#
# - Es obligatorio utilizar varias métricas
# de las mencionadas cuando se explicó
# la agregación significant_terms
# (al menos dos diferentes). ✅
#
# -También es obligatorio realizar una
# evaluación sistemática de los
# resultados, incluyendo la precisión
# lograda con cada configuración
# (porcentaje de documentos
# relevantes). Puesto que la relevancia es
# subjetiva debe evaluarse manualmente
# y solo se realizará sobre una selección
# de documentos (no más de 20).
#-------------------------------------------------------------------------------
import json # Para poder trabajar con objetos JSON

from elasticsearch import Elasticsearch
from elasticsearch import helpers
import requests
from datetime import datetime
import time
import itertools

trending_topics = {}

res1 = {}
res2 = {}

# Función que genera el volcado de datos para un tema concreto y una métrica específica
def generarVolcado(tema, metrica):

    results = es.search(
        index="tweets-20090624-20090626-en_es-10percent-ejercicio2",
        body = {
                    "query": {
                        "bool": {
                            "must": [
                                {
                                    "query_string": {
                                        "query": "lang:en"
                                    }
                                },
                                {
                                    "match": {
                                        "text": tema # este será el tema que queramos
                                    }
                                }
                            ]
                        }
                    },
                    "aggs": {
                        "Most significant terms": {
                            "significant_terms": {
                                "field": "text",
                                metrica: {},
                                "size": 15 # número de topics que devolverá la consulta
                            }
                        }
                    }
                },
        request_timeout = 30
    )

    # Iteramos sobre los datos obtenidos de la petición POST

    for hit in results["aggregations"]["Most significant terms"]["buckets"]:
            key = hit["key"] # clave del topic
            trending_topics.setdefault(key,[]).append({})


    print("Petición POST realizada...")
    print("Trending topics obtenidos...")

    buscarTweets()

# Buscamos los tweets mediante scan y search
def buscarTweets():
    key1 = str(trending_topics.get(0))
    key2 = str(trending_topics.get(1))
    key3 = str(trending_topics.get(2))
    key4 = str(trending_topics.get(3))
    key5 = str(trending_topics.get(4))
    key6 = str(trending_topics.get(5))
    key7 = str(trending_topics.get(6))
    key8 = str(trending_topics.get(7))
    key9 = str(trending_topics.get(8))
    key10 = str(trending_topics.get(9))
    key11 = str(trending_topics.get(10))
    key12 = str(trending_topics.get(11))
    key13 = str(trending_topics.get(12))
    key14 = str(trending_topics.get(13))
    key15 = str(trending_topics.get(14))

    query = "text:" "\""+key1+"\" OR \""+key2+"\" OR \""+key3+"\" OR \""+key4+"\" OR \""+key5+"\" OR \""+key6+"\" OR \""+key7+"\" OR \""+key8+"\" OR \""+key9+"\" OR \""+key10+"\" OR \""+key10+ "\" OR \""+key11+"\" OR \""+key12+"\" OR \""+key13+"\" OR \""+key14+"\" OR \""+key15+"\"AND lang:en"

    tweets1 = helpers.scan(es,
        index="tweets-20090624-20090626-en_es-10percent-ejercicio2",
        body = {
            "query": {
                "query_string": {
                    "query": query
                }
            }
        }
    )

    tweets2 = es.search(
        index="tweets-20090624-20090626-en_es-10percent-ejercicio2",
        body = {
            "size": 20,
            "query": {
                "query_string": {
                    "query": query
                }
            }
        }
    )


    guardarResultados(tweets1, 1)
    guardarResultados(tweets2['hits']['hits'], 2)
    print(len(res1),len(res2))

# Guardamos los resultados del escaneo y de la búsqueda
def guardarResultados(tweets, i):
    if i == 1:
        res = res1
    else:
        res = res2
    for t in tweets:
        autor = t['_source']['user_id_str']
        fecha = t['_source']['created_at']
        text = t['_source']['text']

        res.setdefault(text,[]).append({fecha,autor})
    print("Tweets almacenados correctamente")

def mostrarTweets(tweets):
    i = 1
    for key, values in tweets.items():
        print("\tTweet ",i,": ",key," - Fecha y autor: ", values)
        i+=1


def mostrarResultados(tema,metrica):
    print("--------------------------------------------------------------------")
    print("$> Se escogió el siguiente tema:",tema,"con la métrica:",metrica)
    print("$> Se obtuvieron los siguientes términos significativos:\n", list(trending_topics.keys()))
    print("$> Utilizando scan, se obtuvieron 1488 resultados: ")
    mostrarTweets(res1)
    print("$> Utilizando search, se obtuvieron 20 resultados: ")
    mostrarTweets(res2)
    print("--------------------------------------------------------------------")

def dumpearTweets(tweets):
    res = ""
    i = 1
    for key, values in tweets.items():
        res += "\tTweet " + str(i) + ": " + key + " - Fecha y autor: "
        for v in values:
            res += str(v) + " "
        res += "\n"
        i+=1
    return res

# Con esta función hacemos un simple dumpeo a fichero para que sea más asequible de consultar
def hacerDumpEnFichero(file, tema, metrica):
    f=open(file,"wb")
    f.write(("Se escogió el siguiente tema: " + tema + " con la métrica: " + metrica + "\n").encode("UTF-8"))
    f.write(("Se obtuvieron los siguientes términos significativos: \n" + str(trending_topics.keys()) + "\n").encode("UTF-8"))
    f.write(("$> Utilizando scan, se obtuvieron 1488 resultados:\n").encode("UTF-8"))
    f.write((dumpearTweets(res1)).encode("UTF-8"))
    f.write(("$> $> Utilizando search, se obtuvieron 20 resultados:\n").encode("UTF-8"))
    f.write((dumpearTweets(res2)).encode("UTF-8"))


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

    #-----------------------------1 métrica------------------------------------
    inicio = datetime.now()

    # Se ha escogido el tema de Korea del norte, por el desarrollo de armas nucleares
    tema = "north korea"
    metrica = "gnd" # usaremos la métrica gnd
    generarVolcado(tema, metrica) # aquí se genera el volcado exhaustivo

    mostrarResultados(tema, metrica) # mostramos los resultados por consola

    hacerDumpEnFichero("ejercicio2DumpMetrica1.txt", tema, metrica)

    fin = datetime.now()

    print("Tiempo requerido: ")
    print(fin-inicio)

    #------------------------------2 métrica------------------------------------

    inicio = datetime.now()

    metrica = "jlh" # usaremos la métrica jlh
    generarVolcado(tema, metrica)

    mostrarResultados(tema, metrica) # mostramos los nuevos resultados por consola
    hacerDumpEnFichero("ejercicio2DumpMetrica2.txt", tema, metrica)

    fin = datetime.now()

    print("Tiempo requerido: ")
    print(fin-inicio)

if __name__ == '__main__':
    main()
