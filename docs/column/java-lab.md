# Cassandra - Lab 2

## 1. The Cassandra structure

 In this lab session, we will explore Apache Cassandra by executing various commands to create a keyspace, define column families, and create a secondary index.

### :material-play-box-multiple-outline: Steps

1. Execute the following command in the CQL shell to create a keyspace named "developers" with a replication factor of 3

    ```sql
    CREATE KEYSPACE IF NOT EXISTS developers WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 3};
    ```

    !!! note 
        You can ignore the warning that will be shown after running the command

        ```
        Warnings :
        Your replication factor 3 for keyspace developers is higher than the number of nodes 1
        ```

2. Execute the following commands in th CQL shell to create column families for "Person" and "Movie"

    ```sql
    CREATE COLUMNFAMILY IF NOT EXISTS developers.Person (id bigint PRIMARY KEY, name text, contacts map<text, text>);
    CREATE TYPE IF NOT EXISTS developers.director (name text, movies set<text>);
    CREATE COLUMNFAMILY IF NOT EXISTS developers.Movie (name text PRIMARY KEY, age int, director FROZEN<director>);
    ```

3. Create a secondary index on the "age" column of the "Movie" column family using the following command in the CQL shell

    ```sql
    create index if not exists ageIndex on developers.movie(age);
    ```

4. Use the Cassandra shell to explore, insert, and query data within your instance


### :material-checkbox-multiple-outline: Expected results

* Column families `movie` and `person` created

## 2 Cassandra and Java Connection

In this lab, we will learn how to create a Java connection with Cassandra.

material-play-box-multiple-outline: Steps

**Step 1: Create the `Person` Class**

1. Start by creating a new Java class called `Person`.

2. Add the necessary import statements at the top of the class:

   ```java
   import jakarta.nosql.Column;
   import jakarta.nosql.Entity;
   import jakarta.nosql.Id;
   import java.util.Collections;
   import java.util.Map;
   import java.util.Objects;
   ```

3. Define the class with the `@Entity` annotation, specifying the entity name as "Person."

   ```java
   @Entity("Person")
   public class Person {
   ```

4. Add the fields and methods as specified in the provided code, including the `@Id` annotation for the `id` field and `@Column` annotations for the other fields.

   ```java
       @Id("id")
       private long id;

       @Column
       private String name;

       @Column
       private Map<String, String> contacts;
   ```

5. Implement constructors, getter methods, `equals`, `hashCode`, and `toString` methods as shown in the provided code.

6. Include the `PersonBuilder` class and `builder()` method at the end of the `Person` class.

   ```java
       public static PersonBuilder builder() {
           return new PersonBuilder();
       }
   ```

**Step 2: Create the `PersonBuilder` Class**

1. Create a new Java class called `PersonBuilder`.

2. Define the class with a default constructor:

   ```java
   public class PersonBuilder {
   ```

3. Add the fields for `id`, `name`, and `contacts`:

   ```java
       private long id;
       private String name;
       private Map<String, String> contacts = Collections.emptyMap();
   ```

4. Implement setter methods for `id`, `name`, and `contacts`.

5. Include a `build()` method that constructs and returns a `Person` object with the specified attributes.

   ```java
       public Person build() {
           return new Person(id, name, contacts);
       }
   ```

**Step 3: Create the Main Application**

1. Create a new Java class called `App`.

2. Add the import statements for necessary Jakarta EE and Cassandra-related classes.

3. Define the `main` method inside the `App` class:

   ```java
   public static void main(String[] args) throws InterruptedException {
   ```

4. Within the `main` method, create instances of `Person` using the `PersonBuilder`:

   ```java
       Person otaviojava = Person.builder()
               .contacts(Map.of("twitter", "otaviojava", "linkedin", "otaviojava",
                       "youtube", "otaviojava"))
               .name("Otavio Santana").id(1).build();

       Person elderjava = Person.builder()
               .contacts(Map.of("twitter", "elderjava", "linkedin", "elderjava",
                       "youtube", "elderjava"))
               .name("Elder Moraes").id(2).build();
   ```

