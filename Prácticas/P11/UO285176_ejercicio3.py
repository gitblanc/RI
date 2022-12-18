#-------------------------------------------------------------------------------
# Name:        Ejercicio3
# Purpose:
#
# Author:      UO285276🐧
#
# Created:     08/12/2022
# Copyright:   (c) UO285176 2022
# Licence:     Universidad de Oviedo
#-------------------------------------------------------------------------------

from elasticsearch import Elasticsearch

def moreLikeThis(query):

    results = es.search(
        index="tweets-20090624-20090626-en_es-10percent-ejercicio2",
        body = {
            "query": {
                "more_like_this": {
                    "fields": ["text"], # aquí metemos campos a buscar con la MLT query
                    "like": query, # el texto para encontrar documentos similares
                    "min_term_freq": 1,
                    "max_query_terms": 25
                }
            }
        }
    )
    i = 0
    for r in results["hits"]["hits"]:
        print("$>",i," ",r["_source"])
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

    moreLikeThis("flight 447") # aquí hacemos la consulta, a la que le pasamos la frase
                     # de lo que queremos que encuentre resultados similares

if __name__ == '__main__':
    main()