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

2. Execute the following commands in the CQL shell to create column families for "Person" and "Movie"

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

## 2. Implement the `person` class

In this lab, we will learn how to create a Java connection with Cassandra.

### :material-play-box-multiple-outline: Steps

1. Open the `04-column` project and navigate to the `src/main/java`
2. Create a class called `Person` in the `expert.os.labs.persistence` package
3. Define the class with the `@Entity` annotation, specifying the entity name as "Person."

    ```java
    @Entity("Person")
    public class Person {
    ```

4. Add the fields as specified in the provided code, including the `@Id` annotation for the `id` field and `@Column` annotations for the other fields.

    ```java
    @Id("id")
    private long id;

    @Column
    private String name;

    @Column
    private Map<String, String> contacts;
    ```

5. Implement constructors, getter methods, `equals`, `hashCode`, and `toString` methods.

### :material-checkbox-multiple-outline: Expected results

* Entity `Person` created

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.nosql.Column;
    import jakarta.nosql.Entity;
    import jakarta.nosql.Id;

    import java.util.Map;

    @Entity
    public class Person {

        @Id("id")
        private long id;

        @Column
        private String name;

        @Column
        private Map<String, String> contacts;

        public Person() {
        }

        public Person(long id, String name, Map<String, String> contacts) {
            this.id = id;
            this.name = name;
            this.contacts = contacts;
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Map<String, String> getContacts() {
            return contacts;
        }
    }
    ```

## 3. Implement the Builder class

### :material-play-box-multiple-outline: Steps

1. Create a class called `PersonBuilder` in the `expert.os.labs.persistence` package
2. Add the same fields we had previously added to the `Person` class, without the annotations
    
    ```java
    private long id;
    private String name;
    private Map<String, String> contacts;
    ```

3. Add the builder methods for each field
4. Add the `build()` method creating a new instance of `Person` using its constructor
5. In the `Perdon` class add the builder method referring to the `PersonBuilder`

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import java.util.Map;

    public class PersonBuilder {

        private long id;
        private String name;
        private Map<String, String> contacts;

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
    ```


## 4. Create the execution class for basic Cassandra Operations

### :material-play-box-multiple-outline: Steps

1. Create a class called `AppCassandraOperations` in the `expert.os.labs.persistence` package
2. Add a main method

    ```java
    public static void main(String[] args) throws InterruptedException {
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

    Person user2 = Person.builder()
            .contacts(Map.of("twitter", "elderjava", "linkedin", "elderjava","youtube", "elderjava"))
            .name("Elder Moraes").id(2).build();
    ```

5. Obtain an instance of `ColumnTemplate` using Jakarta EE's `SeContainer`

    ```java
    ColumnTemplate template =  container.select(ColumnTemplate.class).get();
    ```

6. Insert, the two user instances into the column using the `ColumnTemplate`, where the second one will have a delay of 1 second, using the method `insert()`

    ```java
    template.insert(user1);
    template.insert(user2, Duration.ofSeconds(1));
    ```

7. Retrieve the `user2` data based on its `id` and printout the result
    - use the method `select()` from the `template` field
    - the first parameter is the class and the second is the `id` value

    ```java
    Optional<Person> person2 = template.find(Person.class, 2L);
    System.out.println("Person2 data: " + person2);
    ```

8. Add a wait time, then retrieve and printout the same user again

    ```java
    TimeUnit.SECONDS.sleep(2L);
    person2 = template.find(Person.class, 2L);
    System.out.println("Person2 second retrieve data: " + person2);
    ```

9. Retrieve the `user1` data based on its `id` and printout the result

    ```java
    Optional<User> user1Data = template.get("user1", User.class);
    System.out.println("User1 data: " + user1Data);
    ```

10. Define a private constructor for the `AppCassandraOperations` class to prevent instantiation since it contains only static methods:

    ```java
    private AppCassandraOperations() {
    }
    ```

11. Run the `main()` method

### :material-checkbox-multiple-outline: Expected results

* The following output
   
    ```
    Person2 data: Optional[Person{id=2, name='Elder Moraes', contacts={youtube=elderjava, twitter=elderjava, linkedin=elderjava}}]
    Person2 second retrieve data: Optional.empty
    Person1 data: Optional[Person{id=1, name='Otavio Santana', contacts={youtube=otaviojava, twitter=otaviojava, linkedin=otaviojava}}]
    ```

* The second printout, related to the `user2` does not show any data because it expired

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;
    import jakarta.nosql.column.ColumnTemplate;

    import java.time.Duration;
    import java.util.Map;
    import java.util.Optional;
    import java.util.concurrent.TimeUnit;

    public class AppCassandraOperations {

        public static void main(String[] args) throws InterruptedException {

            try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

                Person user1 = Person.builder()
                    .contacts(Map.of("twitter", "otaviojava", "linkedin", "otaviojava", "youtube", "otaviojava"))
                    .name("Otavio Santana").id(1).build();

                Person user2 = Person.builder()
                    .contacts(Map.of("twitter", "elderjava", "linkedin", "elderjava", "youtube", "elderjava"))
                    .name("Elder Moraes").id(2).build();

                ColumnTemplate template = container.select(ColumnTemplate.class).get();

                template.insert(user1);
                template.insert(user2, Duration.ofSeconds(1));

                Optional<Person> person2 = template.find(Person.class, 2L);
                System.out.println("Person2 data: " + person2);

                TimeUnit.SECONDS.sleep(2L);
                person2 = template.find(Person.class, 2L);
                System.out.println("Person2 second retrieve data: " + person2);

                Optional<Person> person1 = template.find(Person.class, 1L);
                System.out.println("Person1 data: " + person1);
            }
        }
    }
    ```

## 5. Create the execution class for Cassandra specialization

In this lab, we will learn how to use specific features with Eclipse JNoSQL.

### :material-play-box-multiple-outline: Steps

1. Create a class called `AppCassandraSpecialization` in the `expert.os.labs.persistence` package
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

4. Create a user instance using the builder of the `Person` class with some data inside the `try` statement

    ```java
    Person user1 = Person.builder().contacts(Map.of("twitter", "ada")).name("Lovelace").id(3).build();
    ```

5. Obtain an instance of `CassandraTemplate` using the `container.select()` method

    ```java
    CassandraTemplate template =  container.select(CassandraTemplate.class).get();
    ```

6. Save the `user1` person entity to Cassandra using a specified consistency level:

    ```java
    template.save(user1, ConsistencyLevel.ONE);
    ```

7. Retrieve data from Cassandra using a CQL query and collect the results into a list of `Person` objects:

    ```java
    List<Person> people = template.<Person>cql("select * from developers.Person where id = 1")
        .collect(Collectors.toList());
    ```

8. Print the retrieved entities:

    ```java
    System.out.println(people);
    ```
        

9. Define a private constructor for the `AppCassandraSpecialization` class to prevent instantiation since it contains only static methods:

     ```java
     private AppCassandraSpecialization() {
     }
     ```

10. Run the `main()` method

### :material-checkbox-multiple-outline: Expected results

* TBD

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

    public class AppCassandraSpecialization {

        public static void main(String[] args) {

            try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
                Person user1 = Person.builder().contacts(Map.of("twitter", "ada")).name("Lovelace").id(3).build();

                CassandraTemplate template =  container.select(CassandraTemplate.class).get();
                template.save(user1, ConsistencyLevel.ONE);

                List<Person> people = template.<Person>cql("select * from developers.Person where id = 1")
                    .collect(Collectors.toList());

                System.out.println(people);
            }
        }

        private AppCassandraSpecialization() {
        }
    }
    ```
