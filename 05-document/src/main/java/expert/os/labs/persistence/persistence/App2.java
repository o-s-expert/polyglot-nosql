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

public class App2 {


    public static void main(String[] args) {
        Faker faker = new Faker();
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

            Library library = container.select(Library.class).get();

            for (int index = 0; index < 100; index++) {
                Book book = Book.of(faker);
                library.save(book);
            }

            Pageable pageable = Pageable.ofSize(10).sortBy(Sort.asc("title"),
                    Sort.desc("year"));

            Page<Book> page = library.findAll(pageable);
            System.out.println("Page: " + page.content());
            var pageable2 = pageable.next();
            var page2 = library.findAll(pageable2);
            System.out.println("Page 2: " + page2.content());
            System.out.println("The result: ");
            library.findByTitle("Effective Java").forEach(System.out::println);

        }
    }

    private App2() {
    }
}
