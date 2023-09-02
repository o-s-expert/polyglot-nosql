# MongoDB and Java Lab


## 1. The fisrt integration

In this lab, you will create a Java application that interacts with a NoSQL database using Jakarta NoSQL and Jakarta EE. The application will define an entity class `Book` to represent books and use a `DocumentTemplate` to perform database operations. Below are the step-by-step instructions for creating these classes:

material-play-box-multiple-outline: Steps

**Step 1: Define the `Book` Entity Class (`Book.java`)**

- Create an entity class named `Book` and annotate it with `@Entity`. This annotation marks the class as an entity that can be persisted in a NoSQL database.

```java
import com.github.javafaker.Faker;
import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

@Entity
public record Book(@Id String isbn, @Column String title, @Column int edition, @Column int year) {

    public static Book of(Faker faker) {
        return new Book(faker.code().isbn13(), faker.book().title(), faker.number().numberBetween(1, 10),
                faker.number().numberBetween(1900, 2022));
    }
}
```

- The `Book` class represents a book with fields such as `isbn`, `title`, `edition`, and `year`. These fields are annotated with `@Id` and `@Column` to specify their roles in the database.

**Step 2: Create the Main Application Class (`App.java`)**

- Create a Java class named `App` which will contain the `main` method and serve as the entry point for your application.

```java
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.nosql.document.DocumentTemplate;

import java.util.Optional;
import java.util.UUID;

public class App {
    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            // The application logic will be placed here
        }
    }

    private App() {
    }
}
```

**Step 3: Import Dependencies**

- Import the necessary classes and libraries at the beginning of your `App` class. You will need classes from Jakarta EE, Jakarta NoSQL, and the `javafaker` library.

**Step 4: Jakarta SE Container Initialization**

- Inside the `try` block, create an instance of the Jakarta SE container using `SeContainerInitializer.newInstance().initialize()`. This initializes the CDI (Contexts and Dependency Injection) container.

**Step 5: Create a `Book` Instance and Save it**

- Create an instance of the `Book` class with some sample data. You can use `UUID.randomUUID().toString()` for the `isbn`, "Effective Java" for the title, `1` for the edition, and `2019` for the year.

```java
Book book = new Book(UUID.randomUUID().toString(), "Effective Java", 1, 2019);
```

- Obtain a `DocumentTemplate` instance from the Jakarta SE container by selecting it: `container.select(DocumentTemplate.class).get()`.

- Use the `DocumentTemplate` to insert the `book` instance into the NoSQL database and retrieve the saved book.

```java
DocumentTemplate template = container.select(DocumentTemplate.class).get();
Book saved = template.insert(book);
System.out.println("Book saved: " + saved);
```

**Step 6: Retrieve a Book by Title**

- Use the `DocumentTemplate` to perform a query to retrieve a book by its title ("Effective Java").

```java
Optional<Book> bookOptional = template.select(Book.class)
        .where("title").eq("Effective Java")
        .singleResult();

System.out.println("Entity found: " + bookOptional);
```

**Step 7: Close the Jakarta SE Container**

- Ensure that the Jakarta SE container is properly closed using a try-with-resources block to avoid resource leaks.

By following these steps, you will create a functioning Jakarta NoSQL-based application to manage and interact with `Book` entities in a NoSQL database. The key takeaways from this lab include defining entities, using a `DocumentTemplate` for database operations, and performing queries on NoSQL data.

### :material-check-outline: Solution

??? example "Click to see..."

```java

import com.github.javafaker.Faker;
import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

@Entity
public record Book(@Id String isbn, @Column String title, @Column int edition, @Column int year) {

    public static Book of(Faker faker) {
        return new Book(faker.code().isbn13(), faker.book().title(), faker.number().numberBetween(1, 10),
                faker.number().numberBetween(1900, 2022));
    }
}


import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.nosql.document.DocumentTemplate;

import java.util.Optional;
import java.util.UUID;

public class App {


    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

            Book book = new Book(UUID.randomUUID().toString(), "Effective Java", 1, 2019);
            DocumentTemplate template = container.select(DocumentTemplate.class).get();
            Book saved = template.insert(book);
            System.out.println("Book saved" + saved);

            Optional<Book> bookOptional = template.select(Book.class)
                    .where("title").eq("Effective Java").singleResult();
            System.out.println("Entity found: " + bookOptional);

        }
    }

    private App() {
    }
}
```


