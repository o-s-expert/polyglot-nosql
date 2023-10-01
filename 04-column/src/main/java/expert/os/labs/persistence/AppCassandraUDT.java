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

import java.util.List;

public class AppCassandraUDT {

    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            MovieRepository repository = container.select(MovieRepository.class).get();

            Movie matrix = new Movie();
            matrix.setName("The Matrix");
            matrix.setAge(1999);
            matrix.setDirector(Director.builder().name("Lana Wachowski")
                .addMovie("The Matrix").addMovie("The Matrix Reloaded").addMovie("Assassins").build());

            Movie fightClub = new Movie();
            fightClub.setName("Fight Club");
            fightClub.setAge(1999);
            fightClub.setDirector(Director.builder().name("David Fincher")
                .addMovie("Fight Club").addMovie("Seven").addMovie("The Social Network").build());

            Movie americanBeauty = new Movie();
            americanBeauty.setName("American Beauty");
            americanBeauty.setAge(1999);
            americanBeauty.setDirector(Director.builder().name("Sam Mendes")
                .addMovie("Spectre").addMovie("SkyFall").addMovie("American Beauty").build());

            repository.saveAll(List.of(matrix, fightClub, americanBeauty));

            List<Movie> allMovies = repository.findAllQuery();
            System.out.println("All movies = " + allMovies);

            List<Movie> movieByAge = repository.findByAge(1999);
            System.out.println("Movies from 1999 = " + movieByAge);
        }
    }

    public AppCassandraUDT() {
    }
}
