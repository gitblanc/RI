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

# Se ha escogido el tema de derailment and collision, por un grave accidente que ocurrió en Washington
tema = "derailment and collision"

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
        request_timeout = 30 # tiempo de espera de 500 segs
    )

    # Iteramos sobre los datos obtenidos de la petición POST

    for hit in results["aggregations"]["Most significant terms"]["buckets"]:
            key = hit["key"] # clave del topic
            trending_topics.setdefault(key,[]).append({})


    print("Petición POST realizada...")
    print("Trending topics obtenidos...")

    buscarTweets(metrica)

# Buscamos los tweets mediante scan y search
def buscarTweets(metrica):
    keys = list(trending_topics.keys())
    key1 = keys[0]
    key2 = keys[1]
    key3 = keys[2]
    key4 = keys[3]
    key5 = keys[4]
    key6 = keys[5]
    key7 = keys[6]
    key8 = keys[7]
    key9 = keys[8]
    key10 = keys[9]
    key11 = keys[10]
    key12 = keys[11]
    key13 = keys[12]
    key14 = keys[13]
    key15 = keys[14]

    query = "text: \\""\""+key1+"\\""\" OR \\""\""+key2+"\\""\" OR \\""\""+key3+"\\""\" OR \\""\""+key4+"\\""\" OR \\""\""+key5+"\\""\" OR \\""\""+key6+"\\""\" OR \\""\""+key7+"\\""\" OR \\""\""+key8+"\\""\" OR \\""\""+key9+"\\""\" OR \\""\""+key10+"\\""\" OR \\""\""+key11+ "\\""\" OR \\""\""+key12+"\\""\" OR \\""\""+key13+"\\""\" OR \\""\""+key14+"\\""\" OR \\""\""+key15+"\\""\" AND lang:en"


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

    guardarEnJSON(metrica, tweets1, tweets2) # aquí hacemos el volcado


def guardarEnJSON(metrica, tweets1, tweets2):

    files = ["Scan-dump-gnd.ndjson", "Scan-dump-chisquare.ndjson", "Search-dump-gnd.ndjson", "Search-dump-chisquare.ndjson"] # posibles ficheros de volcado
    file1 = ""
    file2 = ""
    if metrica == "gnd":
        file1 = files[0]
        file2 = files[2]
    else:
        file1 = files[1]
        file2 = files[3]

    f=open(file1,"wb")

   # Los metemos también en un JSON
    for hit in tweets1:
        text = hit["_source"]["text"]
        user = hit["_source"]["user_id_str"]
        fecha = hit['_source']['created_at']

        # Para visualizar mejor los tuits se sustituyen los saltos de línea
        # por espacios en blanco *y* se añade un salto de línea tras cada tuit
        text = "User: " + user + ", tweet: " + text.replace("\n"," ")+ ", Date: " + fecha +"\n"
        f.write(text.encode("UTF-8"))

    f.close()

    f=open(file2,"wb")
    for hit in tweets2['hits']['hits']:
        text = hit["_source"]["text"]
        user = hit["_source"]["user_id_str"]
        fecha = hit['_source']['created_at']

        # Para visualizar mejor los tuits se sustituyen los saltos de línea
        # por espacios en blanco *y* se añade un salto de línea tras cada tuit
        text = "User: " + user + ", tweet: " + text.replace("\n"," ")+ ", Date: " + fecha +"\n"
        f.write(text.encode("UTF-8"))

    f.close()

    mostrarResultados(file1, file2, metrica) # mostramos los resultados por consola


def mostrarResultados(file1, file2, metrica):
    print("--------------------------------------------------------------------")
    print("$> Se escogió el siguiente tema:",tema,"con la métrica:",metrica)
    print("$> Se obtuvieron los siguientes términos significativos:\n", list(trending_topics.keys()))
    print("$> Utilizando scan, se obtuvieron los resultados: ")

    f = open(file1, "r")
    for line in f: print(line.strip())
    f.close
    print("$> Utilizando search, se obtuvieron los resultados: ")
    f = open(file2, "rb")
    for line in f: print(line.strip())
    f.close
    print("--------------------------------------------------------------------")


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

    #-----------------------------1 métrica-------------------------------------

    inicio = datetime.now()

    metrica = "gnd" # usaremos la métrica gnd

    generarVolcado(tema, metrica) # aquí se genera el volcado exhaustivo

    fin = datetime.now()

    print("Tiempo requerido: ")
    print(fin-inicio)

    #------------------------------2 métrica------------------------------------

    inicio = datetime.now()

    metrica = "jlh" # usaremos la métrica chi_square

    generarVolcado(tema, metrica)

    fin = datetime.now()

    print("Tiempo requerido: ")
    print(fin-inicio)

if __name__ == '__main__':
    main()
