# Neo4J - Lab 2

In this lab, we will explore more about Neo4J and Java.

## 1. Define the `Book` Entity Class

### :material-play-box-multiple-outline: Steps

1. Open the `06-graph` project and navigate to the `src/main/java`
2. Create a class called `Book` in the `expert.os.labs.persistence` package
3. Define the class with the `@Entity` annotation from the `jakarta.nosql` package
4. Add the following `private` fields:
    - `@Id Long id`
    - `@Column String name`

5. Add a static method to create a book by a given `name`

    ```java
    public static Book of(String name) {
        return new Book(name);
    }
    ```

6. Implement constructors, getter methods, `equals`, `hashCode`, and `toString` methods

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.nosql.Column;
    import jakarta.nosql.Entity;
    import jakarta.nosql.Id;

    @Entity
    public class Book {

        @Id
        private Long id;

        @Column
        private String name;

        public Book() {
        }

        public Book(String name) {
            this.name = name;
        }

        public static Book of(String name) {
            return new Book(name);
        }

        // other methods
    }
    ```

## 2. Create the `Category` Class

### :material-play-box-multiple-outline: Steps

1. Create a class called `Category` in the `expert.os.labs.persistence` package
2. Define the class with the `@Entity` annotation from the `jakarta.nosql` package
3. Add the following `private` fields:
    - `@Id Long id`
    - `@Column String name`

4. Add a static method to create a book by a given `name`

    ```java
    public static Category of(String name) {
        return new Category(name);
    }
    ```

5. Implement constructors, getter methods, `equals`, `hashCode`, and `toString` methods

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.nosql.Column;
    import jakarta.nosql.Entity;
    import jakarta.nosql.Id;

    @Entity
    public class Category {

        @Id
        private Long id;

        @Column
        private String name;

        public Category() {
        }

        public Category(String name) {
            this.name = name;
        }

        public static Category of(String name) {
            return new Category(name);
        }

        // other methods
    }
    ```

## 3. Create the `LibraryLabels` Enum

### :material-play-box-multiple-outline: Steps