## 2. Pagination

In this lab, you will create a Java application that utilizes Jakarta Data's repository framework to manage a library of books. The application will allow you to save books, retrieve books by title, and perform pageable queries on the book data. Below are the step-by-step instructions for creating these classes:

material-play-box-multiple-outline: Steps


**Step 1: Define the Library Repository Interface (`Library.java`)**

- Create an interface called `Library` and annotate it with `@Repository`. This annotation indicates that it's a repository for managing entities.

```java
import jakarta.data.repository.PageableRepository;
import jakarta.data.repository.Repository;

import java.util.List;

@Repository
public interface Library extends PageableRepository<Book, String> {

    List<Book> findByTitle(String title);
}
```

- Extend the `PageableRepository<Book, String>` interface, specifying that it's a repository for managing `Book` entities with `String` as the type of the ID field.

- Define a custom query method `List<Book> findByTitle(String title)` to retrieve books by their title.

**Step 2: Create the Main Application Class (`App2.java`)**

- Create a Java class called `App2` which will contain the `main` method and serve as the entry point for your application.

```java
import com.github.javafaker.Faker;
import jakarta.data.repository.Page;
import jakarta.data.repository.Pageable;
import jakarta.data.repository.Sort;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class App2 {
    public static void main(String[] args) {
        Faker faker = new Faker();
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            // The application logic will be placed here
        }
    }

    private App2() {
    }
}
```

**Step 3: Import Dependencies**

- Import the necessary classes and libraries at the beginning of your `App2` class. You will need classes from Jakarta Data, Jakarta Enterprise, and a library called `javafaker`.

**Step 4: Jakarta SE Container Initialization**

- Inside the `try` block, create an instance of the Jakarta SE container using `SeContainerInitializer.newInstance().initialize()`. This initializes the CDI (Contexts and Dependency Injection) container.

**Step 5: Obtain the Library Repository Instance**

- Using the Jakarta SE container, obtain an instance of the `Library` repository by selecting it: `container.select(Library.class).get()`.

**Step 6: Populate the Library with Data**

- Inside a loop (in this case, 100 iterations), generate random book data using the `Book.of(faker)` method and save each book to the library using `library.save(book)`.

**Step 7: Define Pageable Options**

- Create a `Pageable` object named `pageable` using `Pageable.ofSize(10).sortBy(Sort.asc("title"), Sort.desc("year"))`. This sets options for retrieving books in pages of size 10, sorted by title in ascending order and year in descending order.

**Step 8: Retrieve and Print Page 1**

- Use `library.findAll(pageable)` to retrieve the first page of books and store it in the `page` variable.
- Print the content of the first page using `page.content()`.

**Step 9: Retrieve and Print Page 2**

- Create a second `Pageable` object named `pageable2` to retrieve the next page of books.
- Use `library.findAll(pageable2)` to retrieve the second page of books and store it in the `page2` variable.
- Print the content of the second page using `page2.content()`.

**Step 10: Custom Query**

- Perform a custom query using `library.findByTitle("Effective Java")` to retrieve books with the title "Effective Java."
- Iterate through the results and print each book.

**Step 11: Close the Jakarta SE Container**

- Ensure that the Jakarta SE container is properly closed using a try-with-resources block to avoid resource leaks.

By following these steps, you will create a functioning Jakarta Data-based application to manage a library of books. The key takeaways from this lab include setting up Jakarta Data repositories, performing pageable queries, and creating custom query methods.


### :material-check-outline: Solution

??? example "Click to see..."

```java

import jakarta.data.repository.PageableRepository;
import jakarta.data.repository.Repository;

import java.util.List;

@Repository
public interface Library extends PageableRepository<Book, String> {

    List<Book> findByTitle(String title);
}



import com.github.javafaker.Faker;
import jakarta.data.repository.Page;
import jakarta.data.repository.Pageable;
import jakarta.data.repository.Sort;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class App2 {


    public static void main(String[] args) {
        Faker faker = new Faker();
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

            Library library = container.select(Library.class).get();

            for (int index = 0; index < 100; index++) {
                Book book = Book.of(faker);
                library.save(book);
            }

            Pageable pageable = Pageable.ofSize(10).sortBy(Sort.asc("title"),
                    Sort.desc("year"));

            Page<Book> page = library.findAll(pageable);
            System.out.println("Page: " + page.content());
            var pageable2 = pageable.next();
            var page2 = library.findAll(pageable2);
            System.out.println("Page 2: " + page2.content());
            System.out.println("The result: ");
            library.findByTitle("Effective Java").forEach(System.out::println);

        }
    }

    private App2() {
    }
}

```


