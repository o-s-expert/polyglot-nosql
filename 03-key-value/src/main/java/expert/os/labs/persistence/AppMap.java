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
import org.eclipse.jnosql.communication.keyvalue.BucketManagerFactory;
import org.eclipse.jnosql.databases.redis.communication.RedisBucketManagerFactory;

import java.util.Map;
import java.util.Queue;

public class AppMap {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            BucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();
            
            Map<Integer, String> basket = factory.getMap("basket", Integer.class, String.class);
            basket.clear();
            basket.put(1, "Banana");
            basket.put(2, "Watermelon");
            basket.put(4, "Apple");

            System.out.println("Basket: ");
            basket.forEach((k, v) -> System.out.println(k + " - " + v));
        }
    }

    private AppMap() {
    }
}
