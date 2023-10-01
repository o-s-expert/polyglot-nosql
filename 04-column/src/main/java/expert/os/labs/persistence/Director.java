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

import java.util.Set;

@Entity
public class Director {

    @Column
    private String name;

    @Column
    private Set<String> movies;

    public Director(String name, Set<String> movies) {
        this.name = name;
        this.movies = movies;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMovies(Set<String> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "Director{" +
            "name='" + name + '\'' +
            ", movies=" + movies +
            '}';
    }

    public static DirectorBuilder builder() {
        return new DirectorBuilder();
    }
}
