# Cassandra - Lab 1

In this lab session, we will explore Apache Cassandra by setting up a Docker Cassandra instance using the provided command. We will cover the following:

- Launching a Docker Cassandra Container
- Connecting to the Cassandra Cluster
- Creating a Keyspace and a Table
- Inserting and Querying Data


## 1. Starting Cassandra

### :material-play-box-multiple-outline: Steps

1.  Start up Docker
2.  Open your Terminal
3.  Execute the following command

    ```bash
    docker run -d --name cassandra-instance -p 9042:9042 cassandra
    ```

    !!! info

        This command creates a Docker container named "cassandra-instance" running the Cassandra image, maps port 9042 on your host to port 9042 inside the container, and runs Cassandra in the background.


### :material-checkbox-multiple-outline: Expected results

- The terminal will show the container ID

## 2. Connecting to the Cassandra Cluster

### :material-play-box-multiple-outline: Steps

1. Open the Terminal (or stay in the same Terminal window)
2. Execute the following command

   ```bash
   docker exec -it cassandra-instance cqlsh
   ```

### :material-checkbox-multiple-outline: Expected results

* The following information in the Terminal

    ```
    Connected to Test Cluster at 127.0.0.1:9042
    [cqlsh 6.1.0 | Cassandra 4.1.3 | CQL spec 3.4.6 | Native protocol v5]
    Use HELP for help.
    ```

## 3. Create a namespace and a table

### :material-play-box-multiple-outline: Steps

1. Create a namespace and a table where the namespace is called `people` and the table is nammed `person` by copying and pasting the following content in the CQL shell

    ```sql
    CREATE KEYSPACE people WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};
    USE people;

    CREATE TABLE person (
        id UUID PRIMARY KEY,
        name TEXT,
        age INT
    );
    ```

2. Run the folloiwing command in the CQL shell
   
    ```bash
    describe people
    ```

### :material-checkbox-multiple-outline: Expected results

* Namespace and table created, showing the following in the Terminal

    ```sql
    CREATE KEYSPACE people WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  AND durable_writes = true;

    CREATE TABLE people.person (
        id uuid PRIMARY KEY,
        age int,
        name text
    ) WITH additional_write_policy = '99p'
        AND bloom_filter_fp_chance = 0.01
        AND caching = {'keys': 'ALL', 'rows_per_partition': 'NONE'}
        AND cdc = false
        AND comment = ''
        AND compaction = {'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy', 'max_threshold': '32', 'min_threshold': '4'}
        AND compression = {'chunk_length_in_kb': '16', 'class': 'org.apache.cassandra.io.compress.LZ4Compressor'}
        AND memtable = 'default'
        AND crc_check_chance = 1.0
        AND default_time_to_live = 0
        AND extensions = {}
        AND gc_grace_seconds = 864000
        AND max_index_interval = 2048
        AND memtable_flush_period_in_ms = 0
        AND min_index_interval = 128
        AND read_repair = 'BLOCKING'
        AND speculative_retry = '99p';
    ```

## 4. Inserting data

### :material-play-box-multiple-outline: Steps

1. Run the following command in the CQL shell

    ```sql
    INSERT INTO person (id, name, age) VALUES (uuid(), 'Alice', 30);
    INSERT INTO person (id, name, age) VALUES (uuid(), 'Bob', 25);
    INSERT INTO person (id, name, age) VALUES (uuid(), 'Charlie', 35);
    ```

2. Retrieve data from the `person` table running the following command in the CQL shell

    ```sql
    SELECT * FROM person;
    ```

### :material-checkbox-multiple-outline: Expected results

* The `person` data with this similar output

    ```
     id                                  | age | name
    -------------------------------------+-----+---------
    4022c9af-4920-4787-8edb-8d1d8091ec30 |  30 |   Alice
    ec3e9568-901e-41d4-af09-cae644d7d1a3 |  25 |     Bob
    be51376c-d798-4234-ad8a-ba46e60b3131 |  35 | Charlie

    (3 rows)
    ```