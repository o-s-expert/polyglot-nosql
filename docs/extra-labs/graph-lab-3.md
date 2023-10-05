# Neo4J Lab - After Intermediate

This lab won't show the steps in much detail, instead it will be shorter and you need to figure out (with some tips) how to implement it.

Try to, first implement, and later, see the  solution!

## 1. Define the `Person` Entity Class

1. Create a `record` called `Animal` in the `expert.os.labs.persistence` package
2. Define the record with the `@Entity` annotation from the `jakarta.nosql` package
3. Add two fields to the record:
    - `@Id Long id`
    - `@Column String name`

4. Create static factory method `of` to provide a new instance of `Animal`

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
    ```

## 2. Create the service class

1. Create a class called `AnimalService` in the `expert.os.labs.persistence` package
2. Add all the fields and methods from the code below to the class and implements the empty methods

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
            // Create or retrieve a new Animal
        }

        public void eats(Animal animal, Animal animal2) {
            // Create a relation "eats" between the animals who are in the food chain
        }

        List<Animal> eatsTwice() {
            // Retrieve a list of animals who eats twice
        }

        List<Animal> untilGrass() {
            // Retrieves the animals that eat until they reach an animal named "grass"
        }

        public EntityTree entityTree() {
            // Return a `EntityTree` representing the tree structure of animals
        }
    }
    ```

### :material-check-outline: Solution

??? example "Click to see..."

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

## 3. Create the execution class

### :material-play-box-multiple-outline: Steps

1. Create a class called `AppAnimal` in the `expert.os.labs.persistence` package
2. Add the necessary below code to the class

    ```java
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;
    import org.eclipse.jnosql.mapping.graph.EntityTree;

    import java.util.List;

    public class AppAnimal {

        public static void main(String[] args) {
            try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

                AnimalService service = container.select(AnimalService.class).get();

                // Create and establish "eats" relationships between animals


                // Use the service method eats() to define which animal can eat another one based on the chain food


                // Retrieve and printout the list of animals that eats twice


                // Retrieve and printout the list of animals that eats grass


                // Retrieve the entity tree and print it out

            }
        }
    }
    ```

3. Take some time to understand what each method must do by reading the comments and implementing what's necessary in this class

4. Run the `main()` method

### :material-checkbox-multiple-outline: Expected results

* The following output
   
    ```
    The animals that eat twice: [Animal[id=18, name=frog], Animal[id=19, name=snake], Animal[id=20, name=eagle]]
    
    The animals that eat until grass: [Animal[id=16, name=grass]]

    At the top of the entity tree: [Animal[id=20, name=eagle]]
    ```

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;
    import org.eclipse.jnosql.mapping.graph.EntityTree;

    import java.util.List;

    public class AppAnimal {

        public static void main(String[] args) {
            try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

                AnimalService service = container.select(AnimalService.class).get();

                // Create and establish "eats" relationships between animals
                Animal grass = service.animal("grass");
                Animal grasshopper = service.animal("grasshopper");
                Animal frog = service.animal("frog");
                Animal snake = service.animal("snake");
                Animal eagle = service.animal("eagle");

                // Use the service method eats() to define which animal can eat another one based on the chain food
                service.eats(eagle, snake);
                service.eats(snake, frog);
                service.eats(frog, grasshopper);
                service.eats(grasshopper, grass);

                // Retrieve and printout the list of animals that eats twice
                List<Animal> animalEatsTwice = service.eatsTwice();
                System.out.println("The animals that eat twice: " + animalEatsTwice);

                // Retrieve and printout the list of animals that eats grass
                List<Animal> animalEatGrass = service.untilGrass();
                System.out.println("The animals that eat until grass: " + animalEatGrass);

                // Retrieve the entity tree and print it out
                EntityTree entityTree = service.entityTree();
                System.out.println("At the top of the entity tree: " + entityTree.getLeaf().toList());
            }
        }
    }
    ```
