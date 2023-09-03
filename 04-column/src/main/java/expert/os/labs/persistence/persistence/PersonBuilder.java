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

import java.util.Collections;
import java.util.Map;

public class PersonBuilder {
    private long id;
    private String name;
    private Map<String, String> contacts = Collections.emptyMap();
    PersonBuilder() {
    }

    public PersonBuilder id(long id) {
        this.id = id;
        return this;
    }

    public PersonBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PersonBuilder contacts(Map<String, String> contacts) {
        this.contacts = contacts;
        return this;
    }

    public Person build() {
        return new Person(id, name, contacts);
    }
}