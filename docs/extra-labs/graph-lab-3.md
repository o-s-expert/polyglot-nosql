# Neo4J and Java Lab 3


## Third scenario

In this lab, we will explore more about Neo4J and Java.

material-play-box-multiple-outline: Steps

**Step 1: Create the `Animal` Class**

```java
import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

@Entity
public record Animal(@Id Long id, @Column String name) {

    public static Animal of(String name){
        return new Animal(null, name);
    }
}
```

- The `Animal` class is defined as a record, which is a concise way to create simple classes to store data.
- It has two properties: `id` and `name`, which are annotated with `@Id` and `@Column` respectively to specify their roles in the database.
- A static factory method `of` is provided to create instances of `Animal`.

**Step 2: Create the `AnimalService` Class**

```java
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.jnosql.mapping.graph.EntityTree;
import org.eclipse.jnosql.mapping.graph.GraphTemplate;

import java.util.List;

@ApplicationScoped
public class AnimalService {

    @Inject
    private GraphTemplate template;

    Animal animal(String name) {
        return template.traversalVertex().hasLabel(Animal.class)
                .has("name", name)
                .<Animal>next()
                .orElseGet(() -> template.insert(Animal.of(name)));
    }

    public void eats(Animal animal, Animal animal2){
        this.template.edge(animal, "eats", animal2);
    }

    List<Animal> eatsTwice() {
        return template.traversalVertex().hasLabel(Animal.class)
                .repeat().in("eats").times(2).<Animal>result().toList();
    }

    List<Animal> untilGrass() {
        return template.traversalVertex().hasLabel(Animal.class)
                .repeat().out("eats").until().has("name", "grass").<Animal>result()
                .distinct().toList();
    }

    public EntityTree entityTree() {
        return template.traversalVertex().hasLabel(Animal.class)
                .repeat().in("eats").times(4).tree();
    }
}
```

- The `AnimalService` class is annotated with `@ApplicationScoped`, indicating that it's a bean managed by the application container.
- It injects a `GraphTemplate` to interact with the graph database.
- The `animal` method is used to retrieve or insert an `Animal` entity based on its name.
- The `eats` method establishes an "eats" relationship between two `Animal` entities.
- `eatsTwice` returns a list of animals that eat twice in the graph.
- `untilGrass` returns a list of animals that eat until they reach an animal named "grass."
- `entityTree` returns an `EntityTree` representing the tree structure of animals.

**Step 3: Create the `AnimalApp` Class**

```java
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.eclipse.jnosql.mapping.graph.EntityTree;

public class AnimalApp {

    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

            AnimalService service = container.select(AnimalService.class).get();

            // Create and establish "eats" relationships between animals
            Animal grass = service.animal("grass");
            Animal grasshopper = service.animal("grasshopper");
            Animal frog = service.animal("frog");
            Animal snake = service.animal("snake");
            Animal eagle = service.animal("eagle");

            service.eats(eagle, snake);
            service.eats(snake, frog);
            service.eats(frog, grasshopper);
            service.eats(grasshopper, grass);

            // Use service methods to query the graph database
            System.out.println("The animals that eat twice: " + service.eatsTwice());
            EntityTree entityTree = service.entityTree();
            System.out.println("The animals that eat until grass: " + service.untilGrass());
            System.out.println("At the top of the entity tree: " + entityTree.getLeaf().toList());
        }
    }
}
```

- In the `AnimalApp` class, a SeContainer is initialized to manage the application's lifecycle.
- An instance of `AnimalService` is retrieved from the container.
- Animals are created and "eats" relationships are established between them.
- The `AnimalService` methods are used to query the graph database.
- Results are printed to the console.

This application allows you to create, query, and explore relationships between animals in a graph database.

### :material-check-outline: Solution

??? example "Click to see..."

```java


import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;


@Entity
public record Animal(@Id Long id, @Column String name) {


    public static Animal of(String name){
        return new Animal(null, name);
    }
}


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.jnosql.mapping.graph.EntityTree;
import org.eclipse.jnosql.mapping.graph.GraphTemplate;

import java.util.List;

@ApplicationScoped
public class AnimalService {

    @Inject
    private GraphTemplate template;

    Animal animal(String name) {
        return template.traversalVertex().hasLabel(Animal.class)
                .has("name", name)
                .<Animal>next()
                .orElseGet(() -> template.insert(Animal.of(name)));
    }

    public void eats(Animal animal, Animal animal2){
        this.template.edge(animal, "eats", animal2);
    }

    List<Animal> eatsTwice() {
        return template.traversalVertex().hasLabel(Animal.class)
                .repeat().in("eats").times(2).<Animal>result().toList();
    }

    List<Animal> untilGrass() {
        return template.traversalVertex().hasLabel(Animal.class)
                .repeat().out("eats").until().has("name", "grass").<Animal>result()
                .distinct().toList();
    }


    public EntityTree entityTree() {
        return template.traversalVertex().hasLabel(Animal.class)
                .repeat().in("eats").times(4).tree();
    }
}


import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.eclipse.jnosql.mapping.graph.EntityTree;

public class AnimalApp {

    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

            AnimalService service = container.select(AnimalService.class).get();

            Animal grass = service.animal("grass");
            Animal grasshopper = service.animal("grasshopper");
            Animal frog = service.animal("frog");
            Animal snake = service.animal("snake");
            Animal eagle = service.animal("eagle");

            service.eats(eagle, snake);
            service.eats(snake, frog);
            service.eats(frog, grasshopper);
            service.eats(grasshopper, grass);
            System.out.println("The animals that eats twice: " + service.eatsTwice());
            EntityTree entityTree = service.entityTree();
            System.out.println("The animals that eats until grass: " + service.untilGrass());
            System.out.println("AT the top: " + entityTree.getLeaf().toList());
        }
    }
}

```