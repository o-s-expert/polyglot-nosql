# Redis and Java Lab



## 1. The fisrt integration

This lab will introduce the first integration between Java and Redis using Eclipse JNoSQL.

material-play-box-multiple-outline: Steps

**Step 1: Create the `App` Class**

- Define the `App` class:

```java
public class App {
```


**Step 2: Import Required Packages**

- Import necessary packages and classes including Jakarta EE and Jakarta NoSQL:

```java
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.eclipse.jnosql.communication.keyvalue.BucketManagerFactory;
import org.eclipse.jnosql.databases.redis.communication.RedisBucketManagerFactory;

import java.util.List;
import java.util.Set;
```



**Step 3: Implement the `main` Method**

- Implement the `main` method inside the `App` class:

```java
    public static void main(String[] args) {
```

**Step 4: Try-With-Resources Block**

- Set up a try-with-resources block to manage the Jakarta EE `SeContainer`. The `SeContainer` is responsible for dependency injection and managing resources:

```java
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
```

**Step 5: Obtain a `BucketManagerFactory`**

- Obtain an instance of the `BucketManagerFactory` (specifically, `RedisBucketManagerFactory`) using Jakarta EE's dependency injection. The `select` method is used to select and retrieve the factory:

```java
BucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();
```

**Step 6: Work with Lists and Sets**

- Use the `BucketManagerFactory` to create and interact with data structures in Redis. In this case, a Redis list named "names" and a Redis set named "fruits" are created and retrieved:

```java
    List<String> names = factory.getList("names", String.class);
    Set<String> fruits = factory.getSet("fruits", String.class);
```

**Step 7: Add Data to Lists and Sets**

- Add data to the Redis list and set. In this example, names and fruits are added to the respective data structures:

```java
names.addAll(List.of("Otavio", "Elias", "Ada", "Poliana", "Otavio"));
fruits.addAll(List.of("Banana", "Banana", "Apple", "Watermelon", "Banana", "Apple"));
```

**Step 8: Print Data**

- Print the data stored in the "names" and "fruits" data structures:

```java
System.out.println("Names: ");
names.forEach(System.out::println);
System.out.println("Fruits: ");
fruits.forEach(System.out::println);
```

**Step 9: Close the `SeContainer`**

- Close the `SeContainer` and release any resources used by the application:

```java
        }
    }
```

**Step 10: Private Constructor**

- Define a private constructor for the `App` class to prevent instantiation since it contains only static methods:

```java
    private App() {
    }
}
```

This lab demonstrates how to use Jakarta EE with Jakarta NoSQL to interact with a Redis database, create and work with lists and sets, and manage resources using Jakarta EE's `SeContainer`.

### :material-check-outline: Solution

??? example "Click to see..."

```java


import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.eclipse.jnosql.communication.keyvalue.BucketManagerFactory;
import org.eclipse.jnosql.databases.redis.communication.RedisBucketManagerFactory;

import java.util.List;
import java.util.Set;

public class App {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            BucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();
            List<String> names = factory.getList("names", String.class);
            Set<String> fruits = factory.getSet("fruits", String.class);

            names.addAll(List.of("Otavio", "Elias", "Ada", "Poliana", "Otavio"));

            fruits.addAll(List.of("Banana", "Banana", "Apple", "Watermelon", "Banana", "Apple"));

            System.out.println("Names: ");
            names.forEach(System.out::println);
            System.out.println("Fruits: ");
            fruits.forEach(System.out::println);


        }
    }

    private App() {
    }
}
```

## 2. Redis Queue

This lab will introduce the integration with Redis and a Queue.

material-play-box-multiple-outline: Steps

Certainly, here's a step-by-step guide for the student to understand the `App2` class:

**Step 1: Create the `App2` Class**

- Define the `App2` class:

```java
public class App2 {
```

**Step 2: Import Required Packages**

- Import necessary packages and classes including Jakarta EE and Jakarta NoSQL:

```java
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.eclipse.jnosql.communication.keyvalue.BucketManagerFactory;
import org.eclipse.jnosql.databases.redis.communication.RedisBucketManagerFactory;

import java.util.List;
import java.util.Queue;
```


**Step 3: Implement the `main` Method**

- Implement the `main` method inside the `App2` class:

```java
    public static void main(String[] args) {
```

