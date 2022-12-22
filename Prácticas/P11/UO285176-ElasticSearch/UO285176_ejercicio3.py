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
# - ¿Se te ocurre alguna configuración para este tipo de consulta o una forma de
# producir una de esas consultas programáticamente de tal forma que se pueda
# emular la expansión de términos realizada en el ejercicio anterior?
#
# - Si se te ocurre describe cómo hacerlo y escribe una función en Python que
# reciba como parámetro una consulta en el formato aceptado por
# simple_query_string, use internamente una consulta MLT y proporcione
# resultados a la persona usuaria.
#-------------------------------------------------------------------------------

from elasticsearch import Elasticsearch

def moreLikeThis(doc_id):

    index = 'tweets-20090624-20090626-en_es-10percent-ejercicio2'  # El índice a utilizar
    query = {
      "query": {
        "more_like_this": {
          "fields": ["id_str", "text"],  # Los campos a usar por la query MLT
          "like": [
            {
              "_id": doc_id
            }
          ],
          "min_term_freq": 1,  # Mínimo número de veces que el término aparece en el documento
          "min_doc_freq": 1,  # Mínimo número de documentos en los que el término aparece
          "max_query_terms": 25  # Máximo número de términos a incluír en la query MLT
        }
      }
    }

    res = es.search(index=index, body=query)

    # Mostramos los resultados
    i = 1
    for hit in res['hits']['hits']:
        print("$>",i,"Date:",hit['_source']['created_at'],"-",hit['_source']['text'])
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

    ids = "2302882831"

    moreLikeThis(ids) # aquí hacemos la consulta, a la que le pasamos el id del documento
                     # del que queremos que encuentre resultados similares

if __name__ == '__main__':
    main()