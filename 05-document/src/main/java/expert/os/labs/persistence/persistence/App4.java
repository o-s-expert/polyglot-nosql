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


import com.github.javafaker.Faker;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class App4 {


    public static void main(String[] args) {

        ThreadLocalRandom random = ThreadLocalRandom.current();
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            Faker faker = new Faker();
            Address address = new Address(faker.address().streetName(), faker.address().city());
            Job job = new Job(12.12, faker.job().title());
            Author author = Author.builder().
                    withPhones(Arrays.asList(faker.phoneNumber().cellPhone(), faker.phoneNumber().cellPhone()))
                    .withName(faker.name().fullName())
                    .withAddress(address)
                    .withJob(job)
                    .withId(random.nextLong()).build();

            AuthorRepository template = container.select(AuthorRepository.class).get();
             template.save(author);

            System.out.println("The entity find: " + template.findByName(author.getName()));
            System.out.println("The entity find: " + template.findByPhones(author.getPhones().get(0)).toList());

        }
    }

    private App4() {
    }
}
