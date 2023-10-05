# Neo4J Lab - Intermediate

This lab won't show the steps in much detail, instead it will be shorter and you need to figure out (with some tips) how to implement it.

Try to, first implement, and later, see the  solution!

## 1. Define the `Person` Entity Class

1. Create a class called `Person` in the `expert.os.labs.persistence` package
2. Define the class with the `@Entity` annotation from the `jakarta.nosql` package
3. Add the following `private` fields:
    
    | Annotation | Type | Name |
    |--|--|--|
    | `@Id` | `Long` | `id`| 
    | `@Column` | `String` | `name` |
    | `@column` | `long` | `age` |
    | `@Column` | `String` | `occupation` |
    | `@Column` | `Double` | `salary`

4. Implement constructors, getter methods, `equals`, `hashCode`, and `toString` methods

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.nosql.Column;
    import jakarta.nosql.Entity;
    import jakarta.nosql.Id;

    @Entity
    public class Person {

        @Id
        private Long id;

        @Column
        private String name;

        @Column
        private Long age;

        @Column
        private String occupation;

        @Column
        private Double salary;

        public Person() {
        }

        public Person(String name, Long age, String occupation, Double salary) {
            this.name = name;
            this.age = age;
            this.occupation = occupation;
            this.salary = salary;
        }

        // other methods ignored
    }
    ```

## 2. Implement the Builder class

### :material-play-box-multiple-outline: Steps

1. Create a class called `PersonBuilder` in the `expert.os.labs.persistence` package
2. Add the same fields we had previously added to the `Person` class, without the annotations
3. Add the builder methods for each field
4. Add the `build()` method creating a new instance of `Person` using its constructor
5. In the `Person` class add the builder method referring to the `PersonBuilder`

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    public class PersonBuilder {
        private Long id;
        private String name;
        private Long age;
        private String occupation;
        private Double salary;

        public PersonBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PersonBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PersonBuilder age(Long age) {
            this.age = age;
            return this;
        }

        public PersonBuilder occupation(String occupation) {
            this.occupation = occupation;
            return this;
        }

        public PersonBuilder salary(Double salary) {
            this.salary = salary;
            return this;
        }

        public Person build() {
            return new Person(name, age, occupation, salary);
        }
    }
    ```


## 3. Create the service class

1. Create a class called `PersonService` in the `expert.os.labs.persistence` package
2. Add all the fields and methods from the code below to the class

    ```java
    import jakarta.enterprise.context.ApplicationScoped;
    import jakarta.inject.Inject;
    import org.eclipse.jnosql.mapping.graph.GraphTemplate;
    import static org.apache.tinkerpop.gremlin.process.traversal.P.between;
    import static org.apache.tinkerpop.gremlin.process.traversal.P.gte;

    @ApplicationScoped
    public class PersonService {

        @Inject
        private GraphTemplate graph;

        public Person save(Person person) {
            // Save a person entity to the graph database
        }

        void knows(Person person, Person friend) {
            // Establish a "knows" relationship between two persons
        }

        void love(Person person, Person friend) {
            // Establish a "knows" relationship with a "feel" attribute set to "love"
        }

        List<Person> developers() {
            // Retrieve a list of persons with the occupation "Developer" and salary >= 3000 and age between 20 and 25
        }

        List<Person> whoDevelopersKnows() {
            // Retrieve a list of persons known by developers with salary >= 3000 and age between 20 and 25
        }

        List<Person> both() {
            // Retrieve a list of persons who are developers and known by developers with salary >= 3000 and age between 20 and 25
        }

        List<Person> couple() {
            // Retrieve a list of developers who are in love (have a "feel" attribute set to "love")
        }
    }
    ```

