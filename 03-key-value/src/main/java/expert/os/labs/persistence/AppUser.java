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
import jakarta.nosql.keyvalue.KeyValueTemplate;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class AppUser {

    public static void main(String[] args) throws InterruptedException {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            KeyValueTemplate template = container.select(KeyValueTemplate.class).get();

            User user1 = User.builder().username("user1").name("Otavio Santana")
                .languages(Set.of("Portuguese", "English", "Spanish", "Italian", "French"))
                .settings(Map.of("location", "Portugal", "currency", "EUR")).build();


            User user2 = User.builder().username("user2").name("Poliana Santana")
                .languages(Set.of("Portuguese", "English"))
                .settings(Map.of("location", "Portugal", "currency", "EUR")).build();


            template.put(user1);
            template.put(user2, Duration.ofSeconds(1));

            Optional<User> user2Data = template.get("user2", User.class);
            System.out.println("User2 data: " + user2Data);

            TimeUnit.SECONDS.sleep(2L);
            Optional<User> user2DataSecondRetrieve = template.get("user2", User.class);
            System.out.println("User2 second retrieve data: " + user2DataSecondRetrieve);

            Optional<User> user1Data = template.get("user1", User.class);
            System.out.println("User1 data: " + user1Data);
        }
    }
}
