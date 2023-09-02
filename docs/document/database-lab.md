# MongoDB Lab

In this lab session, we will explore MongoDB by setting up a Docker MongoDB instance using the provided command. We will cover the following:

1. Launching a Docker MongoDB Container
2. Connecting to the MongoDB Instance using the MongoDB Shell (mongosh)
3. Creating a Database and a Collection
4. Inserting and Querying Documents

material-play-box-multiple-outline: Steps

**Step 1: Launching a Docker MongoDB Container**

Open your terminal and execute the following command to launch a Docker MongoDB instance:

```bash
docker run -d --name mongodb-instance -p 27017:27017 mongo
```

This command creates a Docker container named "mongodb-instance" running the MongoDB image, mapping port 27017 on your host to port 27017 inside the container, and runs MongoDB in the background.

**Step 2: Connecting to the MongoDB Instance**

Now, let's connect to the MongoDB instance running in the Docker container using the MongoDB Shell (mongosh). In your terminal, run the following command:

```bash
docker exec -it mongodb-instance mongosh
```

This command starts the MongoDB Shell (mongosh) and connects it to the MongoDB instance within the Docker container.

**Step 3: Creating a Database and a Collection**

Once you're connected to MongoDB, let's create a database named "people" and a collection named "person." In the MongoDB Shell (mongosh), run the following commands:

```bash
use people; // Set the current database to "people"

db.createCollection("person"); // Create a collection named "person"
```

These commands create a database named "people" and a collection named "person" within that database.

**Step 4: Inserting and Querying Documents**

Now, let's insert some documents into the "person" collection and query them. In the MongoDB Shell (mongosh), run the following commands:

```bash
db.person.insertOne({ name: "Alice", age: 30 });
db.person.insertOne({ name: "Bob", age: 25 });
db.person.insertOne({ name: "Charlie", age: 35 });

db.person.find();
```

These commands insert three documents into the "person" collection and then retrieve all documents. You should see the documents you inserted displayed in the query results.


### MongoDB commands:


| Command                                       | Description                                           |
|-----------------------------------------------|-------------------------------------------------------|
| **use database_name;**                        | Switches to a specific database for operations. |
| **db.createCollection("collection_name");**    | Creates a new collection within the current database. |
| **db.collection_name.insertOne(document);**    | Inserts a single document into a collection. |
| **db.collection_name.insertMany(documents);**  | Inserts multiple documents into a collection. |
| **db.collection_name.find(query, projection);** | Retrieves documents from a collection based on a query. |
| **db.collection_name.updateOne(filter, update);** | Updates a single document in a collection that matches the filter. |
| **db.collection_name.updateMany(filter, update);** | Updates multiple documents in a collection that match the filter. |
| **db.collection_name.deleteOne(filter);**       | Deletes a single document from a collection that matches the filter. |
| **db.collection_name.deleteMany(filter);**     | Deletes multiple documents from a collection that match the filter. |
| **db.collection_name.aggregate(pipeline);**     | Performs aggregation operations on documents in a collection using a pipeline. |