3. Take some time to understand what each method must do by reading the comments in the code or looking at the following table

    | Method | Implementation | 
    |--|--|
    | `save` | Save or retrieve a person that has a `name` using the `traversalVertex()` method from `graph`|
    | `knows` | Create a relationship between two people using the label `"knows"`|
    | `love` | Create a relationship between two people using the label `"knows"` adding two more relationships: `"feels"` and `"love"` |
    | `developers` | Retrieve a list of people who has the occupation as `"developer"` and is a `"Developer"` |
    | `whoDevelopersKnows` | Retrieve a list of people by developers with salary >= 3000 and age between 20 and 25 |
    | `both` | Retrieve a list of people who are developers and known by developers with salary >= 3000 and age between 20 and 25 |
    | `couple` | Retrieve a list of developers who are in love (have a "feel" attribute set to "love") |

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.enterprise.context.ApplicationScoped;
    import jakarta.inject.Inject;
    import org.eclipse.jnosql.mapping.graph.GraphTemplate;

    import java.util.List;

    import static org.apache.tinkerpop.gremlin.process.traversal.P.between;
    import static org.apache.tinkerpop.gremlin.process.traversal.P.gte;

    @ApplicationScoped
    public class PersonService {

        @Inject
        private GraphTemplate graph;

        public Person save(Person person) {
            return graph.traversalVertex().hasLabel(Person.class)
                .has("name", person.getName()).<Person>next()
                .orElseGet(() -> graph.insert(person));
        }

        void knows(Person person, Person friend) {
            this.graph.edge(person, "knows", friend);
        }

        void love(Person person, Person friend) {
            this.graph.edge(person, "knows", friend).add("feel", "love");
        }

        List<Person> developers() {
            return graph.traversalVertex().has("occupation", "Developer")
                .<Person>result().toList();
        }

        List<Person> whoDevelopersKnows() {
            return graph.traversalVertex().has("salary", gte(3_000D))
                .has("age", between(20, 25)).has("occupation", "Developer").out("knows")
                .<Person>result().toList();
        }

        List<Person> both() {
            return graph.traversalVertex().has("salary", gte(3_000D)).has("age", between(20, 25))
                .has("occupation", "Developer").outE("knows").bothV().<Person>result()
                .distinct().toList();
        }

        List<Person> couple() {
            return graph.traversalVertex().has("salary", gte(3_000D)).has("age", between(20, 25))
                .has("occupation", "Developer").outE("knows").has("feel", "love").bothV()
                .<Person>result().distinct().toList();
        }
    }
    ```

## 4. Create the execution class

### :material-play-box-multiple-outline: Steps

1. Create a class called `AppMarketing` in the `expert.os.labs.persistence` package
2. Add the necessary below code to the class

    ```java
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;
    import java.util.List;

    public final class AppMarketing {

        private AppMarketing() {
        }

        public static void main(String[] args) {
            try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

                PersonService service = container.select(PersonService.class).get();

                // Create and save persons using the builder
                Person banner = service.save(Person.builder().age(30L).name("Banner")
                    .occupation("Developer").salary(3_000D).build());

                // Create and save more persons here...

                // Establish "knows" and "love" relationships between persons

                // Use the methods provided by the PersonService to query the graph database
                List<Person> developers = service.developers();
                List<Person> peopleWhoDeveloperKnows = service.whoDevelopersKnows();
                List<Person> both = service.both();
                List<Person> couple = service.couple();

                // Print the results
                System.out.println("Developers: " + developers);
                System.out.println("People who Developers know: " + peopleWhoDeveloperKnows);
                System.out.println("Both Developers and Known Persons: " + both);
                System.out.println("Developers in Love: " + couple);
            }
        }
    }
    ```

3. Take some time to understand what each method must do by reading the comments and implementing what's necessary in this class

4. Run the `main()` method

### :material-checkbox-multiple-outline: Expected results

* The following output
   
    ```
    Developers has salary greater than 3000 and age between 20 and 25: [Person{id=12, name='Banner', age=30, occupation='Developer', salary=3000.0}, Person{id=13, name='Natalia', age=32, occupation='Developer', salary=5000.0}, Person{id=15, name='tony', age=22, occupation='Developer', salary=4500.0}]
    
    Person who the Developers target know: [Person{id=14, name='Rose', age=40, occupation='Design', salary=1000.0}, Person{id=13, name='Natalia', age=32, occupation='Developer', salary=5000.0}]
    
    The person and the developers target: [Person{id=15, name='tony', age=22, occupation='Developer', salary=4500.0}, Person{id=14, name='Rose', age=40, occupation='Design', salary=1000.0}, Person{id=15, name='tony', age=22, occupation='Developer', salary=4500.0}, Person{id=13, name='Natalia', age=32, occupation='Developer', salary=5000.0}]
    
    Developers to Valentine days: [Person{id=15, name='tony', age=22, occupation='Developer', salary=4500.0}, Person{id=14, name='Rose', age=40, occupation='Design', salary=1000.0}]
    ```

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;
    import java.util.List;

    public final class AppMarketing {

        private AppMarketing() {
        }

        public static void main(String[] args) {
            try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

                PersonService service = container.select(PersonService.class).get();

                Person banner = service.save(Person.builder().age(30L).name("Banner")
                    .occupation("Developer").salary(3_000D).build());

                Person natalia = service.save(Person.builder().age(32L).name("Natalia")
                    .occupation("Developer").salary(5_000D).build());

                Person rose = service.save(Person.builder().age(40L).name("Rose")
                    .occupation("Design").salary(1_000D).build());

                Person tony =service.save(Person.builder().age(22L).name("tony")
                    .occupation("Developer").salary(4_500D).build());

                service.love(tony, rose);
                service.knows(tony, natalia);
                service.knows(natalia, rose);
                service.knows(banner, rose);

                List<Person> developers = service.developers();

                List<Person> peopleWhoDeveloperKnows =service.whoDevelopersKnows();

                List<Person> both = service.both();

                List<Person> couple = service.couple();

                System.out.println("Developers has salary greater than 3000 and age between 20 and 25: " + developers);
                System.out.println("Person who the Developers target know: " + peopleWhoDeveloperKnows);
                System.out.println("The person and the developers target: " + both);
                System.out.println("Developers to Valentine days: " + couple);
            }
        }
    }
    ```
