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

import static expert.os.labs.persistence.persistence.Person.builder;

public final class MarketingApp {


    private MarketingApp() {
    }


    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

            PersonService service = container.select(PersonService.class).get();

            Person banner = service.save(builder().withAge(30L).withName("Banner")
                    .withOccupation("Developer").withSalary(3_000D).build());

            Person natalia = service.save(builder().withAge(32L).withName("Natalia")
                    .withOccupation("Developer").withSalary(5_000D).build());

            Person rose = service.save(builder().withAge(40L).withName("Rose")
                    .withOccupation("Design").withSalary(1_000D).build());

            Person tony =service.save(builder().withAge(22L).withName("tony")
                    .withOccupation("Developer").withSalary(4_500D).build());

            service.love(tony, rose);
            service.knows(tony, natalia);
            service.knows(natalia, rose);
            service.knows(banner, rose);

            List<Person> developers = service.developers();

            List<Person> peopleWhoDeveloperKnows =service.whoDevelopersKnows();

            List<Person> both = service.both();

            List<Person> couple = service.couple();

            System.out.println("Developers has salary greater than 3000 and age between 20 and 25: " + developers);
            System.out.println("Person who the Developers target know: " + peopleWhoDeveloperKnows);
            System.out.println("The person and the developers target: " + both);
            System.out.println("Developers to Valentine days: " + couple);

        }
    }
}

