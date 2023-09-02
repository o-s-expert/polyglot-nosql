# Cassandra Lab

In this lab session, we will explore Apache Cassandra by setting up a Docker Cassandra instance using the provided command. We will cover the following:

- Launching a Docker Cassandra Container
- Connecting to the Cassandra Cluster
- Creating a Keyspace and a Table
- Inserting and Querying Data


material-play-box-multiple-outline: Steps

**Step 1: Launching a Docker Cassandra Container**

Open your terminal and execute the following command to launch a Docker Cassandra instance:

```bash
docker run -d --name cassandra-instance -p 9042:9042 cassandra
```

This command creates a Docker container named "cassandra-instance" running the Cassandra image, maps port 9042 on your host to port 9042 inside the container, and runs Cassandra in the background.

**Step 2: Connecting to the Cassandra Cluster**

Now, let's connect to the Cassandra cluster running in the Docker container. You can use the Cassandra Query Language (CQL) shell, cqlsh, for this purpose. Run the following command in a new terminal window:

```bash
docker exec -it cassandra-instance cqlsh
```

This command starts the CQL shell and connects it to the Cassandra cluster within the Docker container.

**Step 3: Creating a Keyspace and a Table**

Once you're connected to the Cassandra cluster, let's create a keyspace named "people" and a table named "person." In the CQL shell, run the following commands:

```sql
CREATE KEYSPACE people WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};
USE people;

CREATE TABLE person (
    id UUID PRIMARY KEY,
    name TEXT,
    age INT
);
```

These commands create a keyspace named "people" and a table named "person" within that keyspace.

**Step 4: Inserting Data**

Now, let's insert some data into the "person" table. In the CQL shell, run the following commands:

```sql
INSERT INTO person (id, name, age) VALUES (uuid(), 'Alice', 30);
INSERT INTO person (id, name, age) VALUES (uuid(), 'Bob', 25);
INSERT INTO person (id, name, age) VALUES (uuid(), 'Charlie', 35);
```

These commands insert three rows of data into the "person" table.

**Step 5: Querying Data**

To retrieve and view the data from the "person" table, execute the following SELECT command in the CQL shell:

```sql
SELECT * FROM person;
```

This command will display the data you inserted in the "person" table.


## Cassandra Query Language (CQL) commands:

| Command                                       | Description                                           |
|-----------------------------------------------|-------------------------------------------------------|
| **CREATE KEYSPACE keyspace_name WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};** | Create a keyspace with specified replication settings. |
| **USE keyspace_name;**                        | Switch to a specific keyspace to perform operations within it. |
| **CREATE TABLE table_name (column_definitions PRIMARY KEY (primary_key_column));** | Create a table with defined columns and a primary key. |
| **INSERT INTO table_name (column1, column2, ...) VALUES (value1, value2, ...);** | Insert data into a table with specified column values. |
| **SELECT * FROM table_name;**                 | Retrieve all rows from a table. |
| **UPDATE table_name SET column1 = value1 WHERE primary_key_column = key_value;** | Update data in a table based on the primary key. |
| **DELETE FROM table_name WHERE primary_key_column = key_value;** | Delete data from a table based on the primary key. |
| **ALTER TABLE table_name ADD column_name datatype;** | Add a new column to an existing table. |
| **DESCRIBE keyspace_name;**                   | Display the schema and details of a keyspace. |
| **DESCRIBE table_name;**                      | Display the schema and details of a table. |
| **SHOW VERSION;**                             | Show the Cassandra version. |
| **BEGIN BATCH;**                             | Start a batch of CQL statements for atomic execution. |
| **APPLY BATCH;**                             | Execute a batch of CQL statements as a single atomic operation. |
| **QUIT;**                                      | Exit the CQL shell. |
