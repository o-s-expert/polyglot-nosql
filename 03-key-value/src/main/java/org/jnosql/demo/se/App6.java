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

package org.jnosql.demo.se;


import jakarta.nosql.keyvalue.KeyValueTemplate;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class App6 {


    public static void main(String[] args) throws InterruptedException {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            KeyValueTemplate template = container.select(KeyValueTemplate.class).get();
            User otaviojava = User.builder().username("otaviojava").name("Otavio Santana")
                    .languages(Set.of("Portuguese", "English", "Spanish", "Italian", "French"))
                    .settings(Map.of("location", "Portugal", "currency", "EUR")).build();


            User poliana = User.builder().username("polianapo").name("Poliana Santana")
                    .languages(Set.of("Portuguese", "English"))
                    .settings(Map.of("location", "Portugal", "currency", "EUR")).build();

            template.put(otaviojava);
            template.put(poliana, Duration.ofSeconds(1));
            System.out.println("Find Poliana : " + template.get("polianapo", User.class));

            TimeUnit.SECONDS.sleep(2L);
            System.out.println("Find Poliana : " + template.get("polianapo", User.class));
            Optional<User> user = template.get("otaviojava", User.class);
            System.out.println("Entity found: " + user);

        }
    }

    private App6() {
    }
}
