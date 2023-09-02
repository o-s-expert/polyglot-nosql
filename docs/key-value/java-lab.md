# Redis and Java Lab



## 1. The fisrt integration

This lab will introduce the first integration between Java and Redis using Eclipse JNoSQL.

material-play-box-multiple-outline: Steps

1. Create an App class
2. Initialize the SeContianer with the following command:
`SeContainer container = SeContainerInitializer.newInstance().initialize()`
3. Explore the try-with-resources.
4. Inject the BucketManagerFactory selecting from the container with the following command:
`BucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();
`
5. Create a List of String from the Redis instance with the bucket name "names" from the command ` factory.getList("names", String.class)`
6. Create a Set of String from Redis instance with the bucket name fruits from the command `factory.getSet("fruits", String.class)`

7. Include in the list of names the following names: "Otavio", "Elias", "Ada", "Poliana", "Otavio"
8. Include in the Set of fruits the following fruits: "Banana", "Banana", "Apple", "Watermelon", "Banana", "Apple"
9. Log the result.

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

1. Create an App2 class
2. Initialize the SeContianer with the following command:
`SeContainer container = SeContainerInitializer.newInstance().initialize()`
3. Explore the try-with-resources.
5. Inject the BucketManagerFactory selecting from the container with the following command:
`BucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();
`
6. Create a Queue of Strings from the Redis instance with the bucket name "orders" from the command ` factory.getQueue("orders", String.class)`
7. Include in the orders queue Phone, Table, book.
8. Use the remove `method` log the result
9. Use the method `peek` log the result
10. Execute a for each and log the result.



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

1. Create an App3 class
2. Initialize the SeContianer with the following command:
`SeContainer container = SeContainerInitializer.newInstance().initialize()`
Explore the try-with-resources.
3. Inject the BucketManagerFactory selecting from the container with the following command:
`BucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();
`
4. Create a Queue of Strings from the Redis instance with the bucket name "orders" from the command ` factory.getQueue("orders", String.class)`
5. Include in the orders queue Phone, Table, book.
6. Use the remove `method` log the result
7. Use the method `peek` log the result
8. Execute a for each and log the result.



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

1. Create an App4 class
2. Initialize the SeContianer with the following command:
`SeContainer container = SeContainerInitializer.newInstance().initialize()`
Explore the try-with-resources.
3. Inject the BucketManagerFactory selecting from the container with the following command:
`BucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();
`
4. Create a SortedSet from the Redis instance with the bucket name "game" from the command ` factory.getSortedSet("game")`
5. Include in game Otavio with 10 points, Elias with 20, Ada with 30, Poliana with 40.
6. List the ranking with the `getRanking` method.
7. List the reverse ranking with `getRevRanking` method
8. Log both lits



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

1. Create an App5 class
2. Initialize the SeContianer with the following command:
`SeContainer container = SeContainerInitializer.newInstance().initialize()`
Explore the try-with-resources.
3. Inject the BucketManagerFactory selecting from the container with the following command:
`BucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();
`
4. Create a Counter from the Redis instance with the bucket name "home" from the command ` factory.getCounter("home")`
5. Create a Counter from the Redis instance with the bucket name "products" from the command ` factory.getCounter("products")`
6. Increment home
7. Increment products tree times
8. Log both counter



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

1. Create a User class with username, name as String, settings as Map<String, String>, and languages as Set<String>.
2. Put Entity annotation at the User class to define it as a User.
3. In the userName field, include the Id annotation.
4. Create a UserBuilder to make it easier to create a User instance.
5. Create a UserRepository that extends CrudRepository<User, String>
6. Put the Repository at the UserRepository interface
7. Create an App6 class
8. Create the SeContainer into try-resources
9. Inject the KeyValueTemplate exploring the container select
10. Create two Users
11. Insert in the database with the put method at the KeyValueTemplate instance.
12. Find by the key using the get method at KeyValueTemplate.
13. Explore KeyValueTemplate
14. Create an App7 from a copy of App6.
15. Replace the interaction with KeyValueTemplate with the UserRepository instance.

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

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
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