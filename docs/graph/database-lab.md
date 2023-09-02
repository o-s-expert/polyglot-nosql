# Redis Lab

In this lab session, we will explore Redis by setting up a Docker Redis instance using the provided command. We will cover the following:

- Launching a Docker Redis Instance
- Connecting to the Redis Server
- Basic Redis Commands


material-play-box-multiple-outline: Steps

**Step 1: Launching a Docker Redis Instance**
Open your terminal and execute the following command to launch a Docker Redis instance:

```bash
docker run --name redis-instance -p 6379:6379 -d redis
```

This command creates a Docker container named "redis-instance" running the Redis image, maps port 6379 on your host to port 6379 inside the container, and runs Redis in the background.

**Step 2: Connecting to the Redis Server**
Now, let's connect to the Redis server running in the Docker container. You can use the Redis CLI or a Redis client library in your preferred programming language. For this lab, we'll use the Redis CLI.

Open a new terminal window and run the following command to start the Redis CLI and connect to the Docker Redis instance:

```bash
docker exec -it redis-instance redis-cli
```

You are now connected to the Redis server within the Docker container.

**Step 3: Basic Redis Commands**
Now that you are connected to the Redis server, let's explore some basic Redis commands:

- Set a key-value pair:

  ```bash
  SET mykey "Hello, Redis!"
  ```

- Retrieve the value of a key:
  ```bash
  GET mykey
  ```

- Increment a key's value:
  ```bash
  INCR mycounter
  ```

- Retrieve the incremented value:
  ```bash
  GET mycounter
  ```

- List all keys in the database:
  ```bash
  KEYS *
  ```


### Explore commands:

| Command                   | Description                                           |
|---------------------------|-------------------------------------------------------|
| **SET key value**         | Set a key with a string value.                       |
| **GET key**               | Retrieve the value associated with a key.            |
| **INCR key**              | Increment the integer value of a key by 1.          |
| **DECR key**              | Decrement the integer value of a key by 1.          |
| **DEL key**               | Delete a key and its associated value(s).            |
| **KEYS pattern**          | Find all keys matching a specified pattern.          |
| **EXPIRE key seconds**    | Set an expiration time (in seconds) for a key.       |
| **TTL key**               | Get the remaining time to live of a key (in seconds).|
| **HSET key field value**  | Set the field in a hash stored at a key to a value.  |
| **HGET key field**        | Retrieve the value of a field from a hash.           |
| **LPUSH key value [value]** | Insert one or more values at the head of a list.  |
| **RPUSH key value [value]** | Insert one or more values at the tail of a list.  |
| **LPOP key**              | Remove and return the first element from a list.     |
| **RPOP key**              | Remove and return the last element from a list.      |

