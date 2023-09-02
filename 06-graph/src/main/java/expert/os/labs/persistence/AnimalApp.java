package expert.os.labs.persistence;

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
