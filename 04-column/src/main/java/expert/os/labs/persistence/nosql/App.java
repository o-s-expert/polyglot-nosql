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

package expert.os.labs.persistence.nosql;


import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.nosql.column.ColumnTemplate;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


public class App {


    public static void main(String[] args) throws InterruptedException {

        try(SeContainer container = SeContainerInitializer.newInstance().initialize()) {

            Person otaviojava = Person.builder()
                    .contacts(Map.of("twitter", "otaviojava", "linkedin", "otaviojava",
                            "youtube", "otaviojava"))
                    .name("Otavio Santana").id(1).build();

            Person elderjava = Person.builder()
                    .contacts(Map.of("twitter", "elderjava", "linkedin", "elderjava",
                            "youtube", "elderjava"))
                    .name("Elder Moraes").id(2).build();

            ColumnTemplate template =  container.select(ColumnTemplate.class).get();
            template.insert(otaviojava);

            template.insert(elderjava, Duration.ofSeconds(1));

            System.out.println("The elder find: " + template.find(Person.class, 2L));

            TimeUnit.SECONDS.sleep(2L);

            System.out.println("The elder find: " + template.find(Person.class, 2L));

            Optional<Person> person = template.select(Person.class)
                    .where("id").eq(1L).singleResult();
            System.out.println("Entity found: " + person);

        }
    }

    private App() {}
}
