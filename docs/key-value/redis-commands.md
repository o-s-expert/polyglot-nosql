## Handy Redis commands

The full list of commands can be found at [https://redis.io/commands](https://redis.io/commands)

| Command                     | Description                                           |
| --------------------------- | ----------------------------------------------------- |
| `SET key value`           | Set a key with a string value.                        |
| `GET key`                 | Retrieve the value associated with a key.             |
| `INCR key`                | Increment the integer value of a key by 1.            |
| `DECR key`                | Decrement the integer value of a key by 1.            |
| `DEL key`                 | Delete a key and its associated value(s).             |
| `KEYS pattern`            | Find all keys matching a specified pattern.           |
| `EXPIRE key seconds`      | Set an expiration time (in seconds) for a key.        |
| `TTL key`                 | Get the remaining time to live of a key (in seconds). |
| `HSET key field value`    | Set the field in a hash stored at a key to a value.   |
| `HGET key field`          | Retrieve the value of a field from a hash.            |
| `LPUSH key value [value]` | Insert one or more values at the head of a list.      |
| `RPUSH key value [value]` | Insert one or more values at the tail of a list.      |
| `LPOP key`                | Remove and return the first element from a list.      |
| `RPOP key`                | Remove and return the last element from a list.       |
