# Neo4J and Java Lab


## 1. The fisrt integration

In this lab, we will explor more about Neo4J and Java.


material-play-box-multiple-outline: Steps


**Step 1: Create the `Book` Class**

```java
import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;
import java.util.Objects;

@Entity
public class Book {

    @Id
    private Long id;

    @Column
    private String name;

    // Constructor and methods for Book class
}
```

- This class represents a book entity with an `id` (unique identifier) and a `name`.
- It is annotated with `@Entity`, `@Id`, and `@Column` from Jakarta NoSQL to define it as an entity and specify the primary key and columns.
- The `equals`, `hashCode`, and `toString` methods are overridden for custom behavior.
- A static factory method `of` is provided for creating Book instances.

**Step 2: Create the `Category` Class**

```java
import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;
import java.util.Objects;

@Entity
public class Category {

    @Id
    private Long id;

    @Column
    private String name;

    // Constructor and methods for Category class
}
```

- This class represents a category entity with an `id` (unique identifier) and a `name`.
- Similar to the `Book` class, it is annotated with `@Entity`, `@Id`, and `@Column`.
- It also includes custom `equals`, `hashCode`, and `toString` methods, as well as a static factory method `of`.

**Step 3: Create the `LibraryLabels` Enum**

```java
import java.util.function.Supplier;

public enum LibraryLabels implements Supplier<String> {

    IS("is");

    private final String value;

    LibraryLabels(String value) {
        this.value = value;
    }

    @Override
    public String get() {
        return value;
    }
}
```

- This enum defines a single value `IS` with the value `"is"`.
- It implements the `Supplier<String>` interface to provide a custom label for the relationship between books and categories.


**Step 4: Create the `BookService` Class**

```java
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.jnosql.mapping.graph.GraphTemplate;

import java.util.List;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class BookService {

    @Inject
    private GraphTemplate graph;

    Category category(String name) {
        return graph.traversalVertex().hasLabel(Category.class)
                .has("name", name)
                .<Category>next()
                .orElseGet(() -> graph.insert(Category.of(name)));
    }

    Book book(String name) {
        return graph.traversalVertex().hasLabel(Book.class)
                .has("name", name)
                .<Book>next()
                .orElseGet(() -> graph.insert(Book.of(name)));
    }

    public void category(Book book, Category category){
        this.graph.edge(book, LibraryLabels.IS, category);
    }

    public void category(Category subCategory, Category category){
        this.graph.edge(subCategory, LibraryLabels.IS, category);
    }

    public List<String> softwareCategories(){
        return graph.traversalVertex().hasLabel(Category.class)
                .has("name", "Software")
                .in(LibraryLabels.IS).hasLabel("Category").<Category>result()
                .map(Category::getName)
                .collect(toList());
    }

    public List<String> softwareBooks(){
        return graph.traversalVertex().hasLabel(Category.class)
                .has("name", "Software")
                .in(LibraryLabels.IS).hasLabel(Book.class).<Book>result()
                .map(Book::getName)
                .collect(toList());
    }

    public List<String> softwareNoSQLBooks(){
        return graph.traversalVertex().hasLabel(Category.class)
                .has("name", "Software")
                .in(LibraryLabels.IS)
                .has("name", "NoSQL")
                .in(LibraryLabels.IS).<Book>result()
                .map(Book::getName)
                .collect(toList());
    }
}
```

**Step 5: Understanding the `BookService` Class**

- `@ApplicationScoped`: This annotation makes the `BookService` class an application-scoped bean, allowing it to be managed by the Jakarta EE container.

- `@Inject private GraphTemplate graph`: The `GraphTemplate` is injected into the class, allowing you to interact with the underlying graph database.

- `Category category(String name)`: This method takes a category name as input and retrieves or inserts a `Category` entity with the given name in the graph database.

- `Book book(String name)`: Similar to the `category` method, this method retrieves or inserts a `Book` entity with the given name in the graph database.

- `public void category(Book book, Category category)`: This method establishes a relationship between a `Book` and a `Category` using an edge labeled as `LibraryLabels.IS`.

- `public void category(Category subCategory, Category category)`: This method establishes a relationship between two `Category` entities, indicating that `subCategory` is a subcategory of `category`.

- `public List<String> softwareCategories()`: This method retrieves a list of category names that are related to "Software."

- `public List<String> softwareBooks()`: This method retrieves a list of book names that are related to "Software."

- `public List<String> softwareNoSQLBooks()`: This method retrieves a list of book names that are related to both "Software" and "NoSQL."

**Step 6: Using the `BookService` Class**

You can use the `BookService` class in your application by injecting it and calling its methods as needed. Here's an example of how to use it:

