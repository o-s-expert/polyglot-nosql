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

package expert.os.labs;


import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.eclipse.jnosql.communication.keyvalue.BucketManagerFactory;
import org.eclipse.jnosql.databases.redis.communication.RedisBucketManagerFactory;

import java.util.List;
import java.util.Set;

public class App {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            BucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();
            List<String> names = factory.getList("names", String.class);
            Set<String> fruits = factory.getSet("fruits", String.class);

            names.addAll(List.of("Otavio", "Elias", "Ada", "Poliana", "Otavio"));

            fruits.addAll(List.of("Banana", "Banana", "Apple", "Watermelon", "Banana", "Apple"));

            System.out.println("Names: ");
            names.forEach(System.out::println);
            System.out.println("Fruits: ");
            fruits.forEach(System.out::println);


        }
    }

    private App() {
    }
}
