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

package expert.os.labs.persistence;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.nosql.column.ColumnTemplate;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class AppCassandraOperations {

    public static void main(String[] args) throws InterruptedException {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

            Person user1 = Person.builder()
                .contacts(Map.of("twitter", "otaviojava", "linkedin", "otaviojava", "youtube", "otaviojava"))
                .name("Otavio Santana").id(1).build();

            Person user2 = Person.builder()
                .contacts(Map.of("twitter", "elderjava", "linkedin", "elderjava", "youtube", "elderjava"))
                .name("Elder Moraes").id(2).build();

            ColumnTemplate template = container.select(ColumnTemplate.class).get();

            template.insert(user1);
            template.insert(user2, Duration.ofSeconds(1));

            Optional<Person> person2 = template.find(Person.class, 2L);
            System.out.println("Person2 data: " + person2);

            TimeUnit.SECONDS.sleep(2L);
            person2 = template.find(Person.class, 2L);
            System.out.println("Person2 second retrieve data: " + person2);

            Optional<Person> person1 = template.find(Person.class, 1L);
            System.out.println("Person1 data: " + person1);
        }
    }
}
