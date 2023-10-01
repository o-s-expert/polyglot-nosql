# Cassandra Query Language (CQL) commands:

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
