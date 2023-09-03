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


import com.datastax.oss.driver.api.core.ConsistencyLevel;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.eclipse.jnosql.databases.cassandra.mapping.CassandraTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class App2 {


    public static void main(String[] args) {

        try(SeContainer container = SeContainerInitializer.newInstance().initialize()) {

            Person ada = Person.builder()
                    .contacts(Map.of("twitter", "ada"))
                    .name("Ada Lovelace").id(3).build();

            CassandraTemplate template =  container.select(CassandraTemplate.class).get();
            template.save(ada, ConsistencyLevel.ONE);


            List<Person> people = template.<Person>cql("select * from developers.Person where id = 1")
                    .collect(Collectors.toList());
            System.out.println("Entity found: " + people);

        }
    }

    private App2() {}
}
