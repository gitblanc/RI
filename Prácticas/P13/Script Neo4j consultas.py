from neo4j import GraphDatabase
import logging
from neo4j.exceptions import ServiceUnavailable

class App:

    def __init__(self, uri, user, password):
        self.driver = GraphDatabase.driver(uri, auth=(user, password))


    def close(self):
        # Don't forget to close the driver connection when you are finished with it
        self.driver.close()


    def consultas(self):
        with self.driver.session(database="neo4j") as session:
            #------------------------Apartado a)-----------------------------
            result = session.execute_read(self.consultaSencilla1) # consulta 1
            print("Consulta Sencilla 1: ",result)
            result = session.execute_read(self.consultaSencilla2) # consulta 2
            print("Consulta Sencilla 2: ",result)
            #------------------------Apartado b)-----------------------------
            result = session.execute_read(self.consultaIntermedia1) # consulta 1
            print("Consulta Intermedia 1: ",result)
            result = session.execute_read(self.consultaIntermedia2) # consulta 2
            print("Consulta Intermedia 2: ",result)
            #------------------------Apartado c)-----------------------------
            result = session.execute_read(self.consultaAvanzada1) # consulta 1
            print("Consulta Avanzada 1: ",result)
            # result = session.execute_read(self.consultaAvanzada2) # consulta 2
            # print("Consulta Avanzada 2: ",result)


    def procesarNodos(nodes):
        res = []
        for x in nodes:
            res.append(x[0])
        return res

    @staticmethod
    def consultaSencilla1(tx):
        query = (
            "match (a: Asignatura) <-[:CONTIENE]- (:Grado{nombre:\"Ingenieria Informatica\"}) "
            "return a.nombre "
        )
        nodes = tx.run(query)
        return App.procesarNodos(nodes)


    @staticmethod
    def consultaSencilla2(tx):
        query = (
            "match (p:Persona)-[:ESTA_APUNTADO]->(a:Asignatura{nombre:\"Algebra\"}) "
            "return p.nombre "
        )
        nodes = tx.run(query)
        return App.procesarNodos(nodes)


    @staticmethod
    def consultaIntermedia1(tx):
        query = (
            "match (a: Asignatura) <- [:CONTIENE] - (g:Grado) "
            "where g.localizacion <> \"Oviedo\" and a.nombre = \"Calculo\" "
            "match (a) <- [:ESTA_APUNTADO] - (p:Persona) "
            "return distinct(p.nombre) "
        )
        nodes = tx.run(query)
        return App.procesarNodos(nodes)


    @staticmethod
    def consultaIntermedia2(tx):
        query = (
            "MATCH (p:Persona)-[:IMPARTE]->(s:Asignatura) "
            "OPTIONAL MATCH (p)-[:DIRIGE]->(g:Grado) "
            "WITH p, COUNT(g) AS contador "
            "WHERE contador = 0 "
            "RETURN p.nombre "
        )
        nodes = tx.run(query)
        return App.procesarNodos(nodes)


    @staticmethod
    def consultaAvanzada1(tx):
        query = (
            "MATCH (g:Grado) "
            "WHERE EXISTS{ "
            "MATCH (g)<-[:DIRIGE]-(p:Persona) "
            "WHERE p.edad=\"40\" AND EXISTS { "
            "MATCH (g)-[:CONTIENE]->(a:Asignatura) "
            "where a.rama=\"Ingenieria\" "
            "} "
            "} "
            "RETURN  g.nombre "
        )
        nodes = tx.run(query)
        return App.procesarNodos(nodes)


    @staticmethod
    def consultaAvanzada2(tx):
        query = (
            "falta"
        )
        nodes = tx.run(query)
        return App.procesarNodos(nodes)


if __name__ == "__main__":
    # Aura queries use an encrypted connection using the "neo4j+s" URI scheme
    uri = "neo4j+s://c4d04a65.databases.neo4j.io"
    user = "neo4j"
    password = "iS6unODm5uW_20BFiogMwGR93P_M4kcTxKNyKNz0_cw"
    app = App(uri, user, password)
    app.consultas() # aquí se ejecutan las consultas
    app.close()