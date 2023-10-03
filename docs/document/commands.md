# MongoDB commands


| Command                                          | Description                                                                    |
|--------------------------------------------------|--------------------------------------------------------------------------------|
| `use database_name;`                             | Switches to a specific database for operations.                                |
| `db.createCollection("collection_name");`        | Creates a new collection within the current database.                          |
| `db.collection_name.insertOne(document);`        | Inserts a single document into a collection.                                   |
| `db.collection_name.insertMany(documents);`      | Inserts multiple documents into a collection.                                  |
| `db.collection_name.find(query, projection);`    | Retrieves documents from a collection based on a query.                        |
| `db.collection_name.updateOne(filter, update);`  | Updates a single document in a collection that matches the filter.             |
| `db.collection_name.updateMany(filter, update);` | Updates multiple documents in a collection that match the filter.              |
| `db.collection_name.deleteOne(filter);`          | Deletes a single document from a collection that matches the filter.           |
| `db.collection_name.deleteMany(filter);`         | Deletes multiple documents from a collection that match the filter.            |
| `db.collection_name.aggregate(pipeline);`        | Performs aggregation operations on documents in a collection using a pipeline. |

