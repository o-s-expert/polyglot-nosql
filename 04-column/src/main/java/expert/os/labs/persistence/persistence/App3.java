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
