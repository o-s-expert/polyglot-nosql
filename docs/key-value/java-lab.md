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