5. Obtain an instance of `ColumnTemplate` using Jakarta EE's `SeContainer`.

   ```java
       ColumnTemplate template =  container.select(ColumnTemplate.class).get();
   ```

6. Insert the `otaviojava` and `elderjava` instances into Cassandra:

   ```java
       template.insert(otaviojava);
       template.insert(elderjava, Duration.ofSeconds(1));
   ```

7. Retrieve and print data from Cassandra:

   ```java
       System.out.println("The elder find: " + template.find(Person.class, 2L));
       TimeUnit.SECONDS.sleep(2L);
       System.out.println("The elder find: " + template.find(Person.class, 2L));
       Optional<Person> person = template.select(Person.class)
               .where("id").eq(1L).singleResult();
       System.out.println("Entity found: " + person);
   ```

8. Close the `SeContainer` using a try-with-resources block.

   ```java
   try(SeContainer container = SeContainerInitializer.newInstance().initialize()) {
   ```

**Step 4: Compile and Run the Application**

Compile the Java classes and run the `App` class to execute the Cassandra operations.

### :material-check-outline: Solution

??? example "Click to see..."

      ```java
      
      
      import jakarta.nosql.Column;
      import jakarta.nosql.Entity;
      import jakarta.nosql.Id;
      
      import java.util.Collections;
      import java.util.Map;
      import java.util.Objects;
      
      
      @Entity("Person")
      public class Person {
      
          @Id("id")
          private long id;
      
          @Column
          private String name;
      
          @Column
          private Map<String, String> contacts;
      
          public Person() {
          }
      
          Person(long id, String name, Map<String, String> contacts) {
              this.id = id;
              this.name = name;
              this.contacts = contacts;
          }
      
          public long id() {
              return id;
          }
      
          public String name() {
              return name;
          }
      
          public Map<String, String> contacts() {
              return Collections.unmodifiableMap(contacts);
          }
      
          @Override
          public boolean equals(Object o) {
              if (this == o) {
                  return true;
              }
              if (o == null || getClass() != o.getClass()) {
                  return false;
              }
              Person person = (Person) o;
              return id == person.id;
          }
      
          @Override
          public int hashCode() {
              return Objects.hashCode(id);
          }
      
          @Override
          public String toString() {
              return "Person{" +
                      "id=" + id +
                      ", name='" + name + '\'' +
                      ", contacts=" + contacts +
                      '}';
          }
      
          public static PersonBuilder builder() {
              return new PersonBuilder();
          }
      }
      
      import java.util.Collections;
      import java.util.Map;
      
      public class PersonBuilder {
          private long id;
          private String name;
          private Map<String, String> contacts = Collections.emptyMap();
          PersonBuilder() {
          }
      
          public PersonBuilder id(long id) {
              this.id = id;
              return this;
          }
      
          public PersonBuilder name(String name) {
              this.name = name;
              return this;
          }
      
          public PersonBuilder contacts(Map<String, String> contacts) {
              this.contacts = contacts;
              return this;
          }
      
          public Person build() {
              return new Person(id, name, contacts);
          }
      }
      
      
      import jakarta.enterprise.inject.se.SeContainer;
      import jakarta.enterprise.inject.se.SeContainerInitializer;
      import jakarta.nosql.column.ColumnTemplate;
      
      import java.time.Duration;
      import java.util.Map;
      import java.util.Optional;
      import java.util.concurrent.TimeUnit;
      
      
      public class App {
      
      
          public static void main(String[] args) throws InterruptedException {
      
              try(SeContainer container = SeContainerInitializer.newInstance().initialize()) {
      
                  Person otaviojava = Person.builder()
                          .contacts(Map.of("twitter", "otaviojava", "linkedin", "otaviojava",
                                  "youtube", "otaviojava"))
                          .name("Otavio Santana").id(1).build();
      
                  Person elderjava = Person.builder()
                          .contacts(Map.of("twitter", "elderjava", "linkedin", "elderjava",
                                  "youtube", "elderjava"))
                          .name("Elder Moraes").id(2).build();
      
                  ColumnTemplate template =  container.select(ColumnTemplate.class).get();
                  template.insert(otaviojava);
      
                  template.insert(elderjava, Duration.ofSeconds(1));
      
                  System.out.println("The elder find: " + template.find(Person.class, 2L));
      
                  TimeUnit.SECONDS.sleep(2L);
      
                  System.out.println("The elder find: " + template.find(Person.class, 2L));
      
                  Optional<Person> person = template.select(Person.class)
                          .where("id").eq(1L).singleResult();
                  System.out.println("Entity found: " + person);
      
              }
          }
      
          private App() {}
      }
      ```

