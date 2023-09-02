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


import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.eclipse.jnosql.databases.redis.communication.Ranking;
import org.eclipse.jnosql.databases.redis.communication.RedisBucketManagerFactory;
import org.eclipse.jnosql.databases.redis.communication.SortedSet;

import java.util.List;
import java.util.Map;

public class App4 {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            RedisBucketManagerFactory factory = container.select(RedisBucketManagerFactory.class).get();
            SortedSet game = factory.getSortedSet("game");
            game.add("Otavio", 10);
            game.add("Luiz", 20);
            game.add("Ada", 30);
            game.add(Ranking.of("Poliana", 40));

            List<Ranking> ranking = game.getRanking();
            System.out.println("Ranking: " + ranking);

            System.out.println("The reverse ranking: " + game.getRevRanking());


        }
    }

    private App4() {
    }
}
