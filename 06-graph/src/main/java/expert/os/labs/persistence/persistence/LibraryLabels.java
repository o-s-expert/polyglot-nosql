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

import java.util.function.Supplier;

public enum LibraryLabels implements Supplier<String> {

    IS("is");

    private final String value;

    LibraryLabels(String value) {
        this.value = value;
    }

    @Override
    public String get() {
        return value;
    }
}