## 3 Cassandra specialization

In this lab, we will learn how to use specific features with Eclipse JNoSQL.

material-play-box-multiple-outline: Steps

**Step 1: Create the `Person` Class**

You can follow the earlier steps to create the `Person` class with the necessary annotations and methods.

**Step 2: Create the `App2` Class**

1. Create a new Java class called `App2`.

2. Add the import statements for the required classes:

   ```java
   import com.datastax.oss.driver.api.core.ConsistencyLevel;
   import jakarta.enterprise.inject.se.SeContainer;
   import jakarta.enterprise.inject.se.SeContainerInitializer;
   import org.eclipse.jnosql.databases.cassandra.mapping.CassandraTemplate;
   ```

3. Define the `App2` class:

   ```java
   public class App2 {
   ```

4. Implement the `main` method inside the `App2` class:

   ```java
       public static void main(String[] args) {
   ```

5. Within the `main` method, create an instance of `Person` using the `PersonBuilder`:

   ```java
           Person ada = Person.builder()
                   .contacts(Map.of("twitter", "ada"))
                   .name("Ada Lovelace").id(3).build();
   ```

6. Obtain an instance of `CassandraTemplate` using Jakarta EE's `SeContainer`:

   ```java
           CassandraTemplate template = container.select(CassandraTemplate.class).get();
   ```

7. Save the `ada` person entity to Cassandra using a specified consistency level:

   ```java
           template.save(ada, ConsistencyLevel.ONE);
   ```

8. Retrieve data from Cassandra using a CQL query and collect the results into a list of `Person` objects:

   ```java
           List<Person> people = template.<Person>cql("select * from developers.Person where id = 1")
                   .collect(Collectors.toList());
   ```

9. Print the retrieved entities:

   ```java
           System.out.println("Entity found: " + people);
   ```

10. Close the `SeContainer` using a try-with-resources block:

    ```java
    try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
    ```

**Step 3: Compile and Run the Application**

Compile the Java classes and run the `App2` class to execute the Cassandra operations.



### :material-check-outline: Solution

??? example "Click to see..."

      ```java
      
      import com.datastax.oss.driver.api.core.ConsistencyLevel;
      import jakarta.enterprise.inject.se.SeContainer;
      import jakarta.enterprise.inject.se.SeContainerInitializer;
      import org.eclipse.jnosql.databases.cassandra.mapping.CassandraTemplate;
      
      import java.util.List;
      import java.util.Map;
      import java.util.stream.Collectors;
      
      
      public class App2 {
      
      
          public static void main(String[] args) {
      
              try(SeContainer container = SeContainerInitializer.newInstance().initialize()) {
      
                  Person ada = Person.builder()
                          .contacts(Map.of("twitter", "ada"))
                          .name("Ada Lovelace").id(3).build();
      
                  CassandraTemplate template =  container.select(CassandraTemplate.class).get();
                  template.save(ada, ConsistencyLevel.ONE);
      
      
                  List<Person> people = template.<Person>cql("select * from developers.Person where id = 1")
                          .collect(Collectors.toList());
                  System.out.println("Entity found: " + people);
      
              }
          }
      
          private App2() {}
      }
      ```

## 3 Cassandra Repository

In this lab, we will learn how to use Repository with Eclipse JNoSQL.

material-play-box-multiple-outline: Steps

**Step 1: Create the `PersonRepository` Interface**

1. Create a new Java interface called `PersonRepository`.

2. Add the import statements for the required classes and packages, including Jakarta EE, Jakarta Data, and Java collections.

```java
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;
```

3. Annotate the interface with `@Repository` to indicate it's a Jakarta  Data repository.