**Step 4: Try-With-Resources Block**

- Set up a try-with-resources block to manage the Jakarta EE `SeContainer`. The `SeContainer` is responsible for dependency injection and managing resources:

```java
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
```

**Step 5: Obtain a `BucketManagerFactory`**

- Obtain an instance of the `BucketManagerFactory` (specifically, `RedisBucketManagerFactory`) using Jakarta EE's dependency injection. The `select` method is used to select and retrieve the factory:

```java
            BucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();
```

**Step 6: Work with a Redis Queue**

- Use the `BucketManagerFactory` to create and interact with a Redis queue named "orders." Perform the following operations on the queue:
  
  - Clear the queue using `orders.clear()`.
  - Add items ("Phone," "Tablet," "book") to the queue using `orders.add(...)`.
  - Remove the element at the front of the queue using `orders.remove()`.
  - Peek at the element at the front of the queue using `orders.peek()`.

```java
            Queue<String> orders = factory.getQueue("orders", String.class);
            orders.clear();
            orders.add("Phone");
            orders.add("Tablet");
            orders.add("book");
            String front = orders.remove();
            System.out.println("Front: " + front);
            String peeked = orders.peek();
            System.out.println("Peeked element: " + peeked);
```

**Step 7: Print Queue Contents**

- Print the contents of the Redis queue after performing the operations:

```java
            System.out.println("the result");
            orders.forEach(System.out::println);
```

**Step 8: Close the `SeContainer`**

- Close the `SeContainer` and release any resources used by the application:

```java
        }
    }

    private App2() {
    }
}
```

This lab demonstrates how to use Jakarta EE with Jakarta NoSQL to interact with a Redis queue, perform queue operations, and manage resources using Jakarta EE's `SeContainer`.



### :material-check-outline: Solution

??? example "Click to see..."

```java


import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.eclipse.jnosql.communication.keyvalue.BucketManagerFactory;
import org.eclipse.jnosql.databases.redis.communication.RedisBucketManagerFactory;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class App2 {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            BucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();
            Queue<String> orders = factory.getQueue("orders", String.class);
            orders.clear();
            orders.add("Phone");
            orders.add("Tablet");
            orders.add("book");
            // remove the element at the front of the queue
            String front = orders.remove();
            System.out.println("Front: " + front);
            // peek at the element at the front of the queue
            String peeked = orders.peek();
            System.out.println("Peeked element: " + peeked);
            System.out.println("the result");
            orders.forEach(System.out::println);
        }
    }

    private App2() {
    }
}

```

## 2. Redis Map

This lab will introduce the integration with Redis and a Map.

material-play-box-multiple-outline: Steps

Certainly, here's a step-by-step guide for the student to understand the `App3` class:


**Step 1: Create the `App3` Class**

- Define the `App3` class:

```java
public class App3 {
```


**Step 2: Import Required Packages**

- Import necessary packages and classes including Jakarta EE and Jakarta NoSQL:

```java
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.eclipse.jnosql.communication.keyvalue.BucketManagerFactory;
import org.eclipse.jnosql.databases.redis.communication.RedisBucketManagerFactory;

import java.util.Map;
```

**Step 3: Implement the `main` Method**

- Implement the `main` method inside the `App3` class:

```java
    public static void main(String[] args) {
```

**Step 4: Try-With-Resources Block**

- Set up a try-with-resources block to manage the Jakarta EE `SeContainer`. The `SeContainer` is responsible for dependency injection and managing resources:

```java
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
```

**Step 5: Obtain a `BucketManagerFactory`**

- Obtain an instance of the `BucketManagerFactory` (specifically, `RedisBucketManagerFactory`) using Jakarta EE's dependency injection. The `select` method is used to select and retrieve the factory:

```java
            BucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();
```

**Step 6: Work with a Redis Map**

- Use the `BucketManagerFactory` to create and interact with a Redis map named "basket." Perform the following operations on the map:

  - Clear the map using `basket.clear()`.
  - Add items to the map using `basket.put(...)`.
  - Print the contents of the map.

```java
            Map<Integer, String> basket = factory.getMap("basket", Integer.class, String.class);
            basket.clear();
            basket.put(1, "Banana");
            basket.put(2, "Watermelon");
            basket.put(4, "Apple");
```

**Step 7: Print Map Contents**

