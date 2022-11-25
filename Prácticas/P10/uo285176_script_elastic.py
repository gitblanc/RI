# UO285176
import json # Para poder trabajar con objetos JSON

from elasticsearch import Elasticsearch
from elasticsearch import helpers

def main():
    # Password para el usuario 'lectura' asignada por nosotros
    #
    READONLY_PASSWORD = "abretesesamo"

    # Creamos el cliente y lo conectamos a nuestro servidor
    #
    global es

    es = Elasticsearch(
        "https://localhost:9200",
        ca_certs="./http_ca.crt",
        basic_auth=("lectura", READONLY_PASSWORD)
    )

    # DOCUMENTATION OBTAINED from -> https://elasticsearch-py.readthedocs.io/en/v8.4.3/helpers.html#scan

    results = es.search(
        index="tweets-20090624-20090626-en_es-10percent",
        body = {
                "size" : 0,
                "track_total_hits" : "true",
                "query" : {
                    "query_string" : {
                        "query": "text:iran AND lang:en"
                    }
               }
        }
    )

    print(str(results["hits"]["total"]) + " resultados para la query \"text:iran AND lang:en\" enviada como una request.")

    ''' MANERA 1
    scanResults = es.search(
        index="tweets-20090624-20090626-en_es-10percent",
        search_type="scan",
        scroll="10m"
    )
    scrollId = scanResults["_scroll_id"]

    response = es.scroll(scroll_id=scrollId, scroll="1m")
    print(response)
    '''

    '''MANERA 2
    '''
    q = {"query" : {"query_string" : {"query": "text:iran AND lang:en"}}}

    scanResult = helpers.scan(client=es,
     index="tweets-20090624-20090626-en_es-10percent",
     query=q, scroll="5m",
     preserve_order=True)

    i = 0
    for item in scanResult:
        #print(item)
        #print("Id: " + item["_id"] + ", Score: " + str(item["_score"]))
        print("Element: ", item["_source"])
        i+=1
    print("Results: " + str(i))


if __name__ == '__main__':
    main()
