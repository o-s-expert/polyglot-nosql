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


import jakarta.data.repository.Repository;
import org.eclipse.jnosql.databases.cassandra.mapping.CQL;
import org.eclipse.jnosql.databases.cassandra.mapping.CassandraRepository;

import java.util.List;

@Repository
public interface MovieRepository extends CassandraRepository<Movie, String> {


    List<Movie> findByAge(Integer age);

    @CQL("select * from developers.Movie")
    List<Movie> findAllQuery();
}