- Print the contents of the Redis map after performing the operations:

```java
            System.out.println("Basket: ");
            basket.forEach((k, v) -> System.out.println(k + " - " + v));
```

**Step 8: Close the `SeContainer`**

- Close the `SeContainer` and release any resources used by the application:

```java
        }
    }

    private App3() {
    }
}
```

This lab demonstrates how to use Jakarta EE with Jakarta NoSQL to interact with a Redis map, perform map operations, and manage resources using Jakarta EE's `SeContainer`.


### :material-check-outline: Solution

??? example "Click to see..."

`
```java

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.eclipse.jnosql.communication.keyvalue.BucketManagerFactory;
import org.eclipse.jnosql.databases.redis.communication.RedisBucketManagerFactory;

import java.util.Map;
import java.util.Queue;

public class App3 {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            BucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();
            Map<Integer, String> basket = factory.getMap("basket", Integer.class, String.class);
            basket.clear();
            basket.put(1, "Banana");
            basket.put(2, "Watermelon");
            basket.put(4, "Apple");

            System.out.println("Basket: ");
            basket.forEach((k, v) -> System.out.println(k + " - " + v));


        }
    }

    private App3() {
    }
}
```


## 3. Redis SortedSet

This lab will introduce the integration with Redis and a SortedSet.

material-play-box-multiple-outline: Steps

Certainly, here's a step-by-step guide for the student to understand the `App4` class:


**Step 1: Create the `App4` Class**

- Define the `App4` class:

```java
public class App4 {
```

**Step 2: Import Required Packages**

- Import necessary packages and classes including Jakarta EE and Jakarta NoSQL:

```java
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.eclipse.jnosql.databases.redis.communication.Ranking;
import org.eclipse.jnosql.databases.redis.communication.RedisBucketManagerFactory;
import org.eclipse.jnosql.databases.redis.communication.SortedSet;

import java.util.List;
import java.util.Map;
```


**Step 3: Implement the `main` Method**

- Implement the `main` method inside the `App4` class:

```java
    public static void main(String[] args) {
```

**Step 4: Try-With-Resources Block**

- Set up a try-with-resources block to manage the Jakarta EE `SeContainer`. The `SeContainer` is responsible for dependency injection and managing resources:

```java
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
```

**Step 5: Obtain a `RedisBucketManagerFactory`**

- Obtain an instance of the `RedisBucketManagerFactory` using Jakarta EE's dependency injection. The `select` method is used to select and retrieve the factory:

```java
            RedisBucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();
```

**Step 6: Work with a Redis Sorted Set**

- Use the `RedisBucketManagerFactory` to create and interact with a Redis sorted set named "game." Perform the following operations on the sorted set:

  - Add elements to the sorted set using `game.add(...)`. Elements can be added individually or as a `Ranking` object.
  - Get the ranking of the elements using `game.getRanking()`.
  - Get the reverse ranking of the elements using `game.getRevRanking()`.

```java
            SortedSet game = factory.getSortedSet("game");
            game.add("Otavio", 10);
            game.add("Elias", 20);
            game.add("Ada", 30);
            game.add(Ranking.of("Poliana", 40));
```

**Step 7: Print Rankings**

- Print the ranking and reverse ranking of the elements in the Redis sorted set:

```java
            List<Ranking> ranking = game.getRanking();
            System.out.println("Ranking: " + ranking);
            System.out.println("The reverse ranking: " + game.getRevRanking());
```

**Step 8: Close the `SeContainer`**

- Close the `SeContainer` and release any resources used by the application:

```java
        }
    }

    private App4() {
    }
}
```

This lab demonstrates how to use Jakarta EE with Jakarta NoSQL to interact with a Redis sorted set, perform set operations, and manage resources using Jakarta EE's `SeContainer`.



### :material-check-outline: Solution

??? example "Click to see..."


```java


import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.eclipse.jnosql.databases.redis.communication.Ranking;
import org.eclipse.jnosql.databases.redis.communication.RedisBucketManagerFactory;
import org.eclipse.jnosql.databases.redis.communication.SortedSet;

import java.util.List;
import java.util.Map;

public class App4 {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            RedisBucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();
            SortedSet game = factory.getSortedSet("game");
            game.add("Otavio", 10);
            game.add("Elias", 20);
            game.add("Ada", 30);
            game.add(Ranking.of("Poliana", 40));

