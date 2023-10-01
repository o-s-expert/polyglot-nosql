# Cassandra - Lab 4

In this lab, we will learn how to UDT.

## 1. Define the `Director` Entity

### :material-play-box-multiple-outline: Steps

1. Create a class called `Director` in the `expert.os.labs.persistence` package
2. Define the class with the `@Entity` annotation

    ```java
    @Entity
    public class Director {
    ```

3. Add the fields as specified in the provided code adding the`@Column` annotations for all

    ```java
    @Column
    private String name;

    @Column
    private Set<String> movies;
    ```

4. Implement constructors, setter methods, `equals`, `hashCode`, and `toString` method.

### :material-checkbox-multiple-outline: Expected results

* Entity `Director` created

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.nosql.Column;
    import jakarta.nosql.Entity;

    import java.util.Set;

    @Entity
    public class Director {

        @Column
        private String name;

        @Column
        private Set<String> movies;

        public Director() {
        }

        public Director(String name, Set<String> movies) {
            this.name = name;
            this.movies = movies;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setMovies(Set<String> movies) {
            this.movies = movies;
        }

        @Override
        public String toString() {
            return "Director{" +
                "name='" + name + '\'' +
                ", movies=" + movies +
                '}';
        }
    }
    ```

## 2. Implement the Builder class

### :material-play-box-multiple-outline: Steps

1. Create a class called `DirectorBuilder` in the `expert.os.labs.persistence` package
2. Add the same fields we had previously added to the `Director` class, without the annotations
    * note that we need to initialize the `movies` attribute    

    ```java
    private String name;
    private final Set<String> movies = new HashSet<>();
    ```

3. Add the builder methods for each field
    * for the `movies field`, add a single `movie` to the `Set`
4. Add the `build()` method creating a new instance of `Director` using its constructor
5. In the `Director` class add the builder method referring to the `DirectorBuilder`

### :material-checkbox-multiple-outline: Expected results

* Builder class `DirectorBuilder` created

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import java.util.Set;

    public class DirectorBuilder {

        private String name;
        private Set<String> movies;

        public DirectorBuilder name(String name) {
            this.name = name;
            return this;
        }

        public DirectorBuilder addMovie(String movie) {
            this.movies.add(movie)
            return this;
        }

        public Director build() {
            return new Director(name, movies);
        }
    }
    ```

## 3. Define the `Movie` Entity

### :material-play-box-multiple-outline: Steps

1. Create a class called `Movie` in the `expert.os.labs.persistence` package
2. Define the class with the `@Entity` annotation
3. Define instance variables for `name`, `age`, and `director`, where the `name` is the ID and must have the `@Id` annotation and the other attributes the `@Column` annotation

    ```java
    @Id("name")
    private String name;

    @Column
    private Integer age;

    @Column
    private Director director;
    ```

4. Annotate the `director` field with `@UDT("director")` from the package `org.eclipse.jnosql.databases.cassandra.mapping` to specify that it's a User-Defined Type (UDT) with the name "director"

    ```java hl_lines="2"
    @Column
    UDT("director")
    private Director director;
    ```

5. Implement the constructor, getter, setters, `equals`, `hashCode`, and `toString` methods for the class

### :material-checkbox-multiple-outline: Expected results

* Entity `Director` created

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.nosql.Column;
    import jakarta.nosql.Entity;
    import jakarta.nosql.Id;
    import org.eclipse.jnosql.databases.cassandra.mapping.UDT;

    @Entity
    public class Movie {

        @Id("name")
        private String name;

        @Column
        private Integer age;

        @Column
        @UDT("director")
        private Director director;

        public Movie(String name, Integer age, Director director) {
            this.name = name;
            this.age = age;
            this.director = director;
        }

        // getters and setters ignored
        
        @Override
        public String toString() {
            return "Movie{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", director=" + director +
                '}';
        }
    }
    ```

## 4. Define the `Movie` Repository

### :material-play-box-multiple-outline: Steps

1. Create an interface called `MovieRepository` in the `expert.os.labs.persistence` package
2. Annotate the interface with `@Repository` using the package from `jakarta.data.repository` to indicate it's a Jakarta Data repository
3. Extend the `CassandraRepository` interface from the package `org.eclipse.jnosql.databases.cassandra.mapping` and specify the entity type (`Movie`) and the ID type (`String`) as type parameters

    ```java
    @Repository
    public interface MovieRepository extends CassandraRepository<Movie, String> {
    }
    ```

4. Define the custom query `findByAge()`

    ```java
    List<Movie> findByAge(Integer age);
    ```

5. Define a custom query `findAllQuery()` adding the `@CQL` annotation from the package `org.eclipse.jnosql.databases.cassandra.mapping` where its parameter have the following query `select * from developers.Movie`

    ```java
    @CQL("select * from developers.Movie")
    List<Movie> findAllQuery();
    ```

### :material-checkbox-multiple-outline: Expected results

* Repository `MovieRepository` containing two custom queries

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.data.repository.Repository;
    import org.eclipse.jnosql.databases.cassandra.mapping.CQL;
    import org.eclipse.jnosql.databases.cassandra.mapping.CassandraRepository;

    import java.util.List;

    @Repository
    public interface MovieRepository extends CassandraRepository<Movie, String> {

        List<Movie> findByAge(Integer age);

        @CQL("select * from developers.Movie")
        List<Movie> findAllQuery();
    }
    ```

## 5. Create the execution class for Cassandra UDT

### :material-play-box-multiple-outline: Steps

1. Create a class called `AppCassandraUDT` in the `expert.os.labs.persistence` package
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

4. Obtain an instance of the `MovieRepository` interface using Jakarta EE's `SeContainer`. It is done by selecting and getting an instance of the repository

    ```java
    MovieRepository repository = container.select(MovieRepository.class).get();
    ```

5. Add the following `Movie` instances with its data

    ```java
    Movie matrix = new Movie();
    matrix.setName("The Matrix");
    matrix.setAge(1999);
    matrix.setDirector(Director.builder().name("Lana Wachowski")
        .add("The Matrix").add("The Matrix Reloaded").add("Assassins").build());

    Movie fightClub = new Movie();
    fightClub.setName("Fight Club");
    fightClub.setAge(1999);
    fightClub.setDirector(Director.builder().name("David Fincher")
        .add("Fight Club").add("Seven").add("The Social Network").build());

    Movie americanBeauty = new Movie();
    americanBeauty.setName("American Beauty");
    americanBeauty.setAge(1999);
    americanBeauty.setDirector(Director.builder().name("Sam Mendes")
        .add("Spectre").add("SkyFall").add("American Beauty").build());
    ```

6. Use the repository to save all the created `Movie`, as a `List` of entities to Cassandra using `repository.saveAll()`

    ```java
    repository.saveAll(List.of(matrix, fightClub, americanBeauty));
    ```

7. Retrieve and print all movies using `repository.findAllQuery()`

    ```java
    List<Movie> allMovies = repository.findAllQuery();
    System.out.println("All movies = " + allMovies);
    ```

8. Retrieve and print movies from the year 1999 using `repository.findByAge(1999)`

    ```java
    List<Movie> movieByAge = repository.findByAge(1999);
    System.out.println("Movies from 1999 = " + movieByAge);
    ```

9. Define a private constructor for the `AppCassandraUDT` class to prevent instantiation since it contains only static methods:

    ```java
    private AppCassandraUDT() {
    }
    ```

10. Run the `main()` method


### :material-checkbox-multiple-outline: Expected results

* The following output
   
    ```
    All movies = [Movie{name='Fight Club', age=1999, director=Director{name='David Fincher', movies=[Fight Club, Seven, The Social Network]}}, Movie{name='American Beauty', age=1999, director=Director{name='Sam Mendes', movies=[American Beauty, SkyFall, Spectre]}}, Movie{name='The Matrix', age=1999, director=Director{name='Lana Wachowski', movies=[The Matrix, Assassins, The Matrix Reloaded]}}]

    Movies from 1999 = [Movie{name='Fight Club', age=1999, director=Director{name='David Fincher', movies=[Fight Club, Seven, The Social Network]}}, Movie{name='American Beauty', age=1999, director=Director{name='Sam Mendes', movies=[American Beauty, SkyFall, Spectre]}}, Movie{name='The Matrix', age=1999, director=Director{name='Lana Wachowski', movies=[The Matrix, Assassins, The Matrix Reloaded]}}]
    ```

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;

    import java.util.List;

    public class AppCassandraUDT {

        public static void main(String[] args) {
            try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
                MovieRepository repository = container.select(MovieRepository.class).get();

                Movie matrix = new Movie();
                matrix.setName("The Matrix");
                matrix.setAge(1999);
                matrix.setDirector(Director.builder().name("Lana Wachowski")
                    .addMovie("The Matrix").addMovie("The Matrix Reloaded").addMovie("Assassins").build());

                Movie fightClub = new Movie();
                fightClub.setName("Fight Club");
                fightClub.setAge(1999);
                fightClub.setDirector(Director.builder().name("David Fincher")
                    .addMovie("Fight Club").add("Seven").addMovie("The Social Network").build());

                Movie americanBeauty = new Movie();
                americanBeauty.setName("American Beauty");
                americanBeauty.setAge(1999);
                americanBeauty.setDirector(Director.builder().name("Sam Mendes")
                    .addMovie("Spectre").addMovie("SkyFall").addMovie("American Beauty").build());

                repository.saveAll(List.of(matrix, fightClub, americanBeauty));

                List<Movie> allMovies = repository.findAllQuery();
                System.out.println("All movies = " + allMovies);

                List<Movie> movieByAge = repository.findByAge(1999);
                System.out.println("Movies from 1999 = " + movieByAge);
            }
        }

        public AppCassandraUDT() {
        }
    }

    ```
