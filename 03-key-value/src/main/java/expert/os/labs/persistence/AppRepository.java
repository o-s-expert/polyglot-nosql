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

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AppRepository {

        public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

            UserRepository repository = container.select(UserRepository.class).get();
            User ada = User.builder().username("ada").name("Ada Lovelace")
                .languages(Set.of("Latin")).settings(Map.of("currency", "food")).build();

            repository.save(ada);

            Optional<User> userFound = repository.findById("ada");
            System.out.println(userFound);

            boolean userExist = repository.existsById("ada");
            System.out.println("userExist? = " + userExist);

            repository.deleteById("ada");
            userExist = repository.existsById("ada");
            System.out.println("userExist? = " + userExist);
        }
    }

    private AppRepository() {
    }
}
