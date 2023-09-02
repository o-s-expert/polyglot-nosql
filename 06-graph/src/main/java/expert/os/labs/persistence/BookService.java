package expert.os.labs.persistence;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.jnosql.mapping.graph.GraphTemplate;

import java.util.List;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class BookService {

    @Inject
    private GraphTemplate graph;

    Category category(String name) {
        return graph.traversalVertex().hasLabel(Category.class)
                .has("name", name)
                .<Category>next()
                .orElseGet(() -> graph.insert(Category.of(name)));
    }

    Book book(String name) {
        return graph.traversalVertex().hasLabel(Book.class)
                .has("name", name)
                .<Book>next()
                .orElseGet(() -> graph.insert(Book.of(name)));
    }

    public void category(Book book, Category category){
        this.graph.edge(book, LibraryLabels.IS, category);
    }

    public void category(Category subCategory, Category category){
        this.graph.edge(subCategory, LibraryLabels.IS, category);
    }

    public List<String> softwareCategories(){
        return graph.traversalVertex().hasLabel(Category.class)
                .has("name", "Software")
                .in(LibraryLabels.IS).hasLabel("Category").<Category>result()
                .map(Category::getName)
                .collect(toList());
    }

    public List<String> softwareBooks(){
        return graph.traversalVertex().hasLabel(Category.class)
                .has("name", "Software")
                .in(LibraryLabels.IS).hasLabel(Book.class).<Book>result()
                .map(Book::getName)
                .collect(toList());
    }

    public List<String> softwareNoSQLBooks(){
        return graph.traversalVertex().hasLabel(Category.class)
                .has("name", "Software")
                .in(LibraryLabels.IS)
                .has("name", "NoSQL")
                .in(LibraryLabels.IS).<Book>result()
                .map(Book::getName)
                .collect(toList());
    }
}
