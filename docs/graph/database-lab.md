# Neo4J - Lab 1

In this lab session, we will explore Neo4j by setting up a Docker Neo4j instance using the provided command. We will cover the following:

1. Launching a Docker Neo4j Container
2. Accessing the Neo4j Browser Interface
3. Creating a Sample Graph Database
4. Running Cypher Queries

## 1. Lauch a Docker Neo4j Container

### :material-play-box-multiple-outline: Steps

1.  Start up Docker
2.  Open your Terminal
3.  Execute the following command

    ```bash
    docker run --publish=7474:7474 --publish=7687:7687 --env NEO4J_AUTH=neo4j/admin123 neo4j
    ```

    !!! info

        This command creates a Docker container running the Neo4j image, maps ports 7474 and 7687 from the container to the host machine, and sets the initial authentication to "neo4j/admin123."


## 2. Access the Neo4j Browser Interface

### :material-play-box-multiple-outline: Steps

1. Open your web browser and navigate to [http://localhost:7474](http://localhost:7474)
2. Connect to the Neo4J using the following information:
    - Connect URL: `neo4j://neo4j://localhost:7687`
    - Database - leave empty for default: leave it empty
    - Authentication type: `Username / Password`
    - Username: `neo4j`
    - Password: `admin123`

## 2. Create a Sample Graph Database

### :material-play-box-multiple-outline: Steps

1. Create a simple graph database with nodes and relationships in the user interface
    - paste the content into the top section on your screen with start with `neo4j$`
    - hit the play button

        ```cypher
        CREATE (Alice:Person {name: "Alice", age: 30})
        CREATE (Bob:Person {name: "Bob", age: 25})
        CREATE (Charlie:Person {name: "Charlie", age: 35})
        CREATE (Alice)-[:FRIEND]->(Bob)
        CREATE (Alice)-[:FRIEND]->(Charlie)
        ``` 
### :material-checkbox-multiple-outline: Expected results

* The following information in the log

    ```
    Added 3 labels, created 3 nodes, set 6 properties, created 2 relationships, completed after 31 ms.
    ```

## 4. Run Cypher Queries

### :material-play-box-multiple-outline: Steps

1. Run the following Cypher query into the Neo4J interface

    ```
    MATCH (Alice:Person {name: "Alice"})-[:FRIEND]->(friend)
    RETURN Alice, friend
    ```

2. Click on the _Graph_ icon in the left page

This query retrieves Alice and her friends.


### :material-checkbox-multiple-outline: Expected results

* The following relationship between `Alice` and its friends

    ``` mermaid
    erDiagram
    Charlie ||--o{ Alice : friend
    Bob ||--o{ Alice : friend
    ```
