package expert.os.labs.persistence.persistence;

import jakarta.data.repository.PageableRepository;
import jakarta.data.repository.Repository;

import java.util.List;

@Repository
public interface Library extends PageableRepository<Book, String> {

    List<Book> findByTitle(String title);
}
