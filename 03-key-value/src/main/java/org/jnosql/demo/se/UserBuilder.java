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

package org.jnosql.demo.se;

import java.util.Collections;
import java.util.Map;
import java.util.Set;


public class UserBuilder {

    private String username;

    private String name;

    private Map<String, String> settings = Collections.emptyMap();

    private Set<String> languages = Collections.emptySet();

    public UserBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder name(String name) {
        this.name = name;
        return this;
    }


    public UserBuilder settings(Map<String, String> settings) {
        this.settings = settings;
        return this;
    }

    public UserBuilder languages(Set<String> languages) {
        this.languages = languages;
        return this;
    }

    public User build() {
        return new User(username, name, settings, languages);
    }
}
