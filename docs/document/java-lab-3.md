# MongoDB - Lab 4

In this lab, you will create Java classes that model an Author entity along with related classes for building Author objects, representing addresses, and describing jobs. These classes will be used to interact with a NoSQL database using Jakarta NoSQL and Jakarta EE. Below are the step-by-step instructions for creating these classes

## 1. Define the `Address` Embeddable Class

### :material-play-box-multiple-outline: Steps

1. Create a class called `Address` in the `expert.os.labs.persistence` package
2. Annotate it using the `@Embeddable` from the `org.eclipse.jnosql.mapping` package
3. Add two attributes: `city` and `street` and use the `@Column` annotation, both as `String`

    ```java
    @Column
    private String street;

    @Column
    private String city;
    ```

4. Implement constructors, getter methods, `equals`, `hashCode`, and `toString` methods

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.nosql.Column;
    import org.eclipse.jnosql.mapping.Embeddable;

    @Embeddable
    public class Address {

        @Column
        private String street;

        @Column
        private String city;

        public Address() {
        }

        public Address(String street, String city) {
            this.street = street;
            this.city = city;
        }

        // other method ignored
    }    
    ```

## 2. Define the `Job` Embeddable Class

### :material-play-box-multiple-outline: Steps

1. Create a class called `Job` in the `expert.os.labs.persistence` package
2. Annotate it using the `@Embeddable` from the `org.eclipse.jnosql.mapping` package
3. Add two attibutes: `salary` and `occupation` and use the `@Column` annotation, where `salary` is a `double` and `occupation` a `String`

    ```java
    @Column
    private double salary;

    @Column
    private String occupation;
    ```

4. Implement constructors, getter methods, `equals`, `hashCode`, and `toString` methods

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.nosql.Column;
    import org.eclipse.jnosql.mapping.Embeddable;

    @Embeddable
    public class Job {

        @Column
        private double salary;

        @Column
        private String occupation;

        public Job() {
        }

        public Job(double salary, String occupation) {
            this.salary = salary;
            this.occupation = occupation;
        }

        // other method ignored
    }    
    ```

## 3. Define the `Author` Entity Class

### :material-play-box-multiple-outline: Steps

1. Create a class called `Author` in the `expert.os.labs.persistence` package
2. Define the class with the `@Entity` annotation from the `jakarta.nosql` package
3. Add the following fields in in the record:

    | Annotation | Type | Name |
    |--|--|--|
    | `@Id` | `Long` | `id` |
    | `@Colum` | `String` | `name` | 
    | `@Colum` | `List<String>` | `phones` | 
    | `@Colum` | `Address` | `address` | 
    | `@Colum` | `Job` | `job` | 

4. Implement constructors, getter methods, `equals`, `hashCode`, and `toString` methods 

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.nosql.Column;
    import jakarta.nosql.Entity;
    import jakarta.nosql.Id;

    import java.util.List;

    @Entity
    public class Author {

        @Id
        private Long id;

        @Column
        private String name;

        @Column
        private List<String> phones;

        @Column
        private Address address;

        @Column
        private Job job;

        public Author() {
        }

        public Author(Long id, String name, List<String> phones, Address address, Job job) {
            this.id = id;
            this.name = name;
            this.phones = phones;
            this.address = address;
            this.job = job;
        }

        // other method ignored
    }  
    ```

## 4. Implement the Builder class

### :material-play-box-multiple-outline: Steps

1. Create a class called `AuthorBuilder` in the `expert.os.labs.persistence` package
2. Add the same fields we had previously added to the `Author` class, without the annotations
    
    ```java
    private long id;
    private String name;
    private List<String> phones;
    private Address address;
    private Job job;
    ```

3. Add the builder methods for each field
4. Add the `build()` method creating a new instance of `Author` using its constructor
5. In the `Author` class add the builder method referring to the `AuthorBuilder`

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import java.util.List;

    public class AuthorBuilder {

        private long id;

        private String name;

        private List<String> phones;

        private Address address;

        private Job job;

        public AuthorBuilder id(long id) {
            this.id = id;
            return this;
        }

        public AuthorBuilder name(String name) {
            this.name = name;
            return this;
        }

        public AuthorBuilder phones(List<String> phones) {
            this.phones = phones;
            return this;
        }

        public AuthorBuilder address(Address address) {
            this.address = address;
            return this;
        }

        public AuthorBuilder job(Job job) {
            this.job = job;
            return this;
        }

        public Author build() {
            return new Author(id, name, phones, address, job);
        }
    }
    ```

## 5. Create the execution class

### :material-play-box-multiple-outline: Steps

1. Create a class called `AppMongoDBSubdocuments` in the `expert.os.labs.persistence` package
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

9. Obtain a `DocumentTemplate` instance from `jakarta.nosql.document` package by selecting it: `container.select(DocumentTemplate.class).get()`

    ```java
    DocumentTemplate template = container.select(DocumentTemplate.class).get();
    ```

10. Use the `DocumentTemplate` to insert the `Author` instance into the NoSQL database and retrieve the saved author

    ```java
    Author saved = template.insert(author);
    ```

11. Printout the saved author

    ```java
    System.out.println("Author saved = " + saved);
    ```

12. Use the `DocumentTemplate` to perform a query to retrieve authors by the city in their address

    ```java
    List<Author> people = template.select(Author.class).where("address.city").eq(address.getCity()).result();
    ```

13. Printout the result

    ```java
    System.out.println("Entities found: " + people);
    ```

13. Define a private constructor for the `AppCassandraOperations` class to prevent instantiation since it contains only static methods:

    ```java
    private AppMongoDBSubdocuments() {
    }
    ```

14. Run the `main()` method    

### :material-checkbox-multiple-outline: Expected results

* The following output
   
    ```
    Author saved = Author{id=-5926041563979906242, name='Marissa Koch Jr.', phones=[347-971-6761, 1-199-518-8887], address=Address{street='Roselee Ports', city='Stantonport'}, job=Job{salary=12.12, occupation='Legal Assistant'}}

    Entities found: [Author{id=-5926041563979906242, name='Marissa Koch Jr.', phones=[347-971-6761, 1-199-518-8887], address=Address{street='Roselee Ports', city='Stantonport'}, job=Job{salary=12.12, occupation='Legal Assistant'}}]
    ```

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import com.github.javafaker.Faker;
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;
    import jakarta.nosql.document.DocumentTemplate;

    import java.util.Arrays;
    import java.util.List;
    import java.util.concurrent.ThreadLocalRandom;

    public class AppMongoDBSubdocuments {

        public static void main(String[] args) {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            long id = random.nextLong();

            try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
                Faker faker = new Faker();

                Address address = new Address(faker.address().streetName(), faker.address().city());
                Job job = new Job(12.12, faker.job().title());
                Author author = Author.builder()
                    .phones(Arrays.asList(faker.phoneNumber().cellPhone(), faker.phoneNumber().cellPhone()))
                    .name(faker.name().fullName()).address(address).job(job).id(id).build();

                DocumentTemplate template = container.select(DocumentTemplate.class).get();

                Author saved = template.insert(author);
                System.out.println("Author saved = " + saved);

                List<Author> people = template.select(Author.class).where("address.city").eq(address.getCity()).result();
                System.out.println("Entities found: " + people);
            }
        }

        private AppMongoDBSubdocuments() {
        }
    }
    ```