## 3. Subdocuments

In this lab, you will create Java classes that model an Author entity along with related classes for building Author objects, representing addresses, and describing jobs. These classes will be used to interact with a NoSQL database using Jakarta NoSQL and Jakarta EE. Below are the step-by-step instructions for creating these classes

material-play-box-multiple-outline: Steps



**Step 1: Define the `Author` Entity Class (`Author.java`)**

- Create an entity class named `Author` and annotate it with `@Entity`. This annotation marks the class as an entity that can be persisted in a NoSQL database.

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

    // Constructors, getters, setters, and other methods
}
```

- The `Author` class represents an author with fields such as `id`, `name`, `phones`, `address`, and `job`. These fields are annotated with `@Id` and `@Column` to specify their roles in the database.

**Step 2: Create the Author Builder Class (`AuthorBuilder.java`)**

- Create a builder class named `AuthorBuilder` for constructing `Author` objects with different properties.

```java
import java.util.List;

public class AuthorBuilder {

    private long id;
    private String name;
    private List<String> phones;
    private Address address;
    private Job job;

    public AuthorBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public AuthorBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public AuthorBuilder withPhones(List<String> phones) {
        this.phones = phones;
        return this;
    }

    public AuthorBuilder withAddress(Address address) {
        this.address = address;
        return this;
    }

    public AuthorBuilder withJob(Job job) {
        this.job = job;
        return this;
    }

    public Author build() {
        return new Author(id, name, phones, address, job);
    }
}
```

- The `AuthorBuilder` class allows you to set individual properties of an `Author` and build an `Author` object with those properties.

**Step 3: Define the `Address` Embeddable Class (`Address.java`)**

- Create a class named `Address` and annotate it as `@Embeddable`. This class represents an embeddable component within the `Author` entity.

```java
import jakarta.nosql.Column;
import org.eclipse.jnosql.mapping.Embeddable;
import java.util.Objects;

@Embeddable
public class Address {

    @Column
    private String street;

    @Column
    private String city;

    // Constructors, getters, setters, and other methods
}
```

- The `Address` class contains `street` and `city` fields, which are annotated with `@Column`.

**Step 4: Define the `Job` Embeddable Class (`Job.java`)**

- Create a class named `Job` and annotate it as `@Embeddable`. This class represents an embeddable component within the `Author` entity.

```java
import jakarta.nosql.Column;
import org.eclipse.jnosql.mapping.Embeddable;
import java.util.Objects;

@Embeddable
public class Job {

    @Column
    private double salary;

    @Column
    private String occupation;

    // Constructors, getters, setters, and other methods
}
```

- The `Job` class contains `salary` and `occupation` fields, which are annotated with `@Column`.

**Step 5: Create the Main Application Class (`App3.java`)**

- Create a Java class named `App3` which will contain the `main` method and serve as the entry point for your application.

```java
import com.github.javafaker.Faker;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.nosql.document.DocumentTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class App3 {
    public static void main(String[] args) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        long id = random.nextLong();
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            // The application logic will be placed here
        }
    }
}
```

**Step 6: Import Dependencies**

- Import the necessary classes and libraries at the beginning of your `App3` class. You will need classes from Jakarta EE, Jakarta NoSQL, and the `javafaker` library.

**Step 7: Jakarta SE Container Initialization**

- Inside the `try` block, create an instance of the Jakarta SE container using `SeContainerInitializer.newInstance().initialize()`. This initializes the CDI (Contexts and Dependency Injection) container.

**Step 8: Build an `Author` Object and Save it**

- Use the `Faker` library to generate random data for an `Address` and a `Job`.

- Use the `AuthorBuilder` class to build an `Author` object with the generated data.

- Obtain a `DocumentTemplate` instance from the Jakarta SE container by selecting it: `container.select(DocumentTemplate.class).get()`.

- Use the `DocumentTemplate` to insert the `Author` instance into the NoSQL database and retrieve the saved author.

```java
DocumentTemplate template = container.select(DocumentTemplate.class).get();
Author saved = template.insert(author);
System.out.println("Author saved: " + saved);
```

**Step 9: Retrieve Authors by City**

- Use the `DocumentTemplate` to perform a query to retrieve authors by the city in their address.

```java
List<Author> authorsInCity = template.select

