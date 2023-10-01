# Cassandra - Lab 3

In this lab, we will learn how to use Repository with Eclipse JNoSQL.

## 1. Define the Repository interface

### :material-play-box-multiple-outline: Steps

1. Create an interface called `PersonRepository` in the `expert.os.labs.persistence` package
2. Annotate the interface with `@Repository` using the package from `jakarta.data.repository` to indicate it's a Jakarta Data repository
3. Extend the `CrudRepository` interface from the package `jakarta.data.repository` and specify the entity type (`Person`) and the ID type (`Long`) as type parameters. It defines the basic CRUD operations that can be performed on the `Person` entity

    ```java
    @Repository
    public interface PersonRepository extends CrudRepository<Person, Long> {
    }
    ```

### :material-checkbox-multiple-outline: Expected results

* A repository class ready to perform database operations

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.data.repository.CrudRepository;
    import jakarta.data.repository.Repository;

    @Repository
    public interface PersonRepository extends CrudRepository<Person, Long> {
    }
    ```

## 2. Create the execution class for Cassandra Repository

### :material-play-box-multiple-outline: Steps

1. Create a class called `AppCassandraRository` in the `expert.os.labs.persistence` package
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

4. Create two user instances using the builder of the `Person` class with different data inside the `try` statement

    ```java
    Person user1 = Person.builder()
            .contacts(Map.of("twitter", "otaviojava", "linkedin", "otaviojava","youtube", "otaviojava"))
            .name("Otavio Santana").id(1).build();
    ```

5. Obtain an instance of the `PersonRepository` interface using Jakarta EE's `SeContainer`. It is done by selecting and getting an instance of the repository

    ```java
    PersonRepository repository = container.select(PersonRepository.class).get();
    ```

6. Save the user

    ```java
    repository.save(user1);
    ```

7. Retrieve data from the repository by `id` (1L) using the `findById` method where the result is wrapped in an `Optional` to handle the possibility of a non-existent entity

    ```java
    Optional<Person> person = repository.findById(1L);
    ```

8. Print the retrieved entity or indicate if it was not found

    ```java
    System.out.println("Entity found: " + person);
    ```

9. Define a private constructor for the `AppCassandraRepository` class to prevent instantiation since it contains only static methods:

    ```java
    private AppCassandraRepository() {
    }
    ```

10. Run the `main()` method

### :material-checkbox-multiple-outline: Expected results

* The following output
   
    ```
    Entity found: Optional[Person{id=1, name='Otavio Santana', contacts={youtube=otaviojava, twitter=otaviojava, linkedin=otaviojava}}]
    ```

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;

    import java.util.Map;
    import java.util.Optional;

    public class AppCassandraRepository {

        public static void main(String[] args) {
            try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
                Person user1 = Person.builder()
                    .contacts(Map.of("twitter", "otaviojava", "linkedin", "otaviojava","youtube", "otaviojava"))
                    .name("Otavio Santana").id(1).build();

                PersonRepository repository = container.select(PersonRepository.class).get();
                repository.save(user1);

                Optional<Person> person = repository.findById(1L);
                System.out.println("Entity found: " + person);
            }
        }

        private AppCassandraRepository() {
        }
    }
    ```