            List<Ranking> ranking = game.getRanking();
            System.out.println("Ranking: " + ranking);

            System.out.println("The reverse ranking: " + game.getRevRanking());


        }
    }

    private App4() {
    }
}
```


## 3. Redis Counter

This lab will introduce the integration with Redis and a Counter.

material-play-box-multiple-outline: Steps

Certainly, here's a step-by-step guide for the student to understand the `App5` class:

**Step 1: Create the `App5` Class**

- Define the `App5` class:

```java
public class App5 {
```

**Step 2: Import Required Packages**

- Import necessary packages and classes including Jakarta EE and Jakarta NoSQL:

```java
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.eclipse.jnosql.databases.redis.communication.Counter;
import org.eclipse.jnosql.databases.redis.communication.RedisBucketManagerFactory;
```

**Step 3: Implement the `main` Method**

- Implement the `main` method inside the `App5` class:

```java
    public static void main(String[] args) {
```

**Step 4: Try-With-Resources Block**

- Set up a try-with-resources block to manage the Jakarta EE `SeContainer`. The `SeContainer` is responsible for dependency injection and managing resources:

```java
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
```

**Step 5: Obtain a `RedisBucketManagerFactory`**

- Obtain an instance of the `RedisBucketManagerFactory` using Jakarta EE's dependency injection. The `select` method is used to select and retrieve the factory:

```java
            RedisBucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();
```

**Step 6: Work with Redis Counters**

- Use the `RedisBucketManagerFactory` to create and interact with Redis counters named "home" and "products." Perform the following operations on the counters:

  - Increment the counters using `counter.increment()`. You can increment a counter by a specified value.
  - Get the current value of the counters using `counter.get()`.

```java
        Counter home = factory.getCounter("home");
        Counter products = factory.getCounter("products");
        home.increment();
        products.increment();
        products.increment(3L);
```

**Step 7: Print Counter Values**

- Print the current values of the counters:

```java
        System.out.println("Home: " + home.get());
        System.out.println("Products: " + products.get());
```

**Step 8: Close the `SeContainer`**

- Close the `SeContainer` and release any resources used by the application:

```java
        }
    }

    private App5() {
    }
}
```

This lab demonstrates how to use Jakarta EE with Jakarta NoSQL to interact with Redis counters, increment counter values, and retrieve current counter values using Jakarta EE's `SeContainer`.



### :material-check-outline: Solution

??? example "Click to see..."


```java
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.eclipse.jnosql.databases.redis.communication.Counter;
import org.eclipse.jnosql.databases.redis.communication.Ranking;
import org.eclipse.jnosql.databases.redis.communication.RedisBucketManagerFactory;
import org.eclipse.jnosql.databases.redis.communication.SortedSet;

import java.util.List;

public class App5 {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            RedisBucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();
            Counter home = factory.getCounter("home");
            Counter products = factory.getCounter("products");
            home.increment();
            products.increment();
            products.increment(3L);

            System.out.println("Home: " + home.get());
            System.out.println("Products: " + products.get());


        }
    }

    private App5() {
    }
}

