# Redis - Lab 2

This lab will introduce the first integration between Java and Redis using Eclipse JNoSQL.

!!! note 

    The connection properties are already defined in the `src/main/java/resources/META-INF` folder in the `microprofile-config.properties`.

    The connection to Redis will be done using the database, port, and host defined there.

## 1. The first integration

### :material-play-box-multiple-outline: Steps

1. Open the `03-key-value` project and navigate to the `src/main/java`
2. Create a class called `AppFirstIntegration` in the `expert.os.labs.persistence` package
3. Add a main method

    ```java
    public static void main(String[] args) {
    }
    ```

4. Set up a try-with-resources block, inside the `main` method, to manage the Jakarta EE `SeContainer` that is responsible for dependency injection and managing resources

    ```java
    try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {     
    }
    ```

5. Obtain an instance of the `BucketManagerFactory` (specifically, `RedisBucketManagerFactory`) using Jakarta EE's dependency injection by adding the following inside the try-catch-resource block

    ```java
    BucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();
    ```

6. Use the `BucketManagerFactory` to create and interact with data structures in Redis. In this case, a Redis list named "names" and a Redis set named "fruits" are created and retrieved

    - add the following code after the previous one:

        ```java
        List<String> names = factory.getList("names", String.class);
        Set<String> fruits = factory.getSet("fruits", String.class);
        ```

7. Add data to the Redis list and set. In this example, names and fruits are added to the respective data structures
    
    - add the following code after the previous one:

        ```java
        names.addAll(List.of("Otavio", "Elias", "Ada", "Poliana", "Otavio"));
        fruits.addAll(List.of("Banana", "Banana", "Apple", "Watermelon", "Banana", "Apple"));
        ```

8. Print the data stored in the "names" and "fruits" data structures:

    ```java
    System.out.println("Names: ");
    names.forEach(System.out::println);

    System.out.println("Fruits: ");
    fruits.forEach(System.out::println);
    ```

9. Define a private constructor for the `App` class to prevent instantiation since it contains only static methods:

    ```java
    private AppFirstIntegration() {
    }
    ```

9. Run the `main()` method

### :material-checkbox-multiple-outline: Expected results

* The following output
   
    ```
    Names: 
    Otavio
    Elias
    Ada
    Poliana
    Otavio
    Fruits: 
    Apple
    Watermelon
    Banana
    ```

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;
    import org.eclipse.jnosql.communication.keyvalue.BucketManagerFactory;
    import org.eclipse.jnosql.databases.redis.communication.RedisBucketManagerFactory;

    import java.util.List;
    import java.util.Set;

    public class AppFirstIntegration {

        private AppFirstIntegration() {
        }

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
    }
    ```

## 2. Redis and a Queue

This lab will introduce the integration with Redis and a Queue.

### :material-play-box-multiple-outline: Steps

1. Create a class called `AppQueue` in the `expert.os.labs.persistence` package
2. Add a main method

    ```java
    public static void main(String[] args) {
    }
    ```

3. Set up a try-with-resources block, inside the `main` method, to manage the Jakarta EE `SeContainer` that is responsible for dependency injection and managing resources

    ```java
    try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {     
    }
    ```

4. Obtain an instance of the `BucketManagerFactory` (specifically, `RedisBucketManagerFactory`) using Jakarta EE's dependency injection by adding the following inside the try-catch-resource block

    ```java
    BucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();
    ```

5. Use the `BucketManagerFactory` to create and interact with a Redis queue named `orders` by using the method `getQueue`
    
    - the `Queue` class is from the `java.util` package
    - its type is `String`
    - the `getQueue()` key is name `"orders"` and the type is `String`

        ```java
        Queue<String> orders = factory.getQueue("orders", String.class);
        ```

6. Clear the previous content using `orders.clear()` and then add the following items in the queue using the method `add(...)`

    - "Phone"
    - "Tablet"
    - "Book"

7. Remove the element at the front of the queue using `orders.remove()`
8. Peek at the element at the front of the queue using `orders.peek()`
9. Print the contents of the Redis queue after performing the below operations

    ```java
    orders.forEach(System.out::println);
    ```

10. Define a private constructor for the `App` class to prevent instantiation since it contains only static methods:

    ```java
    private AppQueue() {
    }
    ```

11. Run the `main()` method

### :material-checkbox-multiple-outline: Expected results

* The following output

    ```
    Table
    Book
    ```

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;
    import org.eclipse.jnosql.communication.keyvalue.BucketManagerFactory;
    import org.eclipse.jnosql.databases.redis.communication.RedisBucketManagerFactory;

    import java.util.Queue;

    public class AppQueue {

        public static void main(String[] args) {
            try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
                BucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();

                Queue<String> orders = factory.getQueue("orders", String.class);
                orders.clear();
                orders.add("Phone");
                orders.add("Tablet");
                orders.add("Book");

                orders.remove();
                orders.peek();
                orders.forEach(System.out::println);
            }
        }
    }
    ```

## 3. Redis and a Map

### :material-play-box-multiple-outline: Steps

1. Create a class called `AppQueue` in the `expert.os.labs.persistence` package
2. Add a main method

    ```java
    public static void main(String[] args) {
    }
    ```

3. Set up a try-with-resources block, inside the `main` method, to manage the Jakarta EE `SeContainer` that is responsible for dependency injection and managing resources

    ```java
    try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {     
    }
    ```

4. Obtain an instance of the `BucketManagerFactory` (specifically, `RedisBucketManagerFactory`) using Jakarta EE's dependency injection by adding the following inside the try-catch-resource block

    ```java
    BucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();
    ```