4. Extend the `CrudRepository` interface and specify the entity type (`Person`) and the ID type (`Long`) as type parameters. It defines the basic CRUD operations that can be performed on the `Person` entity.

```java
@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
}
```

**Step 2: Create the `App3` Class**

1. Create a new Java class called `App3`.

2. Add the import statements for the required classes and packages, including Jakarta EE, Jakarta Data, and Java collections.

3. Define the `App3` class:

```java
public class App3 {
```

4. Implement the `main` method inside the `App3` class:

```java
   public static void main(String[] args) {
```

5. Set up a try-with-resources block to manage the Jakarta EE `SeContainer`. The `SeContainer` is responsible for dependency injection and managing resources:

```java
       try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
```

6. Create a new `Person` instance (`otaviojava`) using the `Person.builder()` pattern. Set the `contacts`, `name`, and `id` fields:

```java
           Person otaviojava = Person.builder()
                   .contacts(Map.of("twitter", "otaviojava", "linkedin", "otaviojava",
                           "youtube", "otaviojava"))
                   .name("Otavio Santana").id(1).build();
```

7. Obtain an instance of the `PersonRepository` interface using Jakarta EE's `SeContainer`. It is done by selecting and getting an instance of the repository:

```java
           PersonRepository repository = container.select(PersonRepository.class).get();
```

8. Save the `otaviojava` person entity to the repository:

```java
           repository.save(otaviojava);
```

9. Retrieve data from the repository by `id` (1L) using the `findById` method. The result is wrapped in an `Optional` to handle the possibility of a non-existent entity:

```java
           Optional<Person> person = repository.findById(1L);
```

10. Print the retrieved entity or indicate if it was not found:

```java
           System.out.println("Entity found: " + person.orElse("Entity not found"));
```

11. Close the `SeContainer` and release any resources used by the application:

```java
       }
   }
```

12. Define a private constructor for the `App3` class to prevent instantiation since it contains only static methods:

```java
   private App3() {
   }
```

**Step 3: Compile and Run the Application**

Compile the Java classes and run the `App3` class to execute the Spring Data repository operations. This lab demonstrates how to create a Spring Data repository interface, use it to save and retrieve data, and manage resources using Jakarta EE's `SeContainer`.

### :material-check-outline: Solution

??? example "Click to see..."

      ```java
      
      import jakarta.data.repository.CrudRepository;
      import jakarta.data.repository.Repository;
      @Repository
      public interface PersonRepository extends CrudRepository<Person, Long> {
      
      }
      
      import jakarta.enterprise.inject.se.SeContainer;
      import jakarta.enterprise.inject.se.SeContainerInitializer;
      
      import java.util.Map;
      import java.util.Optional;
      
      
      public class App3 {
      
      
          public static void main(String[] args){
      
              try(SeContainer container = SeContainerInitializer.newInstance().initialize()) {
      
                  Person otaviojava = Person.builder()
                          .contacts(Map.of("twitter", "otaviojava", "linkedin", "otaviojava",
                                  "youtube", "otaviojava"))
                          .name("Otavio Santana").id(1).build();
      
      
                  PersonRepository repository = container.select(PersonRepository.class).get();
                  repository.save(otaviojava);
      
                  Optional<Person> person = repository.findById(1L);
                  System.out.println("Entity found: " + person);
      
              }
          }
      
          private App3() {}
      }
      
      ```

## 3 Cassandra UDT

In this lab, we will learn how to UDT.

material-play-box-multiple-outline: Steps

**Step 1: Create the `Director` Class**

1. Create a new Java class called `Director`.

2. Add the import statements for the required classes and packages, including Jakarta NoSQL annotations, `java.util.HashSet`, and `java.util.Objects`.

   ```java
   import jakarta.nosql.Column;
   import jakarta.nosql.Entity;
   import java.util.HashSet;
   import java.util.Objects;
   import java.util.Set;
   ```

3. Annotate the class with `@Entity` to specify that it's an entity class.

4. Define instance variables for `name` and `movies`. Add getter and setter methods for these variables.

