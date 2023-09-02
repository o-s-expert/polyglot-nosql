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
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class App2 {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            BucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();
            Queue<String> orders = factory.getQueue("orders", String.class);
            orders.clear();
            orders.add("Phone");
            orders.add("Tablet");
            orders.add("book");
            // remove the element at the front of the queue
            String front = orders.remove();
            System.out.println("Front: " + front);
            // peek at the element at the front of the queue
            String peeked = orders.peek();
            System.out.println("Peeked element: " + peeked);
            System.out.println("the result");
            orders.forEach(System.out::println);
        }
    }

    private App2() {
    }
}