```java
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.util.List;

public final class BookApp {

    private BookApp() {
    }

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

            BookService service = container.select(BookService.class).get();

            // Use the methods provided by the BookService to manage categories and books
            Category softwareCategory = service.category("Software");
            Book effectiveJavaBook = service.book("Effective Java");

            service.category(effectiveJavaBook, softwareCategory);

            List<String> softwareCategories = service.softwareCategories();
            List<String> softwareBooks = service.softwareBooks();
            List<String> softwareNoSQLBooks = service.softwareNoSQLBooks();

            System.out.println("Software Categories: " + softwareCategories);
            System.out.println("Software Books: " + softwareBooks);
            System.out.println("Software and NoSQL Books: " + softwareNoSQLBooks);
        }
    }
}
```

In this example, we initialize a Jakarta SE container, obtain an instance of the `BookService` class, and then use its methods to manage categories and books in the graph database.


### :material-check-outline: Solution

??? example "Click to see..."

```java

import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

import java.util.Objects;

@Entity
public class Book {


    @Id
    private Long id;

    @Column
    private String name;


    Book() {
    }

    private Book(String name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        Book traveler = (Book) o;
        return Objects.equals(id, traveler.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static Book of(String name) {
        return new Book(name);
    }
}


import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

import java.util.Objects;

@Entity
public class Category {


    @Id
    private Long id;

    @Column
    private String name;


    Category() {
    }

    private Category(String name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        Category traveler = (Category) o;
        return Objects.equals(id, traveler.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static Category of(String name) {
        return new Category(name);
    }
}


import java.util.function.Supplier;

public enum LibraryLabels implements Supplier<String> {

    IS("is");

    private final String value;

    LibraryLabels(String value) {
        this.value = value;
    }

    @Override
    public String get() {
        return value;
    }
}


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.jnosql.mapping.graph.GraphTemplate;

import java.util.List;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class BookService {

    @Inject
    private GraphTemplate graph;

    Category category(String name) {
        return graph.traversalVertex().hasLabel(Category.class)
                .has("name", name)
                .<Category>next()
                .orElseGet(() -> graph.insert(Category.of(name)));
    }

    Book book(String name) {
        return graph.traversalVertex().hasLabel(Book.class)
                .has("name", name)
                .<Book>next()
                .orElseGet(() -> graph.insert(Book.of(name)));
    }

    public void category(Book book, Category category){
        this.graph.edge(book, LibraryLabels.IS, category);
    }

    public void category(Category subCategory, Category category){
        this.graph.edge(subCategory, LibraryLabels.IS, category);
    }

    public List<String> softwareCategories(){
        return graph.traversalVertex().hasLabel(Category.class)
                .has("name", "Software")
                .in(LibraryLabels.IS).hasLabel("Category").<Category>result()
                .map(Category::getName)
                .collect(toList());
    }

    public List<String> softwareBooks(){
        return graph.traversalVertex().hasLabel(Category.class)
                .has("name", "Software")
                .in(LibraryLabels.IS).hasLabel(Book.class).<Book>result()
                .map(Book::getName)
                .collect(toList());
    }

    public List<String> softwareNoSQLBooks(){
        return graph.traversalVertex().hasLabel(Category.class)
                .has("name", "Software")
                .in(LibraryLabels.IS)
                .has("name", "NoSQL")
                .in(LibraryLabels.IS).<Book>result()
                .map(Book::getName)
                .collect(toList());
    }
}


import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.util.List;

public final class BookApp {

    private BookApp() {
    }

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

            BookService service = container.select(BookService.class).get();

            Category software = service.category("Software");
            Category romance = service.category("Romance");
            Category java = service.category("Java");
            Category nosql =service.category("NoSQL");
            Category microService =service.category("Micro Service");

            Book effectiveJava = service.book("Effective Java");
            Book nosqlDistilled = service.book("NoSQL Distilled");
            Book migratingMicroservice = service.book("Migrating to Microservice Databases");
            Book shack = service.book("The Shack");


            service.category(java, software);
            service.category(nosql, software);
            service.category(microService, software);

            service.category(effectiveJava, software);
            service.category(nosqlDistilled, software);
            service.category(migratingMicroservice, software);

            service.category(effectiveJava, java);
            service.category(nosqlDistilled, nosql);
            service.category(migratingMicroservice, microService);

            service.category(shack, romance);

            List<String> softwareCategories =service.softwareCategories();

            List<String> softwareBooks = service.softwareBooks();

            List<String> softwareNoSQLBooks = service.softwareNoSQLBooks();


            System.out.println("The software categories: " + softwareCategories);
            System.out.println("The software books: " + softwareBooks);
            System.out.println("The software and NoSQL books: " + softwareNoSQLBooks);


        }
    }
}

```