5. Implement `equals`, `hashCode`, and `toString` methods for the class.

6. Create a nested static class `DirectorBuilder` for convenient object construction. This class provides methods for setting the `name` and adding movies to the `movies` set.

   ```java
   public static DirectorBuilder builder() {
       return new DirectorBuilder();
   }

   public static class DirectorBuilder {
       // Implement builder methods
   }
   ```

**Step 2: Create the `Movie` Class**

1. Create a new Java class called `Movie`.

2. Add the import statements for the required classes and packages, including Jakarta NoSQL annotations, `org.eclipse.jnosql.databases.cassandra.mapping.UDT`, and `java.util.Objects`.

   ```java
   import jakarta.nosql.Column;
   import jakarta.nosql.Entity;
   import jakarta.nosql.Id;
   import org.eclipse.jnosql.databases.cassandra.mapping.UDT;
   import java.util.Objects;
   ```

3. Annotate the class with `@Entity` to specify that it's an entity class.

4. Define instance variables for `name`, `age`, and `director`. Add getter and setter methods for these variables.

5. Annotate the `director` field with `@UDT("director")` to specify that it's a User-Defined Type (UDT) with the name "director."

6. Implement `equals`, `hashCode`, and `toString` methods for the class.

**Step 3: Create the `MovieRepository` Interface**

1. Create a new Java interface called `MovieRepository`.

2. Add the import statements for the required classes and packages, including Jakarta NoSQL, Jakarta Data, and Cassandra-specific annotations.

   ```java
   import jakarta.nosql.CassandraRepository;
   import java.util.List;
   ```

3. Annotate the interface with `@Repository` to indicate that it's a Spring Data repository.

4. Extend the `CassandraRepository` interface and specify the entity type (`Movie`) and the ID type (`String`) as type parameters.

5. Define custom query methods, such as `findByAge(Integer age)` and `findAllQuery()`, to retrieve data from Cassandra based on specific criteria.

**Step 4: Create the `App4` Class**

1. Create a new Java class called `App4`.

2. Add the import statements for the required classes and packages, including Jakarta EE and Java collections.

3. Define the `App4` class:

   ```java
   public class App4 {
   ```

4. Implement the `main` method inside the `App4` class:

   ```java
       public static void main(String[] args) {
   ```

5. Set up a try-with-resources block to manage the Jakarta EE `SeContainer`. The `SeContainer` is responsible for dependency injection and managing resources:

   ```java
           try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
   ```

6. Obtain an instance of the `MovieRepository` interface using Jakarta EE's `SeContainer`. This is done by selecting and getting an instance of the repository:

   ```java
               MovieRepository repository = container.select(MovieRepository.class).get();
   ```

7. Create instances of `Movie` entities (`matrix`, `fightClub`, and `americanBeauty`) and populate them with data.

8. Use the repository to save all the created `Movie` entities to Cassandra using `repository.saveAll`.

9. Retrieve and print all movies using `repository.findAllQuery()`.

10. Retrieve and print movies from the year 1999 using `repository.findByAge(1999)`.

11. Close the `SeContainer` and release any resources used by the application:

    ```java
           }
       }
   ```

12. Define a private constructor for the `App4` class to prevent instantiation since it contains only static methods:

    ```java
       private App4() {
       }
   ```

**Step 5: Compile and Run the Application**

Compile the Java classes and run the `App4` class to execute Spring Data repository operations. This lab demonstrates how to create Spring Data repository interfaces, save and retrieve data from Cassandra, and manage resources using Jakarta EE's `SeContainer`.

### :material-check-outline: Solution

