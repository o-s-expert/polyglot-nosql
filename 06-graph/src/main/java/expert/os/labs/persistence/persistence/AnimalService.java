/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *  The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 *  and the Apache License v2.0 is available at http://www.opensource.org/licenses/apache2.0.php.
 *
 * You may elect to redistribute this code under either of these licenses.
 */
package expert.os.labs.persistence.persistence;

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
