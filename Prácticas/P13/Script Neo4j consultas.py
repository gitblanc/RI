from neo4j import GraphDatabase
import logging
from neo4j.exceptions import ServiceUnavailable

class App:

    def __init__(self, uri, user, password):
        self.driver = GraphDatabase.driver(uri, auth=(user, password))

    def close(self):
        # Don't forget to close the driver connection when you are finished with it
        self.driver.close()

    # def create_friendship(self, person1_name, person2_name):
    #     with self.driver.session(database="neo4j") as session:
    #         # Write transactions allow the driver to handle retries and transient errors
    #         result = session.execute_write(
    #             self._create_and_return_friendship, person1_name, person2_name)
    #         for row in result:
    #             print("Created friendship between: {p1}, {p2}".format(p1=row['p1'], p2=row['p2']))

    # @staticmethod
    # def _create_and_return_friendship(tx, person1_name, person2_name):
    #     # To learn more about the Cypher syntax, see https://neo4j.com/docs/cypher-manual/current/
    #     # The Reference Card is also a good resource for keywords https://neo4j.com/docs/cypher-refcard/current/
    #     query = (
    #         "CREATE (p1:Person { name: $person1_name }) "
    #         "CREATE (p2:Person { name: $person2_name }) "
    #         "CREATE (p1)-[:KNOWS]->(p2) "
    #         "RETURN p1, p2"
    #     )
    #     result = tx.run(query, person1_name=person1_name, person2_name=person2_name)
    #     try:
    #         return [{"p1": row["p1"]["name"], "p2": row["p2"]["name"]}
    #                 for row in result]
    #     # Capture any errors along with the query and data for traceability
    #     except ServiceUnavailable as exception:
    #         logging.error("{query} raised an error: \n {exception}".format(
    #             query=query, exception=exception))
    #         raise

    # def find_person(self, person_name):
    #     with self.driver.session(database="neo4j") as session:
    #         result = session.execute_read(self._find_and_return_person, person_name)
    #         for row in result:
    #             print("Found person: {row}".format(row=row))

    # @staticmethod
    # def _find_and_return_person(tx, person_name):
    #     query = (
    #         "MATCH (p:Person) "
    #         "WHERE p.name = $person_name "
    #         "RETURN p.name AS name"
    #     )
    #     result = tx.run(query, person_name=person_name)
    #     return [row["name"] for row in result]

    @staticmethod
    def consultaSencilla1():
        query = (
            "match (a: Asignatura) <-[:CONTIENE]- (:Grado{nombre:\"Ingenieria Informatica\"}) "
            "return a"
        )
        result = tx.run 


if __name__ == "__main__":
    # Aura queries use an encrypted connection using the "neo4j+s" URI scheme
    uri = "neo4j+s://5ab21b39.databases.neo4j.io"
    user = "neo4j"
    password = "4P1E94MhfNkggY-cjeDQK1xsSzY6MvvbLyRvNPYnZ3Y"
    app = App(uri, user, password)
    # app.create_friendship("Alice", "David")
    # app.find_person("Alice")
    app.close()