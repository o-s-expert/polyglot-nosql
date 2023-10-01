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

package expert.os.labs.persistence;


import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;


@Entity("Person")
public class Person {

    @Id("id")
    private long id;

    @Column
    private String name;

    @Column
    private Map<String, String> contacts;

    public Person() {
    }

    Person(long id, String name, Map<String, String> contacts) {
        this.id = id;
        this.name = name;
        this.contacts = contacts;
    }

    public long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Map<String, String> contacts() {
        return Collections.unmodifiableMap(contacts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return id == person.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contacts=" + contacts +
                '}';
    }

    public static PersonBuilder builder() {
        return new PersonBuilder();
    }
}
