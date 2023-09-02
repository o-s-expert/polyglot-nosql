# Neo4J Lab

In this lab session, we will explore Neo4j by setting up a Docker Neo4j instance using the provided command. We will cover the following:

1. Launching a Docker Neo4j Container
2. Accessing the Neo4j Browser Interface
3. Creating a Sample Graph Database
4. Running Cypher Queries

material-play-box-multiple-outline: Steps

**Step 1: Launching a Docker Neo4j Container**

Open your terminal and execute the following command to launch a Docker Neo4j instance:

```bash
docker run --publish=7474:7474 --publish=7687:7687 --env NEO4J_AUTH=neo4j/admin123 neo4j
```

This command creates a Docker container running the Neo4j image, maps ports 7474 and 7687 from the container to the host machine, and sets the initial authentication to "neo4j/admin123."

**Step 2: Accessing the Neo4j Browser Interface**

Once the container runs, open a web browser and navigate to `http://localhost:7474`. It will take you to the Neo4j Browser interface.

**Step 3: Creating a Sample Graph Database**

In the Neo4j Browser, you can run Cypher queries to create a sample graph database. For example, let's create a simple graph with nodes and relationships:

```cypher
CREATE (Alice:Person {name: "Alice", age: 30})
CREATE (Bob:Person {name: "Bob", age: 25})
CREATE (Charlie:Person {name: "Charlie", age: 35})
CREATE (Alice)-[:FRIEND]->(Bob)
CREATE (Alice)-[:FRIEND]->(Charlie)
```

These Cypher queries create three "Person" nodes with properties and two "FRIEND" relationships between them.

**Step 4: Running Cypher Queries**

Once you have created the sample graph, you can run Cypher queries to retrieve information. For instance, let's find friends of Alice:

```cypher
MATCH (Alice:Person {name: "Alice"})-[:FRIEND]->(friend)
RETURN Alice, friend
```

This query retrieves Alice and her friends.


## Cypher commands:

| Cypher Command                             | Description                                           |
|--------------------------------------------|-------------------------------------------------------|
| **MATCH (node:Label)-[:RELATIONSHIP]->(other) RETURN node, other;** | Retrieves nodes and relationships based on specified criteria, filtering and returning results. |

| **CREATE (node:Label {property: value});**  | Creates nodes with optional labels and properties. You can also create relationships between nodes in the same query. |

| **MERGE (node:Label {property: value});**  | Combines node creation and matching, ensuring that a node with the specified properties and labels either exists or is created. |

| **DELETE node;**                           | Removes nodes and their relationships from the graph. You can also use this command to delete relationships. |

| **SET node.property = value;**              | Sets or updates the value of a property on a node or relationship. |

| **RETURN DISTINCT node.property;**          | Returns distinct values of a specified property from the query results. |

| **ORDER BY node.property ASC/DESC;**       | Orders query results based on the specified property in ascending (ASC) or descending (DESC) order. |

| **LIMIT n;**                               | Limits the number of results returned by the query to the specified value (n). |

| **WITH node AS alias;**                    | Creates an alias for a node or a result set, allowing you to reference it in subsequent parts of the query. |

| **OPTIONAL MATCH (node)-[:RELATIONSHIP]->(other) RETURN node, other;** | Performs a match operation that doesn't require a match to exist, allowing you to retrieve optional relationships without affecting the main query. |

