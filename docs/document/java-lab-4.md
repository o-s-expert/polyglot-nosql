# MongoDB - Lab 4

In this lab, you will create Java classes for defining a repository interface and a main application class. The repository interface (`AuthorRepository`) will extend `PageableRepository` for data access, and the main application (`App4`) will demonstrate how to use this repository to interact with a NoSQL database using Jakarta Data. Below are the step-by-step instructions for creating these classes:

## 1. Define the `AuthorRepository` Interface

### :material-play-box-multiple-outline: Steps

1. Create an `interface` called `AuthorRepository` in the `expert.os.labs.persistence` package
2. Annotate it with `@Repository` from the `jakarta.data.repository` package
3. Extends the interface with the `PageableRepository` using the `Author` class and `Long`

    ```java
    @Repository
    public interface AuthorRepository extends PageableRepository<Author, Long> {
    }
    ```

4. Add a custom query to find the author by name

    ```java
     List<Author> findByName(String name);
    ```

5. Add a custom query to find a phone number

    ```java
    Stream<Author> findByPhones(String phone);
    ```

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.data.repository.PageableRepository;
    import jakarta.data.repository.Repository;

    import java.util.List;
    import java.util.stream.Stream;

    @Repository
    public interface AuthorRepository extends PageableRepository<Author, Long> {

        List<Author> findByName(String name);

        Stream<Author> findByPhones(String phone);
    }
    ```

## 2. Create the execution class

### :material-play-box-multiple-outline: Steps

1. Create a class called `AppMongoDBSubdocumentRepository` in the `expert.os.labs.persistence` package
2. Add a main method

    ```java
    public static void main(String[] args) {
    }
    ```

3. Create a `ThreadLocalRandom` instance and get the next value, associating it to an attribute

    ```java
    ThreadLocalRandom random = ThreadLocalRandom.current();
    long id = random.nextLong();
    ```

4.  Set up a try-with-resources block, inside the `main` method, to manage the Jakarta EE `SeContainer` that is responsible for dependency injection and managing resources

    ```java
    try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {     
    }
    ```

5. Create a new instance of `Faker`

    ```java
    Faker faker = new Faker();
    ```

6. Create a new instance of the `Author` class using `faker` to provide data

    ```java
    Address address = new Address(faker.address().streetName(), faker.address().city());
    ```

7. Create a new instance of the `Job` class using `faker` to provide data

    ```java
    Job job = new Job(12.12, faker.job().title());
    ```

8. Create a new instance of the `Author` class using `faker` to provide data

    ```java
    Author author = Author.builder()
        .phones(Arrays.asList(faker.phoneNumber().cellPhone(), faker.phoneNumber().cellPhone()))
        .name(faker.name().fullName()).address(address).job(job).id(id).build();
    ```

9. Obtain an instance of the `AuthorRepository` from the Jakarta SE container by selecting it: `container.select(AuthorRepository.class).get()`

    ```java
    AuthorRepository template = container.select(AuthorRepository.class).get();
    ```

10. Use the `AuthorRepository` to save the `Author` instance into the NoSQL database

    ```java
    template.save(author);
    ```

11. Query the authors by name

    ```java
    List<Author> authorByName = template.findByName(author.getName());
    ```

12. Printout the result

    ```java
    System.out.println("Author by name = " + authorByName);
    ```

13. Retrieve the same author by it's phone number

    ```java
    List<Author> list = template.findByPhones(author.getPhones().get(0)).toList();
    ```

14. Printout the result

    ```java
    System.out.println("Author's phone = " + list);
    ```

15. Define a private constructor for the `AppMongoDBSubdocumentRepository` class to prevent instantiation since it contains only static methods:

    ```java
    private AppMongoDBSubdocuments() {
    }
    ```

16. Run the `main()` method    

### :material-checkbox-multiple-outline: Expected results

* The following output
   
    ```
    Author by name = [Author{id=-6736921736259877587, name='Edward Koch', phones=[1-267-083-3844, (367) 346-8130], address=Address{street='Jin Ports', city='North Alysebury'}, job=Job{salary=12.12, occupation='IT Consultant'}}]
    
    Author's phone = [Author{id=-6736921736259877587, name='Edward Koch', phones=[1-267-083-3844, (367) 346-8130], address=Address{street='Jin Ports', city='North Alysebury'}, job=Job{salary=12.12, occupation='IT Consultant'}}]
    ```

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import com.github.javafaker.Faker;
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;

    import java.util.Arrays;
    import java.util.List;
    import java.util.concurrent.ThreadLocalRandom;

    public class AppMongoDBSubdocumentRepository {

        public static void main(String[] args) {

            ThreadLocalRandom random = ThreadLocalRandom.current();
            long id = random.nextLong();

            try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
                Faker faker = new Faker();

                Address address = new Address(faker.address().streetName(), faker.address().city());
                Job job = new Job(12.12, faker.job().title());
                Author author = Author.builder()
                    .phones(Arrays.asList(faker.phoneNumber().cellPhone(), faker.phoneNumber().cellPhone()))
                    .name(faker.name().fullName())
                    .address(address)
                    .job(job)
                    .id(id).build();

                AuthorRepository template = container.select(AuthorRepository.class).get();
                template.save(author);

                List<Author> authorByName = template.findByName(author.getName());
                System.out.println("Author by name = " + authorByName);

                List<Author> list = template.findByPhones(author.getPhones().get(0)).toList();
                System.out.println("Author's phone = " + list);
            }
        }

        private AppMongoDBSubdocumentRepository() {
        }
    }
    ```
