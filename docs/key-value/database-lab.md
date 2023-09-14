# Redis - Lab 1

In this lab session, we will explore Redis by setting up a Docker Redis instance using the provided command. We will cover the following:

- Launching a Docker Redis Instance
- Connecting to the Redis Server
- Basic Redis Commands

## 1. Starting Redis

### :material-play-box-multiple-outline: Steps

1.  Start up Docker
2.  Open your Terminal
3.  Execute the following command

    ```bash
    docker run --name redis-instance -p 6379:6379 -d redis
    ```

    !!! info

        This command creates a Docker container named "redis-instance" running the Redis image, maps port 6379 on your host to port 6379 inside the container, and runs Redis in the background.

### :material-checkbox-multiple-outline: Expected results

- The terminal will show the container ID

## 2. Connecting to the Redis Server

Now, let's connect to the Redis server running in the Docker container. You can use the Redis CLI or a Redis client library in your preferred programming language. For this lab, we'll use the Redis CLI.

### :material-play-box-multiple-outline: Steps

1. Open the Terminal (or stay in the same Terminal window)
2. Execute the following command

   ```bash
   docker exec -it redis-instance redis-cli
   ```

### :material-checkbox-multiple-outline: Expected results

- Connected to the Redis server where the command line will show `127.0.0.1:6379>`

## 3. Explore the basic Redis commands

### :material-play-box-multiple-outline: Steps

1. Execute the list of commands below, expecting its results

   | Description                    | Command                     | Result                    |
   | ------------------------------ | --------------------------- | ------------------------- |
   | Set a key-value pair           | `SET mykey "Hello, Redis!"` | OK                        |
   | Retrieve the value of a key    | `GET mykey`                 | "Hello, Redis!"           |
   | Increment a key's value        | `INCR mycounter`            | (integer) 1               |
   | Retrieve the incremented value | `GET mycounter`             | "1"                       |
   | List all keys in the database  | `KEYS *`                    | 1) "mycounter" 2) "mykey" |
