# Neo4J and Java Lab


## Second scenario

In this lab, we will explor more about Neo4J and Java.

material-play-box-multiple-outline: Steps

**Step 1: Create the `Person` Class**

```java
import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

import java.util.Objects;

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

    public static PersonBuilder builder() {
        return new PersonBuilder();
    }

    public static class PersonBuilder {
        // Builder class is present in the provided code
    }
}
```

**Step 2: Create the `PersonService` Class**

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

**Step 3: Create the `MarketingApp` Class**

```java
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import java.util.List;

import static expert.os.labs.persistence.Person.builder;

public final class MarketingApp {

    private MarketingApp() {
    }

    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

            PersonService service = container.select(PersonService.class).get();

            // Create and save persons using the builder
            Person banner = service.save(builder().withAge(30L).withName("Banner")
                    .withOccupation("Developer").withSalary(3_000D).build());

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

In this example, we have a `Person` class to represent individuals, a `PersonService` class to interact with the graph database, and a `MarketingApp` class to run the application. The application allows you to create and query relationships between persons based on their characteristics such as occupation, salary, age, and feelings like "love."

### :material-check-outline: Solution

??? example "Click to see..."

```java

import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

import java.util.Objects;


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


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public Long getAge() {
        return age;
    }

    Person() {
    }

    Person(String name, Long age, String occupation, Double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.occupation = occupation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        Person person = (Person) o;
        return Objects.equals(id, person.id);
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
                ", age=" + age +
                ", occupation='" + occupation + '\'' +
                ", salary=" + salary +
                '}';
    }

    public static PersonBuilder builder() {
        return new PersonBuilder();
    }

    public static class PersonBuilder {

        private String name;

        private Long age;

        private String occupation;

        private Double salary;


        public PersonBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public PersonBuilder withAge(Long age) {
            this.age = age;
            return this;
        }

        public PersonBuilder withOccupation(String occupation) {
            this.occupation = occupation;
            return this;
        }

        public PersonBuilder withSalary(Double salary) {
            this.salary = salary;
            return this;
        }

        public Person build() {
            return new Person(name, age, occupation, salary);
        }
    }

}

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
                .has("name", person.getName())
                .<Person>next()
                .orElseGet(() -> graph.insert(person));
    }

    void knows(Person person, Person friend) {
        this.graph.edge(person, "knows", friend);
    }

    void love(Person person, Person friend) {
        this.graph.edge(person, "knows", friend).add("feel", "love");
    }

    List<Person> developers() {
        return graph.traversalVertex()
                .has("occupation", "Developer")
                .<Person>result().toList();
    }

    List<Person> whoDevelopersKnows() {
        return graph.traversalVertex()
                .has("salary", gte(3_000D))
                .has("age", between(20, 25))
                .has("occupation", "Developer")
                .out("knows")
                .<Person>result().toList();
    }

    List<Person> both() {
        return graph.traversalVertex()
                .has("salary", gte(3_000D))
                .has("age", between(20, 25))
                .has("occupation", "Developer")
                .outE("knows")
                .bothV()
                .<Person>result()
                .distinct()
                .toList();
    }

    List<Person> couple() {
        return graph.traversalVertex()
                .has("salary", gte(3_000D))
                .has("age", between(20, 25))
                .has("occupation", "Developer")
                .outE("knows")
                .has("feel", "love")
                .bothV()
                .<Person>result()
                .distinct()
                .toList();
    }
}

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.util.List;

import static expert.os.labs.persistence.Person.builder;

public final class MarketingApp {


    private MarketingApp() {
    }


    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

            PersonService service = container.select(PersonService.class).get();

            Person banner = service.save(builder().withAge(30L).withName("Banner")
                    .withOccupation("Developer").withSalary(3_000D).build());

            Person natalia = service.save(builder().withAge(32L).withName("Natalia")
                    .withOccupation("Developer").withSalary(5_000D).build());

            Person rose = service.save(builder().withAge(40L).withName("Rose")
                    .withOccupation("Design").withSalary(1_000D).build());

            Person tony =service.save(builder().withAge(22L).withName("tony")
                    .withOccupation("Developer").withSalary(4_500D).build());

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