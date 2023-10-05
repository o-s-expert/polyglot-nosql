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
