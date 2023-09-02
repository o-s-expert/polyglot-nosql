package expert.os.labs;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.jnosql.databases.redis.communication.Counter;
import org.eclipse.jnosql.databases.redis.communication.SortedSet;
import org.eclipse.jnosql.mapping.keyvalue.KeyValueDatabase;

@ApplicationScoped
public class RedisStructure {

    @Inject
    @KeyValueDatabase("home")
    private Counter counter;

    @Inject
    @KeyValueDatabase("game")
    private SortedSet sortedSet;


    public void increment() {
        counter.increment();
    }

    public void increment(String name, long value) {
        sortedSet.increment(name, value);
    }
}
