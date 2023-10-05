package expert.os.labs.persistence.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.jnosql.mapping.graph.GraphTemplate;

import java.util.List;

import static org.apache.tinkerpop.gremlin.process.traversal.P.between;
import static org.apache.tinkerpop.gremlin.process.traversal.P.gte;

@ApplicationScoped
public class PersonService {

    @Inject
    private GraphTemplate graph;

    public Person save(Person person) {
        return graph.traversalVertex().hasLabel(Person.class)
                .has("name", person.getName())
                .<Person>next()
                .orElseGet(() -> graph.insert(person));
    }

    void knows(Person person, Person friend) {
        this.graph.edge(person, "knows", friend);
    }

    void love(Person person, Person friend) {
        this.graph.edge(person, "knows", friend).add("feel", "love");
    }

    List<Person> developers() {
        return graph.traversalVertex()
                .has("occupation", "Developer")
                .<Person>result().toList();
    }

    List<Person> whoDevelopersKnows() {
        return graph.traversalVertex()
                .has("salary", gte(3_000D))
                .has("age", between(20, 25))
                .has("occupation", "Developer")
                .out("knows")
                .<Person>result().toList();
    }

    List<Person> both() {
        return graph.traversalVertex()
                .has("salary", gte(3_000D))
                .has("age", between(20, 25))
                .has("occupation", "Developer")
                .outE("knows")
                .bothV()
                .<Person>result()
                .distinct()
                .toList();
    }

    List<Person> couple() {
        return graph.traversalVertex()
                .has("salary", gte(3_000D))
                .has("age", between(20, 25))
                .has("occupation", "Developer")
                .outE("knows")
                .has("feel", "love")
                .bothV()
                .<Person>result()
                .distinct()
                .toList();
    }
}