(Author.class)
        .where("address.city").eq(address.getCity())
        .result();

System.out.println("Authors in the city: " + authorsInCity);
```

**Step 10: Close the Jakarta SE Container**

- Ensure that the Jakarta SE container is properly closed using a try-with-resources block to avoid resource leaks.

By following these steps, you will create a functioning Jakarta NoSQL-based application to manage and interact with `Author` entities in a NoSQL database. The key takeaways from this lab include defining entities, using a `DocumentTemplate` for database operations, and performing queries on NoSQL data.

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

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public List<String> getPhones() {
        return phones;
    }


    public Author() {
    }

    Author(long id, String name, List<String> phones, Address address, Job job) {
        this.id = id;
        this.name = name;
        this.phones = phones;
        this.address = address;
        this.job = job;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phones=" + phones +
                ", address=" + address +
                ", job=" + job +
                '}';
    }

    public static AuthorBuilder builder() {
        return new AuthorBuilder();
    }
}

import java.util.List;


public class AuthorBuilder {

    private long id;

    private String name;

    private List<String> phones;

    private Address address;

    private Job job;

    public AuthorBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public AuthorBuilder withName(String name) {
        this.name = name;
        return this;
    }


    public AuthorBuilder withPhones(List<String> phones) {
        this.phones = phones;
        return this;
    }

    public AuthorBuilder withAddress(Address address) {
        this.address = address;
        return this;
    }

    public AuthorBuilder withJob(Job job) {
        this.job = job;
        return this;
    }

    public Author build() {
        return new Author(id, name, phones, address, job);
    }
}



import jakarta.nosql.Column;
import jakarta.nosql.Entity;

import java.util.Objects;

@Entity
public class Address {

    @Column
    private String street;

    @Column
    private String city;


    Address(String street, String city) {
        this.street = street;
        this.city = city;
    }

    Address() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(street, address.street) &&
                Objects.equals(city, address.city);
    }

    @Override
    public int hashCode() {

        return Objects.hash(street, city);
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}


import jakarta.nosql.Column;
import org.eclipse.jnosql.mapping.Embeddable;

import java.util.Objects;

@Embeddable
public class Job {

    @Column
    private double salary;
    @Column
    private String occupation;

    Job(double salary, String occupation) {
        this.salary = salary;
        this.occupation = occupation;
    }

    public Job() {
    }

    public double getSalary() {
        return salary;
    }

    public String getOccupation() {
        return occupation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Job)) {
            return false;
        }
        Job job = (Job) o;
        return Double.compare(job.salary, salary) == 0 &&
                Objects.equals(occupation, job.occupation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salary, occupation);
    }

    @Override
    public String toString() {
        return "Job{" +
                "salary=" + salary +
                ", occupation='" + occupation + '\'' +
                '}';
    }
}


import com.github.javafaker.Faker;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.nosql.document.DocumentTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class App3 {


    public static void main(String[] args) {

        ThreadLocalRandom random = ThreadLocalRandom.current();
        long id = random.nextLong();
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            Faker faker = new Faker();
            Address address = new Address(faker.address().streetName(), faker.address().city());
            Job job = new Job(12.12, faker.job().title());
            Author author = Author.builder().
                    withPhones(Arrays.asList(faker.phoneNumber().cellPhone(), faker.phoneNumber().cellPhone()))
                    .withName(faker.name().fullName())
                    .withAddress(address)
                    .withJob(job)
                    .withId(id).build();

            DocumentTemplate template = container.select(DocumentTemplate.class).get();
            Author saved = template.insert(author);
            System.out.println("Person saved" + saved);

            List<Author> people = template.select(Author.class)
                    .where("address.city").eq(address.getCity()).result();

            System.out.println("Entities found: " + people);

        }
    }

    private App3() {
    }
}

```


