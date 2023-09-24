# Redis - Lab 3

This lab will introduce an entity integration with Redis database with Java.


## 1. Create an entity

### :material-play-box-multiple-outline: Steps

1. Create a class called `User` in the `expert.os.labs.persistence` package
2. Annotate this class with `@Entity` , indicating that it's a persistent entity
3. Add the following `private` fields with its types

    | type | field |
    |--|--|
    | `String` | `userName` |
    | `String` | `name` |
    | `Map<String, String>` | `settings` |
    | `Set<String>` | `languages` |

4. Annotate the `userName` with `@Id`
5. Create a constructor using all fields
6. Create get methods for each field
7. Add the `toString()` method

### :material-checkbox-multiple-outline: Expected results

* Entity `User` created


### :material-check-outline: Solution

??? example "Click to see..."
    ```java
    import jakarta.nosql.Entity;
    import jakarta.nosql.Id;
    import java.util.Map;
    import java.util.Set;


    @Entity
    public class User {

        @Id
        private String userName;

        private String name;

        private Map<String, String> settings;

        private Set<String> languages;

        public User() {
        }

        public User(String userName, String name, Map<String, String> settings, Set<String> languages) {
            this.userName = userName;
            this.name = name;
            this.settings = settings;
            this.languages = languages;
        }

        public String getUserName() {
            return userName;
        }

        public String getName() {
            return name;
        }

        public Map<String, String> getSettings() {
            return settings;
        }

        public Set<String> getLanguages() {
            return languages;
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
    }
    ```

## 2. Implement the Builder class

### :material-play-box-multiple-outline: Steps

1. Create a class called `UserBuilder` in the `expert.os.labs.persistence` package
2. Add the same fields we had previously added to the `User` class, without the annotations
    ```java
    private String userName;
    private String name;
    private Map<String, String> settings;
    private Set<String> languages;
    ```
3. Add the builder methods for each field
      - example using the `userName` field

         ```java
         public UserBuilder userName(String userName) {
            this.userName = userName;
            return this;
         }
         ```

4. Add the `build()` method creating a new instance of `User` using its constructor
5. In the `User` class add the builder method referring to the `UserBuilder`

    ```java
    public static UserBuilder builder() {
       return new UserBuilder();
    }
    ``` 

### :material-checkbox-multiple-outline: Expected results

* A new `UserBuilder` class implementing the Builder pattern for the `User` class
* A new `builder()` method in the `User` class

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
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
    ```

## 3. Define the repository interface

1. Create an `interface` called `UserRepository` in the `expert.os.labs.persistence` package
2. Annotate the class with `@Repository` from the `jakarta.data.repository` package
3. `extends` the class using `CrudRepository<User, String>` from the `jakarta.data.repository` package

### :material-checkbox-multiple-outline: Expected results

* The `UserRepository` that specifies methods for performing CRUD (Create, Read, Update, Delete) operations on `User` entities

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    @Repository
    public interface UserRepository extends CrudRepository<User, String> {
    }
    ```

## 4. Create the execution class

### :material-play-box-multiple-outline: Steps

1. Create a class called `AppUser` in the `expert.os.labs.persistence` package
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

4. Obtain an instance of the `KeyValueTemplate` to interact with the key-value store

    ```java
    KeyValueTemplate template = container.select(KeyValueTemplate.class).get();
    ```

5. Create two user instances using the `UserBuilder` class with different data insde the `try` statement

    ```java
    User user1 = User.builder().username("user1").name("Otavio Santana")
        .languages(Set.of("Portuguese", "English", "Spanish", "Italian", "French"))
        .settings(Map.of("location", "Portugal", "currency", "EUR")).build();


    User user2 = User.builder().username("user2").name("Poliana Santana")
        .languages(Set.of("Portuguese", "English"))
        .settings(Map.of("location", "Portugal", "currency", "EUR")).build();
    ```

6. Add the two user instances into the key-value store using the `KeuValueTemplate`, where the second one will have a delat of 1 second

    ```java
    template.put(user1);
    template.put(user2, Duration.ofSeconds(1));
    ```

7. Retrieve the `user2` data based on its `userName` and print out the result
    - use the method `get()` from the `template` field
    - the first parameter is the value of the `userName` field and the second parameter is the class

    ```java
    Optional<User> user2Data = template.get("user2", User.class);
    System.out.println("User2 data: " + user2Data);
    ```

8. Add a wait time, then retrieve and print out the same user again

    ```java
    TimeUnit.SECONDS.sleep(2L);
    Optional<User> user2DataSecondRetrieve = template.get("user2", User.class);
    System.out.println("User2 second retrieve data: " + user2DataSecondRetrieve);
    ```

9. Retrieve the `user1` data based on its `userName` and print out the result

    ```java
    Optional<User> user1Data = template.get("user1", User.class);
    System.out.println("User1 data: " + user1Data);
    ```

10. Define a private constructor for the `AppUser` class to prevent instantiation since it contains only static methods:

    ```java
    private AppUser() {
    }
    ```

