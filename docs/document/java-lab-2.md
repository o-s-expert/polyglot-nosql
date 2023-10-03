# MongoDB - Lab 3

In this lab, you will create a Java application that utilizes Jakarta Data's repository framework to manage a library of books. The application will allow you to save books, retrieve books by title, and perform pageable queries on the book data. 

## 1. Define the Library Repository Interface

### :material-play-box-multiple-outline: Steps

1. Create an interface called `Library` and annotate it with `@Repository` from the `jakarta.data.repository` package

    ```java
    @Repository
    public interface Library {
    }
    ```

2. Extend the `PageableRepository<Book, String>` interface, specifying that it's a repository for managing `Book` entities with `String` as the type of the ID field

    ```java
    @Repository
    public interface Library extends PageableRepository<Book, String> {
    }
    ```

3. Define a custom query method `findByTitle()` that will take a `title` as `String` where the return is a `List` of `Book`

    ```java
    List<Book> findByTitle(String title);
    ```

### :material-checkbox-multiple-outline: Expected results

* Entity `Library` created

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
    ```

## 2. Add a factory data method to `Book`

### :material-play-box-multiple-outline: Steps

1. Open the `Book` class
2. Add the following method

    ```java
    public static Book of(Faker faker) {
        return new Book(faker.code().isbn13(), faker.book().title(), faker.number().numberBetween(1, 10),
            faker.number().numberBetween(1900, 2022));
    }
    ```

    !!! note

        The method `of()` will take the `Faker` class that's from the [DataFaker](https://www.datafaker.net/) library to generate random data when its accessed.


### :material-checkbox-multiple-outline: Expected results

* Class `Book` with an additional method to generate its instance using random data

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
    ```

## 3. Create the execution class to insert data

### :material-play-box-multiple-outline: Steps

1. Create a class called `AppMongoDBPagable` in the `expert.os.labs.persistence` package
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

4. Using the Jakarta SE container, obtain an instance of the `Library` repository by selecting it: `container.select(Library.class).get()`

    ```java
    Library library = container.select(Library.class).get();
    ```

5. Instantiate the `Faker` class to be able to, later on, save the book with some data

    ```java
    Faker faker = new Faker();
    ```

6. Create a look saving 100 different books using the `libary.save()` method

    ```java
    IntStream.range(0, 100).mapToObj(index -> Book.of(faker)).forEach(library::save);
    ```

7. Create a `Pageable` object named `pageable` using `Pageable.ofSize(10).sortBy(Sort.asc("title"), Sort.desc("year"))`. This sets options for retrieving books in pages of size 10, sorted by title in ascending order and year in descending order
    - both `Pageable` and `Sort` classes are from the `jakarta.data.repository` package

    ```java
    Pageable pageable = Pageable.ofSize(10).sortBy(Sort.asc("title"), Sort.desc("year"));
    ```

8. Find all books on the filter we created above, which will contain 10 entries, using the `Page` class from `jakarta.data.repository` package

    ```java
    Page<Book> page = library.findAll(pageable);
    ```

9. Printout the content of the `page` attribute using the `content()` method

    ```java
    System.out.println("Page = " + page.content());
    ```

10. Create a second `Pageable` object named `nextPage` to retrieve the next page of books

    ```java
    Pageable nextPage = pageable.next();
    ```

11. Find all books from the next page

    ```java
    Page<Book> page2 = library.findAll(nextPage);
    ```

12. Printout the content of the `page2` attribute using the `content()` method

    ```java
    System.out.println("Page 2 = " + page2);
    ```   

13. Use the custom query `findByTitle()` to find the book "Effective Java" and printout the result

    ```java
    library.findByTitle("Effective Java").forEach(System.out::println);
    ```

14. Define a private constructor for the `AppMongoDBPagable` class to prevent instantiation since it contains only static methods:

    ```java
    private AppMongoDBPagable() {
    }
    ```

15. Run the `main()` method


### :material-checkbox-multiple-outline: Expected results

* The following output
   
    ```
    Page = [Book[isbn=2fc4801f-fbed-4878-9e85-538487feae65, title=Effective Java, edition=1, year=2019], Book[isbn=9790753509363, title=If Not Now, When?, edition=5, year=2017], Book[isbn=9780972389006, title=Françoise Sagan, edition=4, year=2016], Book[isbn=9781430782025, title=The Little Foxes, edition=9, year=2012], Book[isbn=9790835585384, title=If Not Now, When?, edition=6, year=2010], Book[isbn=9780771303333, title=The World, the Flesh and the Devil, edition=7, year=2009], Book[isbn=9791077784467, title=To Sail Beyond the Sunset, edition=9, year=2008], Book[isbn=9780705967556, title=That Hideous Strength, edition=3, year=2006], Book[isbn=9790949723771, title=An Instant In The Wind, edition=5, year=2006], Book[isbn=9781470218720, title=The Violent Bear It Away, edition=7, year=2005]]

    Page 2 = NoSQLPage{entities=[Book[isbn=9781470218720, title=The Violent Bear It Away, edition=7, year=2005], Book[isbn=9780878082858, title=Where Angels Fear to Tread, edition=3, year=2003], Book[isbn=9791962492460, title=The Moon by Night, edition=4, year=2002], Book[isbn=9780736251754, title=East of Eden, edition=4, year=2002], Book[isbn=9781087074238, title=Nectar in a Sieve, edition=8, year=2001], Book[isbn=9790880353556, title=To Say Nothing of the Dog, edition=4, year=2001], Book[isbn=9781884451362, title=Clouds of Witness, edition=8, year=2000], Book[isbn=9791584073665, title=Have His Carcase, edition=3, year=2000], Book[isbn=9781961487482, title=The Wind's Twelve Quarters, edition=7, year=1999], Book[isbn=9790332536162, title=Edna O'Brien, edition=9, year=1998]], pageable=Pageable{page=2, size=10, title ASC, year DESC}}

    Book[isbn=2fc4801f-fbed-4878-9e85-538487feae65, title=Effective Java, edition=1, year=2019]

    ```

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import com.github.javafaker.Faker;
    import jakarta.data.repository.Page;
    import jakarta.data.repository.Pageable;
    import jakarta.data.repository.Sort;
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;

    import java.util.stream.IntStream;

    public class AppMongoDBPagable {

        public static void main(String[] args) {

            try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
                Library library = container.select(Library.class).get();


                Faker faker = new Faker();
                IntStream.range(0, 100).mapToObj(index -> Book.of(faker)).forEach(library::save);

                Pageable pageable = Pageable.ofSize(10).sortBy(Sort.asc("title"), Sort.desc("year"));
                Page<Book> page = library.findAll(pageable);
                System.out.println("Page = " + page.content());

                Pageable nextPage = pageable.next();
                Page<Book> page2 = library.findAll(nextPage);
                System.out.println("Page 2 = " + page2);

                library.findByTitle("Effective Java").forEach(System.out::println);
            }
        }

        private AppMongoDBPagable() {
        }
    }
    ```
