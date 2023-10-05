# Cypher commands

| Cypher Command                             | Description                                           |
|--------------------------------------------|-------------------------------------------------------|
| `MATCH (node:Label)-[:RELATIONSHIP]->(other) RETURN node, other;` | Retrieves nodes and relationships based on specified criteria, filtering and returning results. |
| `CREATE (node:Label {property: value});`  | Creates nodes with optional labels and properties. You can also create relationships between nodes in the same query. |
| `MERGE (node:Label {property: value});`  | Combines node creation and matching, ensuring that a node with the specified properties and labels either exists or is created. |
| `DELETE node;`                           | Removes nodes and their relationships from the graph. You can also use this command to delete relationships. |
| `SET node.property = value;`              | Sets or updates the value of a property on a node or relationship. |
| `RETURN DISTINCT node.property;`          | Returns distinct values of a specified property from the query results. |
| `ORDER BY node.property ASC/DESC;`       | Orders query results based on the specified property in ascending (ASC) or descending (DESC) order. |
| `LIMIT n;`                               | Limits the number of results returned by the query to the specified value (n). |
| `WITH node AS alias;`                    | Creates an alias for a node or a result set, allowing you to reference it in subsequent parts of the query. |
| `OPTIONAL MATCH (node)-[:RELATIONSHIP]->(other) RETURN node, other;` | Performs a match operation that doesn't require a match to exist, allowing you to retrieve optional relationships without affecting the main query. |
