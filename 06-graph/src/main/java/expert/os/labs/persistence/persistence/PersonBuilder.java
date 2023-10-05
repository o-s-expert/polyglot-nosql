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

public class PersonBuilder {
    private Long id;
    private String name;
    private Long age;
    private String occupation;
    private Double salary;

    public PersonBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public PersonBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PersonBuilder age(Long age) {
        this.age = age;
        return this;
    }

    public PersonBuilder occupation(String occupation) {
        this.occupation = occupation;
        return this;
    }

    public PersonBuilder salary(Double salary) {
        this.salary = salary;
        return this;
    }

    public Person build() {
        return new Person(name, age, occupation, salary);
    }
}