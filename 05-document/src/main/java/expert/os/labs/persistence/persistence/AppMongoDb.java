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


import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.nosql.document.DocumentTemplate;

import java.util.Optional;
import java.util.UUID;

public class AppMongoDb {

    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            Book book = new Book(UUID.randomUUID().toString(), "Effective Java", 1, 2019);

            DocumentTemplate template = container.select(DocumentTemplate.class).get();
            Book saved = template.insert(book);
            System.out.println("Book saved: = " + saved);

            Optional<Book> bookFound = template.select(Book.class).where("title").eq("Effective Java").singleResult();
            System.out.println("Book Found = " + bookFound);
        }
    }

    private AppMongoDb() {
    }
}