1. Create an `enum` called `LibraryLabels` in the `expert.os.labs.persistence` package
2. It implements the `Supplier<String>` interface to provide a custom label for the relationship between books and categories
3. Add the constant `IS`
4. Associate a value to the constant with the same name `is`
5. Define a `private` attribute to get the constant value
6. Define a constructor to set the value
7. Override the `get()` method from the `Supplier` returning the value attribute

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import java.util.function.Supplier;

    public enum LibraryLabels implements Supplier<String> {

        IS("is");

        private String value;

        LibraryLabels(String value) {
            this.value = value;
        }

        @Override
        public String get() {
            return value;
        }
    }
    ```

## 4. Create the `BookService` Class

### :material-play-box-multiple-outline: Steps

1. Create a class called `BookService` in the `expert.os.labs.persistence` package
2. Annotate it using the `@ApplicationScoped` from the `jakarta.enterprise.context` package
3. Create a private field with the `GraphTemplate` type from the `org.eclipse.jnosql.mapping.graph` package
    - name is as `graph`
    - add the annotation `@Inject` from the `jakarta.inject` package

    ```java
    @Inject
    private GraphTemplate graph;
    ```

4. Create a method named `category` that receives a `name` as input, retrieving or inserting it in the graph

    ```java
    Category category(String name) {
    return graph.traversalVertex().hasLabel(Category.class)
        .has("name", name)
        .<Category>next()
        .orElseGet(() -> graph.insert(Category.of(name)));
    }
    ```

5. Create a method named `book` that receives a `name` as input, retrieving or inserting it in the graph

    ```java
    Book book(String name) {
        return graph.traversalVertex().hasLabel(Book.class)
            .has("name", name)
            .<Book>next()
            .orElseGet(() -> graph.insert(Book.of(name)));
    }
    ```

6. Create a method named `category` to establish a relationship between `Book` and `Category`, so they will become the parameters
    - use the `graph.edge()` method to establish the `IS` relationship

        ```java
        public void category(Book book, Category category){
            this.graph.edge(book, LibraryLabels.IS, category);
        }
        ```


7. Create a method named `category` to establish a relationship between other categories, creating a sub-category
    - use the `graph.edge()` method to establish the `IS` relationship

    ```java
    public void category(Category subCategory, Category category){
        this.graph.edge(subCategory, LibraryLabels.IS, category);
    }
    ```

8. Create a method to retrieve a list of categories by their name related to "Software"

    ```java
    public List<String> softwareCategories(){
    return graph.traversalVertex().hasLabel(Category.class)
        .has("name", "Software")
        .in(LibraryLabels.IS).hasLabel("Category").<Category>result()
        .map(Category::getName)
        .collect(Collectors.toList());
    }
    ```

9. Create a method to retrieve a list of books by their name related to "Software"

    ```java
    public List<String> softwareBooks(){
    return graph.traversalVertex().hasLabel(Category.class)
        .has("name", "Software")
        .in(LibraryLabels.IS).hasLabel(Book.class).<Book>result()
        .map(Book::getName)
        .collect(Collectors.toList());
    }
    ```

10. Create a method to retrieve a list of books by its name related to "Software" and  "NoSQL"

    ```java
    public List<String> softwareNoSQLBooks(){
        return graph.traversalVertex().hasLabel(Category.class)
            .has("name", "Software")
            .in(LibraryLabels.IS)
            .has("name", "NoSQL")
            .in(LibraryLabels.IS).<Book>result()
            .map(Book::getName)
            .collect(Collectors.toList());
    }
    ```

## 5. Create the execution class

### :material-play-box-multiple-outline: Steps

1. Create a class called `AppBook` in the `expert.os.labs.persistence` package
2. Add a main method

    ```java
    public static void main(String[] args) {
    }
    ```

3.  Set up a try-with-resources block, inside the `main` method, to manage the Jakarta EE `SeContainer` that is responsible for dependency injection and managing resources

    ```java
    try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {     
    }
    ```

4. Obtain a `BookService` instance from `jakarta.nosql.document` package by selecting it: `container.select(BookService.class).get()`

    ```java
    BookService service = container.select(BookService.class).get();
    ```

5. Create the following categories by using the `BookService` instance
    - Software
    - Romance
    - Java
    - NoSQL
    - Micro Service

        ```java
        Category software = service.category("Software");
        Category romance = service.category("Romance");
        Category java = service.category("Java");
        Category nosql =service.category("NoSQL");
        Category microService =service.category("MicroService");
        ```

6. Create the following books by using the `BookService` instance
    - Effective Java
    - NoSQL Distilled
    - Migrating to Microservice Databases
    - The Shack

        ```java
        Book effectiveJava = service.book("Effective Java");
        Book nosqlDistilled = service.book("NoSQL Distilled");
        Book migratingMicroservice = service.book("Migrating to Microservice Databases");
        Book shack = service.book("The Shack");
        ```

7. Associate the sub-categories within the categories
    - Java and Software
    - NoSQL and Software
    - Microservice and Software

        ```java
        service.category(java, software);
        service.category(nosql, software);
        service.category(microService, software);
        ```

8. Associate the books with the categories
    - Effective Java and Software
    - NoSQL Distilled and Software
    - MicroServices and Software        

        ```java
        service.category(effectiveJava, software);
        service.category(nosqlDistilled, software);
        service.category(migratingMicroservice, software);
        ```

9. Associate the sub-category with the books
    - Effective Java and Java
    - NoSQL Distilled and NoSQL
    - Migrating to Microservices to MicroService
    - Shack to Romance

        ```java
        service.category(effectiveJava, java);
        service.category(nosqlDistilled, nosql);
        service.category(migratingMicroservice, microService);
        ``` 

10. Filter the software books by category using the `softwareCategories()` method and printout the result

    ```java
    List<String> softwareCategories =service.softwareCategories();
    System.out.println("Software Categories = " + softwareCategories);
    ```

 11. Filter all the software books by using the `softwareBooks()` method and printout the result

    ```java
    List<String> softwareBooks = service.softwareBooks();
    System.out.println("Software Books = " + softwareBooks);
    ```   

 12. Filter all the software and NoSQL books by using the `softwareNoSQLBooks()` method and printout the result

    ```java
    List<String> softwareNoSQLBooks = service.softwareNoSQLBooks();
    System.out.println("Software and NoSQL Books = " + softwareNoSQLBooks);
    ```     

13. Define a private constructor for the `AppBook` class to prevent instantiation since it contains only static methods:

    ```java
    private AppBook() {
    }
    ```

14. Run the `main()` method

### :material-checkbox-multiple-outline: Expected results

* The following output
   
    ```
    Software Categories = [NoSQL, Java, MicroService]
    Software Books = [Effective Java, NoSQL Distilled, Migrating to Microservice Databases]
    Software and NoSQL Books = [NoSQL Distilled]
    ```

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;

    import java.util.List;

    public class AppBook {

        public static void main(String[] args) {

            try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
                BookService service = container.select(BookService.class).get();

                Category software = service.category("Software");
                Category romance = service.category("Romance");
                Category java = service.category("Java");
                Category nosql =service.category("NoSQL");
                Category microService =service.category("MicroService");

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
                System.out.println("Software Categories = " + softwareCategories);

                List<String> softwareBooks = service.softwareBooks();
                System.out.println("Software Books = " + softwareBooks);

                List<String> softwareNoSQLBooks = service.softwareNoSQLBooks();
                System.out.println("Software and NoSQL Books = " + softwareNoSQLBooks);
            }
        }

        private AppBook() {
        }
    }

    ```
    