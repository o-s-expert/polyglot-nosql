# Redis and Java Lab




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