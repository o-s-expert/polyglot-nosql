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
import jakarta.data.repository.Page;
import jakarta.data.repository.Pageable;
import jakarta.data.repository.Sort;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.util.stream.IntStream;

public class AppMongoDBPagable {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            Library library = container.select(Library.class).get();


            Faker faker = new Faker();
            IntStream.range(0, 100).mapToObj(index -> Book.of(faker)).forEach(library::save);

            Pageable pageable = Pageable.ofSize(10).sortBy(Sort.asc("title"), Sort.desc("year"));
            Page<Book> page = library.findAll(pageable);
            System.out.println("Page = " + page.content());

            Pageable nextPage = pageable.next();
            Page<Book> page2 = library.findAll(nextPage);
            System.out.println("Page 2 = " + page2);

            library.findByTitle("Effective Java").forEach(System.out::println);
        }
    }

    private AppMongoDBPagable() {
    }
}
