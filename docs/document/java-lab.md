# MongoDB - Lab 2

In this lab, you will create a Java application that interacts with a NoSQL database using Jakarta NoSQL and Jakarta EE. The application will define an entity class `Book` to represent books and use a `DocumentTemplate` to perform database operations. 

## 1. Define the `Book` Entity Class

### :material-play-box-multiple-outline: Steps

1. Open the `05-document` project and navigate to the `src/main/java`
2. Create a `record` called `Book` in the `expert.os.labs.persistence` package
3. Define the class with the `@Entity` annotation from the `jakarta.nosql` package
4. Add the following fields in the record:
    - `@Id String isbn`
    - `@Column String title`
    - `@Column int edition`
    - `@Column int year`

5. Create a static method to create

### :material-checkbox-multiple-outline: Expected results

* Record `Book` created

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.nosql.Column;
    import jakarta.nosql.Entity;
    import jakarta.nosql.Id;

    @Entity
    public record Book(@Id String isbn, @Column String title, @Column int edition, @Column int year) {
    }
    ```

## 2. Create the execution class to insert data

### :material-play-box-multiple-outline: Steps

1. Create a class called `AppMongoDB` in the `expert.os.labs.persistence` package
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

4. Create an instance of the `Book` class with some sample data. You can use `UUID.randomUUID().toString()` for the `isbn`, "Effective Java" for the title, `1` for the edition, and `2019` for the year. inside the `try` statement

    ```java
    Book book = new Book(UUID.randomUUID().toString(), "Effective Java", 1, 2019);
    ```

5. Obtain a `DocumentTemplate` instance from the package `jakarta.nosql.document` using the Jakarta SE container by selecting it: `container.select(DocumentTemplate.class).get()`

    ```java
    DocumentTemplate template = container.select(DocumentTemplate.class).get();
    ```

6. Use the `DocumentTemplate` to insert the `book` instance into the NoSQL database

    ```java
    Book saved = template.insert(book);
    ```

7. Printout the content of the book inserted

    ```java
    System.out.println("Book saved: = " + saved);
    ```

8. Use the `DocumentTemplate` to perform a query to retrieve a book by its title

    ```java
    Optional<Book> bookFound = template.select(Book.class).where("title").eq("Effective Java").singleResult();
    ```

9. Printout the result

   ```java
   System.out.println("bookFound = " + bookFound);
   ```

10. Define a private constructor for the `AppMongoDB` class to prevent instantiation since it contains only static methods:

    ```java
    private AppMongoDB() {
    }
    ```

11. Run the `main()` method

### :material-checkbox-multiple-outline: Expected results

* The following output
   
    ```
    Book saved: = Book[isbn=2fc4801f-fbed-4878-9e85-538487feae65, title=Effective Java, edition=1, year=2019]

    Book Found = Optional[Book[isbn=2fc4801f-fbed-4878-9e85-538487feae65, title=Effective Java, edition=1, year=2019]]
    ```

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;
    import jakarta.nosql.document.DocumentTemplate;

    import java.util.Optional;
    import java.util.UUID;

    public class AppMongoDb {

        public static void main(String[] args) {
            try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
                Book book = new Book(UUID.randomUUID().toString(), "Effective Java", 1, 2019);

                DocumentTemplate template = container.select(DocumentTemplate.class).get();
                Book saved = template.insert(book);
                System.out.println("Book saved: = " + saved);

                Optional<Book> bookFound = template.select(Book.class).where("title").eq("Effective Java").singleResult();
                System.out.println("Book Found = " + bookFound);
            }
        }

        private AppMongoDb() {
        }
    }
    ```