??? example "Click to see..."

      ```java
      
      import jakarta.nosql.Column;
      import jakarta.nosql.Entity;
      
      import java.util.HashSet;
      import java.util.Objects;
      import java.util.Set;
      
      @Entity
      public class Director {
      
          @Column
          private String name;
      
          @Column
          private Set<String> movies;
      
          public String getName() {
              return name;
          }
      
          public void setName(String name) {
              this.name = name;
          }
      
          public Set<String> getMovies() {
              return movies;
          }
      
          public void setMovies(Set<String> movies) {
              this.movies = movies;
          }
      
          @Override
          public boolean equals(Object o) {
              if (this == o) return true;
              if (!(o instanceof Director)) return false;
              Director director = (Director) o;
              return Objects.equals(name, director.name) &&
                      Objects.equals(movies, director.movies);
          }
      
          @Override
          public int hashCode() {
              return Objects.hash(name, movies);
          }
      
          @Override
          public String toString() {
              final StringBuilder sb = new StringBuilder("Director{");
              sb.append("name='").append(name).append('\'');
              sb.append(", movies=").append(movies);
              sb.append('}');
              return sb.toString();
          }
      
          public static DirectorBuilder builder() {
              return new DirectorBuilder();
          }
      
          public static class DirectorBuilder {
      
              private String name;
      
              private Set<String> movies = new HashSet<>();
      
              public DirectorBuilder withName(String name) {
                  this.name = name;
                  return this;
              }
      
              public DirectorBuilder add(String movie) {
                  this.movies.add(movie);
                  return this;
              }
      
              public Director build() {
                  Director director = new Director();
                  director.setName(name);
                  director.setMovies(movies);
                  return director;
              }
          }
      }
      
      
      import jakarta.nosql.Column;
      import jakarta.nosql.Entity;
      import jakarta.nosql.Id;
      import org.eclipse.jnosql.databases.cassandra.mapping.UDT;
      
      import java.util.Objects;
      
      @Entity
      public class Movie {
      
          @Id("name")
          private String name;
      
          @Column
          private Integer age;
      
          @Column
          @UDT("director")
          private Director director;
      
          public String getName() {
              return name;
          }
      
          public void setName(String name) {
              this.name = name;
          }
      
          public Integer getAge() {
              return age;
          }
      
          public void setAge(Integer age) {
              this.age = age;
          }
      
          public Director getDirector() {
              return director;
          }
      
          public void setDirector(Director director) {
              this.director = director;
          }
      
          @Override
          public boolean equals(Object o) {
              if (this == o) return true;
              if (!(o instanceof Movie)) return false;
              Movie movie = (Movie) o;
              return Objects.equals(name, movie.name);
          }
      
          @Override
          public int hashCode() {
              return Objects.hashCode(name);
          }
      
          @Override
          public String toString() {
              return "Movie{" +
                      "name='" + name + '\'' +
                      ", age=" + age +
                      ", director=" + director +
                      '}';
          }
      }
      
      @Repository
      public interface MovieRepository extends CassandraRepository<Movie, String> {
      
      
          List<Movie> findByAge(Integer age);
      
          @CQL("select * from developers.Movie")
          List<Movie> findAllQuery();
      }
      
      
      
      import jakarta.enterprise.inject.se.SeContainer;
      import jakarta.enterprise.inject.se.SeContainerInitializer;
      
      import java.util.List;
      
      public class App4 {
      
      
          public static void main(String[] args) {
      
              try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
      
                  MovieRepository repository = container.select(MovieRepository.class).get();
      
                  Movie matrix = new Movie();
                  matrix.setName("The Matrix");
                  matrix.setAge(1999);
                  matrix.setDirector(Director.builder().withName("Lana Wachowski")
                          .add("The Matrix").add("The Matrix Reloaded").add("Assassins").build());
      
                  Movie fightClub = new Movie();
                  fightClub.setName("Fight Club");
                  fightClub.setAge(1999);
                  fightClub.setDirector(Director.builder().withName("David Fincher")
                          .add("Fight Club").add("Seven").add("The Social Network").build());
      
                  Movie americanBeauty = new Movie();
                  americanBeauty.setName("American Beauty");
                  americanBeauty.setAge(1999);
                  americanBeauty.setDirector(Director.builder().withName("Sam Mendes")
                          .add("Spectre").add("Skyfall").add("American Beauty").build());
      
      
                  repository.saveAll(List.of(matrix, fightClub, americanBeauty));
                  System.out.println("Find all: " + repository.findAllQuery());
                  System.out.println("Movies from 1999: " + repository.findByAge(1999));
      
      
              }
          }
      
          private App4() {}
      }
      
      ```