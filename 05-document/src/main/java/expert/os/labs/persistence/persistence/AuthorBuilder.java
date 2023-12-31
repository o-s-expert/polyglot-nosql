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

import java.util.List;


public class AuthorBuilder {

    private long id;

    private String name;

    private List<String> phones;

    private Address address;

    private Job job;

    public AuthorBuilder id(long id) {
        this.id = id;
        return this;
    }

    public AuthorBuilder name(String name) {
        this.name = name;
        return this;
    }


    public AuthorBuilder phones(List<String> phones) {
        this.phones = phones;
        return this;
    }

    public AuthorBuilder address(Address address) {
        this.address = address;
        return this;
    }

    public AuthorBuilder job(Job job) {
        this.job = job;
        return this;
    }

    public Author build() {
        return new Author(id, name, phones, address, job);
    }
}