9. Run the `main()` method

### :material-checkbox-multiple-outline: Expected results

* The following output
   
    ```
    User2 data: Optional[User{userName='user2', name='Poliana Santana', settings={location=Portugal, currency=EUR}, languages=[English, Portuguese]}]
    User2 second retrieve data: Optional.empty
    User1 data: Optional[User{userName='user1', name='Otavio Santana', settings={location=Portugal, currency=EUR}, languages=[English, Italian, French, Portuguese, Spanish]}]
    ```

* The second print out, related to the `user2` does not show any data because it expired

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;
    import jakarta.nosql.keyvalue.KeyValueTemplate;
    import java.time.Duration;
    import java.util.Map;
    import java.util.Optional;
    import java.util.Set;
    import java.util.concurrent.TimeUnit;

    public class AppUser {

        public static void main(String[] args) throws InterruptedException {
            try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
                KeyValueTemplate template = container.select(KeyValueTemplate.class).get();

                User user1 = User.builder().username("user1").name("Otavio Santana")
                    .languages(Set.of("Portuguese", "English", "Spanish", "Italian", "French"))
                    .settings(Map.of("location", "Portugal", "currency", "EUR")).build();


                User user2 = User.builder().username("user2").name("Poliana Santana")
                    .languages(Set.of("Portuguese", "English"))
                    .settings(Map.of("location", "Portugal", "currency", "EUR")).build();


                template.put(user1);
                template.put(user2, Duration.ofSeconds(1));

                Optional<User> user2Data = template.get("user2", User.class);
                System.out.println("User2 data: " + user2Data);

                TimeUnit.SECONDS.sleep(2L);
                Optional<User> user2DataSecondRetrieve = template.get("user2", User.class);
                System.out.println("User2 second retrieve data: " + user2DataSecondRetrieve);

                Optional<User> user1Data = template.get("user1", User.class);
                System.out.println("User1 data: " + user1Data);
            }
        }
    }
    ```

## 5. Use the  `UserRepository` with Redis

### :material-play-box-multiple-outline: Steps

1. Create an interface called `UserRepository` in the `expert.os.labs.persistence` package
2. Annotate the class with `@Repository` from the `jakarta.data.repository` package
3. Extends the interface using the `CrudRepository`

    ```java
    extends CrudRepository<User, String>
    ```

### :material-checkbox-multiple-outline: Expected results

* The integration in Redis with the repository to perform the CRUD operations in the `User` entity

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.data.repository.CrudRepository;
    import jakarta.data.repository.Repository;

    @Repository
    public interface UserRepository extends CrudRepository<User, String> {
    }
    ```

## 6. Using the `UserRepository`

### :material-play-box-multiple-outline: Steps

1. Create a class called `AppRepository` in the `expert.os.labs.persistence` package
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

4. Add the `UserRepository` instance

    ```java
     UserRepository repository = container.select(UserRepository.class).get();
    ```

5. Create a new `User` object

    ```java
    User ada = User.builder().username("ada").name("Ada Lovelace")
                .languages(Set.of("Latin")).settings(Map.of("currency", "food")).build();
    ```

6. Save the `User` object

    ```java
    repository.save(ada);
    ```

7. Retrieve the `User` object saved by its id, which is `username`, using the `findById` method fom the repository and print out the result

    ```java
    Optional<User> userFound = repository.findById("ada");
    System.out.println(userFound);
    ```

8. Check if an user already exists using the `existsById` method from the repository, and print out the result

    ```java
    boolean userExist = repository.existsById("ada");
    System.out.println("userExist? = " + userExist);
    ```

9. Delete the user using the `deleteById` method

    ```java
    repository.deleteById("ada");
    ```

10. Check if an user still exists using the `existsById` method from the repository, and print out the result

    ```java
    userExist = repository.existsById("ada");
    System.out.println("userExist? = " + userExist);
    ```

11. Define a private constructor for the `AppRepository` class to prevent instantiation since it contains only static methods:

    ```java
    private AppRepository() {
    }
    ```

12. Run the `main()` method

### :material-checkbox-multiple-outline: Expected results

* The following output

    ```
    Optional[User{userName='ada', name='Ada Lovelace', settings={currency=food}, languages=[Latin]}]
    userExist? = true
    userExist? = false
    ```

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;
    import java.util.Map;
    import java.util.Optional;
    import java.util.Set;

    public class AppRepository {

        public static void main(String[] args) {

            try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

                UserRepository repository = container.select(UserRepository.class).get();
                User ada = User.builder().username("ada").name("Ada Lovelace")
                    .languages(Set.of("Latin")).settings(Map.of("currency", "food")).build();

                repository.save(ada);

                Optional<User> userFound = repository.findById("ada");
                System.out.println(userFound);

                boolean userExist = repository.existsById("ada");
                System.out.println("userExist? = " + userExist);

                repository.deleteById("ada");
                userExist = repository.existsById("ada");
                System.out.println("userExist? = " + userExist);
            }
        }

        private AppRepository() {
        }
    }
    ```