```



## 4. Redis with entity


This lab will introduce an entity integration with Redis database with Java.

material-play-box-multiple-outline: Steps

**Step 1: Define the `User` Entity Class**

```java
@Entity
public class User  {
```

- The `User` class is annotated with `@Entity`, indicating that it's a persistent entity.
- It contains fields for storing user information such as `userName`, `name`, `settings`, and `languages`.
- Getter methods are provided for accessing these fields.

**Step 2: Implement the `UserBuilder` Class**

```java
public class UserBuilder {
```

- The `UserBuilder` class is a builder pattern class for creating `User` instances.
- It provides methods for setting the username, name, settings, and languages.
- The `build` method constructs and returns a new `User` object.

**Step 3: Define the `UserRepository` Interface**

```java
@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
```

- The `UserRepository` interface extends `CrudRepository` and is annotated with `@Repository`.
- It specifies methods for performing CRUD (Create, Read, Update, Delete) operations on `User` entities.

**Step 4: Create the `App6` Class**

```java
public class App6 {
```

- The `App6` class is the main class that contains the `main` method.

**Step 5: Implement the `main` Method**

```java
public static void main(String[] args) throws InterruptedException {
```

- The `main` method is the entry point of the application.

**Step 6: Try-With-Resources Block for Jakarta EE Container**

```java
try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
```

- A try-with-resources block is used to manage the Jakarta EE container, which handles dependency injection and resource management.

**Step 7: Obtain a `KeyValueTemplate` Instance**

```java
KeyValueTemplate template = container.select(KeyValueTemplate.class).get();
```

- An instance of `KeyValueTemplate` is obtained from the Jakarta EE container to interact with the key-value store.

**Step 8: Create `User` Instances**

```java
User otaviojava = User.builder().username("otaviojava").name("Otavio Santana")
    .languages(Set.of("Portuguese", "English", "Spanish", "Italian", "French"))
    .settings(Map.of("location", "Portugal", "currency", "EUR")).build();

User poliana = User.builder().username("polianapo").name("Poliana Santana")
    .languages(Set.of("Portuguese", "English"))
    .settings(Map.of("location", "Portugal", "currency", "EUR")).build();
```

- Two `User` instances, `otaviojava` and `poliana`, are created with user information using the builder pattern.

**Step 9: Put `User` Instances into the Key-Value Store**

```java
template.put(otaviojava);
template.put(poliana, Duration.ofSeconds(1));
```

- The `put` method is used to store the `User` instances in the key-value store. A duration of 1 second is specified for the Poliana entry.

**Step 10: Retrieve `User` Instances**

```java
System.out.println("Find Poliana : " + template.get("polianapo", User.class));
```

- The `get` method is used to retrieve the Poliana user by username from the key-value store.

**Step 11: Wait and Retrieve Poliana Again**

```java
TimeUnit.SECONDS.sleep(2L);
System.out.println("Find Poliana : " + template.get("polianapo", User.class));
```

- A `TimeUnit` is used to pause execution for 2 seconds to demonstrate the expiration of the Poliana entry. Then, an attempt is made to retrieve Poliana again.

**Step 12: Retrieve Otavio Santana**

```java
Optional<User> user = template.get("otaviojava", User.class);
System.out.println("Entity found: " + user);
```

- The `get` method is used to retrieve the Otavio Santana user by username from the key-value store.

**Step 13: Close the Try-With-Resources Block**

```java
}
```

- The try-with-resources block is closed to release resources properly.

**Step 14: Private Constructor**

```java
private App6() {
}
```

- A private constructor is defined to prevent the instantiation of the `App6` class.

This lab demonstrates how to use Jakarta EE with Jakarta NoSQL to interact with a key-value store, create and retrieve `User` entities, and manage data expiration using durations.

### :material-check-outline: Solution

??? example "Click to see..."


```java

import jakarta.nosql.Entity;
import jakarta.nosql.Id;

import java.util.Map;
import java.util.Objects;
import java.util.Set;


@Entity
public class User  {


    @Id
    private String userName;

    private String name;

    private Map<String, String> settings;

    private Set<String> languages;

    public User() {
    }

    User(String userName, String name, Map<String, String> settings,
         Set<String> languages) {
        this.userName = userName;
        this.name = name;
        this.settings = settings;
        this.languages = languages;
    }

    public String userName() {
        return userName;
    }

    public String name() {
        return name;
    }

    public Map<String, String> settings() {
        return settings;
    }

    public Set<String> languages() {
        return languages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userName);
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", settings=" + settings +
                ", languages=" + languages +
                '}';
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }
}


import java.util.Collections;
import java.util.Map;
import java.util.Set;


public class UserBuilder {

    private String username;

    private String name;

    private Map<String, String> settings = Collections.emptyMap();

    private Set<String> languages = Collections.emptySet();

    public UserBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder name(String name) {
        this.name = name;
        return this;
    }


    public UserBuilder settings(Map<String, String> settings) {
        this.settings = settings;
        return this;
    }

    public UserBuilder languages(Set<String> languages) {
        this.languages = languages;
        return this;
    }

