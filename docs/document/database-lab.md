# MongoDB - Lab 1

In this lab session, we will explore MongoDB by setting up a Docker MongoDB instance using the provided command. We will cover the following:

1. Launching a Docker MongoDB Container
2. Connecting to the MongoDB Instance using the MongoDB Shell (mongosh)
3. Creating a Database and a Collection
4. Inserting and Querying Documents


## 1. Starting MongoDB

### :material-play-box-multiple-outline: Steps

1.  Start up Docker
2.  Open your Terminal
3.  Execute the following command

    ```bash
    docker run -d --name mongodb-instance -p 27017:27017 mongo
    ```

    !!! info

        This command creates a Docker container named "mongodb-instance" running the MongoDB image, mapping port 27017 on your host to port 27017 inside the container, and running MongoDB in the background.

### :material-checkbox-multiple-outline: Expected results

- The terminal will show the container ID

## 2. Connecting to the MongoDB Instance

### :material-play-box-multiple-outline: Steps

1. Open the Terminal (or stay in the same Terminal window)
2. Execute the following command

   ```bash
   docker exec -it mongodb-instance mongosh
   ```

### :material-checkbox-multiple-outline: Expected results

* The following information in the Terminal

    ```
    Current Mongosh Log ID:	651983f1eda3dad76726cddf
    Connecting to:		URL to MongoDB
    Using MongoDB:		7.0.1
    Using Mongosh:		1.10.6
    ```

## 3. Create a database and a collection

### :material-play-box-multiple-outline: Steps

1. Create a database named `workspace` by running the following command in the MongoDB Shell

    ```bash
    use workplace;
    ```

2. Create a collection called `people` by running the following command in the MongoDB Shell

    ```bash
    db.createCollection("people");
    ```

### :material-checkbox-multiple-outline: Expected results

* Two logs in your Terminal related to the previous commands

    ```
    switched to db workplace;
    { ok: 1 }
    ```

## 4. Inserting and Querying Documents

### :material-play-box-multiple-outline: Steps

1. Insert the following documents to the `people` collection, using the MongoDB Shell

    ```bash
    db.people.insertOne({ name: "Alice", age: 30 });

    db.people.insertMany([
        { name: "Bob", age: 25 },
        { name: "Charlie", age: 35 }
    ]);
    ```

2. Query the documents, to prove that they were successfully inserted, by running the following command into the MondBD Shell

    ```bash
    db.people.find();
    ```

### :material-checkbox-multiple-outline: Expected results

* The following information in the Terminal

    ```json
    [
        { _id: ObjectId("651985c0eda3dad76726cde0"), name: 'Alice', age: 30 },
        { _id: ObjectId("651985c1eda3dad76726cde1"), name: 'Bob', age: 25 },
        {
            _id: ObjectId("651985c1eda3dad76726cde2"),
            name: 'Charlie',
            age: 35
        }
    ]
    ```
