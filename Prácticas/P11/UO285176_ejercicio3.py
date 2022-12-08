#-------------------------------------------------------------------------------
# Name:        Ejercicio3
# Purpose:
#
# Author:      UO285176🐧
#
# Created:     08/12/2022
# Copyright:   (c) UO285176 2022
# Licence:     Universidad de Oviedo
#-------------------------------------------------------------------------------

import json # Para poder trabajar con objetos JSON

from elasticsearch import Elasticsearch
from elasticsearch import helpers
import requests
from datetime import datetime


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





if __name__ == '__main__':
    main()