## 3. Subdocuments with Repositories

In this lab, you will create Java classes for defining a repository interface and a main application class. The repository interface (`AuthorRepository`) will extend `PageableRepository` for data access, and the main application (`App4`) will demonstrate how to use this repository to interact with a NoSQL database using Jakarta Data. Below are the step-by-step instructions for creating these classes:

material-play-box-multiple-outline: Steps

In this lab, you will create Java classes for defining a repository interface and a main application class. The repository interface (`AuthorRepository`) will extend `PageableRepository` for data access, and the main application (`App4`) will demonstrate how to use this repository to interact with a NoSQL database using Jakarta Data. Below are the step-by-step instructions for creating these classes:

**Step 1: Define the `AuthorRepository` Interface (`AuthorRepository.java`)**

- Create an interface named `AuthorRepository` and annotate it with `@Repository`. This annotation marks the interface as a repository.

```java
import jakarta.data.repository.PageableRepository;
import java.util.List;
import java.util.stream.Stream;

@Repository
public interface AuthorRepository extends PageableRepository<Author, Long> {
    List<Author> findByName(String name);

    Stream<Author> findByPhones(String phone);
}
```

- The `AuthorRepository` interface extends `PageableRepository`, which provides paging and sorting functionality for querying the repository. It defines two methods for finding authors by name and phones.

**Step 2: Create the Main Application Class (`App4.java`)**

- Create a Java class named `App4` which will contain the `main` method and serve as the entry point for your application.

```java
import com.github.javafaker.Faker;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class App4 {
    public static void main(String[] args) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            // The application logic will be placed here
        }
    }

    private App4() {
    }
}
```

**Step 3: Import Dependencies**

- Import the necessary classes and libraries at the beginning of your `App4` class. You will need classes from Jakarta EE, Jakarta Data, Jakarta Validation, and the `javafaker` library.

**Step 4: Jakarta SE Container Initialization**

- Inside the `try` block, create an instance of the Jakarta SE container using `SeContainerInitializer.newInstance().initialize()`. This initializes the CDI (Contexts and Dependency Injection) container.

**Step 5: Build an `Author` Object and Save it**

- Use the `Faker` library to generate random data for an `Address` and a `Job`.

- Use the `AuthorBuilder` class (which is not shown but is assumed to exist based on your previous code) to build an `Author` object with the generated data.

- Obtain an instance of the `AuthorRepository` from the Jakarta SE container by selecting it: `container.select(AuthorRepository.class).get()`.

- Use the `AuthorRepository` to save the `Author` instance into the NoSQL database.

```java
AuthorRepository repository = container.select(AuthorRepository.class).get();
repository.save(author);
```

**Step 6: Query the Repository**

- Use the `AuthorRepository` to query the database and retrieve authors based on their name and phones.

```java
System.out.println("Authors with the same name: " + repository.findByName(author.getName()));

System.out.println("Authors with the same phone: " + repository.findByPhones(author.getPhones().get(0)).toList());
```

**Step 7: Close the Jakarta SE Container**

- Ensure that the Jakarta SE container is properly closed using a try-with-resources block to avoid resource leaks.

By following these steps, you will create a functioning Jakarta Data-based application to manage and interact with `Author` entities in a NoSQL database. The key takeaways from this lab include defining repository interfaces, using `PageableRepository` for data access, and performing queries on data.


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


import com.github.javafaker.Faker;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class App4 {


    public static void main(String[] args) {

        ThreadLocalRandom random = ThreadLocalRandom.current();
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            Faker faker = new Faker();
            Address address = new Address(faker.address().streetName(), faker.address().city());
            Job job = new Job(12.12, faker.job().title());
            Author author = Author.builder().
                    withPhones(Arrays.asList(faker.phoneNumber().cellPhone(), faker.phoneNumber().cellPhone()))
                    .withName(faker.name().fullName())
                    .withAddress(address)
                    .withJob(job)
                    .withId(random.nextLong()).build();

            AuthorRepository template = container.select(AuthorRepository.class).get();
             template.save(author);

            System.out.println("The entity find: " + template.findByName(author.getName()));
            System.out.println("The entity find: " + template.findByPhones(author.getPhones().get(0)).toList());

        }
    }

    private App4() {
    }
}


```