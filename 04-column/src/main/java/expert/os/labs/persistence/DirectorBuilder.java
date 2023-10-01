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

import java.util.Set;

public class DirectorBuilder {

    private String name;
    private final Set<String> movies = new HashSet<>();

    public DirectorBuilder name(String name) {
        this.name = name;
        return this;
    }

    public DirectorBuilder addMovie(String movie) {
        this.movies.add(movie);
        return this;
    }

    public Director build() {
        return new Director(name, movies);
    }
}
