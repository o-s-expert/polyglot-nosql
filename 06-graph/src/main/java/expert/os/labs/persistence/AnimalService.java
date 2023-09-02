package expert.os.labs.persistence;


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