5. Use the `BucketManagerFactory` to create and interact with a Redis map named `basket` by using the method `getBasket`

    - the `Map` must have the types as `Integer` and `String`
    - the `getMap()` the key is named `"basket"` and the types are Integer` and `String`

    ```java
     Map<Integer, String> basket = factory.getMap("basket", Integer.class, String.class);
    ```

6. Clear the previous content using `orders.clear()` and then add the following items on the basket using the method `put(...)`

    | key | value |
    |--|--|
    | 1 | "Banana" |
    | 2 | "Watermelon" |
    | 3 | "BaApplenana" |

7. Print the contents of the Redis queue after performing the below operations

    ```java
    basket.forEach((k, v) -> System.out.println(k + " - " + v));
    ```

8. Define a private constructor for the `App` class to prevent instantiation since it contains only static methods:

    ```java
    private AppQueue() {
    }
    ```

9. Run the `main()` method

### :material-checkbox-multiple-outline: Expected results

* The following output

    ```
    1 - Banana
    2 - Watermelon
    4 - Apple
    ```

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;
    import org.eclipse.jnosql.communication.keyvalue.BucketManagerFactory;
    import org.eclipse.jnosql.databases.redis.communication.RedisBucketManagerFactory;

    import java.util.Map;

    public class AppMap {

        public static void main(String[] args) {

            try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
                BucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();

                Map<Integer, String> basket = factory.getMap("basket", Integer.class, String.class);
                basket.clear();
                basket.put(1, "Banana");
                basket.put(2, "Watermelon");
                basket.put(4, "Apple");

                basket.forEach((k, v) -> System.out.println(k + " - " + v));
            }
        }

        private AppMap() {
        }
    }
    ```

## 4. Redis and a SortedMap

### :material-play-box-multiple-outline: Steps

1. Create a class called `AppSortedMap` in the `expert.os.labs.persistence` package
2. Add a main method

    ```java
    public static void main(String[] args) {
    }
    ```

3. Set up a try-with-resources block, inside the `main` method, to manage the Jakarta EE `SeContainer` that is responsible for dependency injection and managing resources

    ```java
    try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {     
    }
    ```

4. Obtain an instance of the `BucketManagerFactory` (specifically, `RedisBucketManagerFactory`) using Jakarta EE's dependency injection by adding the following inside the try-catch-resource block

    ```java
    BucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();
    ```

5. Use the `BucketManagerFactory` to create and interact with a Redis sorted map named `game` by using the method `getSortedSet`

    - the `SortedSet` is the field class

    ```java
    SortedSet game = factory.getSortedSet("game");
    ```

6. Add the following itens in the game using the method `add(...)`

    | key | value |
    |--|--|
    | "Otavio" | 10 |
    | "Elias" | 20 |
    | "Ada | 30 |

7. Add one more item to the game, but using the `Ranking.of()` from `org.eclipse.jnosql.databases.redis.communication` package, adding:

    | key | value |
    |--|--|
    | "Poliana" | 40 |

8. Get the ranking, add it on a list

    ```java
    List<Ranking> ranking = game.getRanking();
    ```

9. Print out the ranking

    ```java
    System.out.println("Ranking: " + ranking);
    ```

10. Print out the ranking in a reverse order

    ```java
    System.out.println("The reverse ranking: " + game.getRevRanking());
    ```

11. Run the `main()` method

### :material-checkbox-multiple-outline: Expected results

* The following output

    ```
    Ranking: [DefaultRanking{point=10.0, member='Otavio'}, DefaultRanking{point=20.0, member='Elias'}, DefaultRanking{point=30.0, member='Ada'}, DefaultRanking{point=40.0, member='Poliana'}]
    The reverse ranking: [DefaultRanking{point=40.0, member='Poliana'}, DefaultRanking{point=30.0, member='Ada'}, DefaultRanking{point=20.0, member='Elias'}, DefaultRanking{point=10.0, member='Otavio'}]
    ```

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;
    import org.eclipse.jnosql.databases.redis.communication.Ranking;
    import org.eclipse.jnosql.databases.redis.communication.RedisBucketManagerFactory;
    import org.eclipse.jnosql.databases.redis.communication.SortedSet;

    import java.util.List;

    public class AppSortedMap {

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

        private AppSortedMap() {
        }
    }
    ```

## 5. Redis and Counter

### :material-play-box-multiple-outline: Steps

1. Create a class called `AppSortedMap` in the `expert.os.labs.persistence` package
2. Add a main method

    ```java
    public static void main(String[] args) {
    }
    ```

3. Set up a try-with-resources block, inside the `main` method, to manage the Jakarta EE `SeContainer` that is responsible for dependency injection and managing resources

    ```java
    try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {     
    }
    ```

4. Obtain an instance of the `BucketManagerFactory` (specifically, `RedisBucketManagerFactory`) using Jakarta EE's dependency injection by adding the following inside the try-catch-resource block

    ```java
    BucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();
    ```

5. Use the `RedisBucketManagerFactory` to create and interact with Redis counters named "home" and "products" using the `counter()` method

    ```java
    Counter home = factory.getCounter("home");
    Counter products = factory.getCounter("products");
    ```

6. Increment the `home` and the `products` using the method `increment()`

    ```java
    home.increment();
    products.increment();
    ```

7. Increment in 3 the `products`

    ```java
    products.increment(3L);
    ```

8. Print out the `home` and `products` values

    ```java
    System.out.println("Home: " + home.get());
    System.out.println("Products: " + products.get());
    ```

9. Run the `main()` method

### :material-checkbox-multiple-outline: Expected results

* The following output

    ```
    Home: 1.0
    Products: 4.0
    ```

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;
    import org.eclipse.jnosql.databases.redis.communication.Counter;
    import org.eclipse.jnosql.databases.redis.communication.RedisBucketManagerFactory;

    public class AppCounter {

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

        private AppCounter() {
        }
    }
    ```