    public User build() {
        return new User(username, name, settings, languages);
    }
}

import jakarta.nosql.keyvalue.KeyValueTemplate;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class App6 {


    public static void main(String[] args) throws InterruptedException {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            KeyValueTemplate template = container.select(KeyValueTemplate.class).get();
            User otaviojava = User.builder().username("otaviojava").name("Otavio Santana")
                    .languages(Set.of("Portuguese", "English", "Spanish", "Italian", "French"))
                    .settings(Map.of("location", "Portugal", "currency", "EUR")).build();


            User poliana = User.builder().username("polianapo").name("Poliana Santana")
                    .languages(Set.of("Portuguese", "English"))
                    .settings(Map.of("location", "Portugal", "currency", "EUR")).build();

            template.put(otaviojava);
            template.put(poliana, Duration.ofSeconds(1));
            System.out.println("Find Poliana : " + template.get("polianapo", User.class));

            TimeUnit.SECONDS.sleep(2L);
            System.out.println("Find Poliana : " + template.get("polianapo", User.class));
            Optional<User> user = template.get("otaviojava", User.class);
            System.out.println("Entity found: " + user);

        }
    }

    private App6() {
    }
}


```



## 4. Redis with Repository


This lab will introduce Repository integration with Redis database with Java.

material-play-box-multiple-outline: Steps

**Step 1: Define the `UserRepository` Interface**

```java
@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
```

- The `UserRepository` interface extends `CrudRepository` and is annotated with `@Repository`.
- It specifies methods for performing CRUD (Create, Read, Update, Delete) operations on `User` entities.

**Step 2: Create the `App7` Class**

```java
public class App7 {
```

- The `App7` class is the main class that contains the `main` method.

**Step 3: Implement the `main` Method**

```java
public static void main(String[] args) {
```

- The `main` method is the entry point of the application.

**Step 4: Try-With-Resources Block for Jakarta EE Container**

```java
try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
```

- A try-with-resources block is used to manage the Jakarta EE container, which handles dependency injection and resource management.

**Step 5: Obtain a `UserRepository` Instance**

```java
UserRepository repository = container.select(UserRepository.class).get();
```

- An instance of `UserRepository` is obtained from the Jakarta EE container to interact with the user repository.

**Step 6: Create a New `User` Instance (Ada Lovelace)**

```java
User ada = User.builder().username("ada").name("Ada Lovelace")
        .languages(Set.of("Latin")).settings(Map.of("currency", "food")).build();
```

- A new `User` instance for Ada Lovelace is created using the builder pattern.
- The user's username, name, languages, and settings are set.

**Step 7: Save the `User` to the Repository**

```java
repository.save(ada);
```

- The `save` method is used to persist the `User` instance (Ada Lovelace) to the repository.

**Step 8: Find the `User` by ID**

```java
Optional<User> user = repository.findById("ada");
System.out.println("User found: " + user);
```

- The `findById` method is used to retrieve the user (Ada Lovelace) from the repository by ID.
- The result is printed to the console.

**Step 9: Check if the User Exists**

```java
System.out.println("Exist? " + repository.existsById("ada"));
```

- The `existsById` method is used to check if the user (Ada Lovelace) exists in the repository.
- The result is printed to the console.

**Step 10: Delete the User**

```java
repository.deleteById("ada");
```

- The `deleteById` method is used to remove the user (Ada Lovelace) from the repository.

**Step 11: Check if the User Exists After Deletion**

```java
System.out.println("Exist? " + repository.existsById("ada"));
```

- After deleting the user, the `existsById` method is used again to check if the user (Ada Lovelace) still exists in the repository.
- The result is printed to the console.

**Step 12: Close the Try-With-Resources Block**

```java
}
```

- The try-with-resources block is closed to release resources properly.

**Step 13: Private Constructor**

```java
private App7() {
}
```

- A private constructor is defined to prevent the instantiation of the `App7` class.

This lab demonstrates how to use Jakarta EE with Jakarta NoSQL to perform CRUD operations on a `User` repository, including creating, reading, updating, and deleting `User` entities.


### :material-check-outline: Solution

??? example "Click to see..."


```java

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}


import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class App7 {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

            UserRepository repository = container.select(UserRepository.class).get();

            User ada = User.builder().username("ada").name("Ada Lovelace")
                    .languages(Set.of("Latin")).settings(Map.of("currency", "food")).build();
            repository.save(ada);
            Optional<User> user = repository.findById("ada");
            System.out.println("User found: " + user);
            System.out.println("Exist? " + repository.existsById("ada"));
            repository.deleteById("ada");
            System.out.println("Exist? " + repository.existsById("ada"));
        }
    }

    private App7() {
    }
}


```
