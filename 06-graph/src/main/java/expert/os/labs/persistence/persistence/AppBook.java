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

import java.util.List;

public class AppBook {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            BookService service = container.select(BookService.class).get();

            Category software = service.category("Software");
            Category romance = service.category("Romance");
            Category java = service.category("Java");
            Category nosql =service.category("NoSQL");
            Category microService =service.category("MicroService");

            Book effectiveJava = service.book("Effective Java");
            Book nosqlDistilled = service.book("NoSQL Distilled");
            Book migratingMicroservice = service.book("Migrating to Microservice Databases");
            Book shack = service.book("The Shack");

            service.category(java, software);
            service.category(nosql, software);
            service.category(microService, software);

            service.category(effectiveJava, software);
            service.category(nosqlDistilled, software);
            service.category(migratingMicroservice, software);

            service.category(effectiveJava, java);
            service.category(nosqlDistilled, nosql);
            service.category(migratingMicroservice, microService);
            service.category(shack, romance);

            List<String> softwareCategories =service.softwareCategories();
            System.out.println("Software Categories = " + softwareCategories);

            List<String> softwareBooks = service.softwareBooks();
            System.out.println("Software Books = " + softwareBooks);

            List<String> softwareNoSQLBooks = service.softwareNoSQLBooks();
            System.out.println("Software and NoSQL Books = " + softwareNoSQLBooks);
        }
    }

    private AppBook() {
    }
}
