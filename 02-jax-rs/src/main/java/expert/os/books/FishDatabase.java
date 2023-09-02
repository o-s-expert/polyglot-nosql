package expert.os.books;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class FishDatabase {

    private Map<String, Fish> fishes;

    public FishDatabase() {
        this.fishes = new HashMap<>();
    }

    public void save(Fish fish) {
        this.fishes.put(fish.id(), fish);
    }

    public void delete(String id) {
        this.fishes.remove(id);
    }

    public Optional<Fish> findById(String id) {
        return Optional.ofNullable(this.fishes.get(id));
    }

    public void clear() {
        this.fishes.clear();
    }
    public List<Fish> findAll() {
        return List.copyOf(this.fishes.values());
    }
}
