package expert.os.books.persistence.nosql.chapter14;

import com.github.javafaker.Faker;
import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

@Entity
public record Book(@Id String isbn, @Column String title, @Column int edition, @Column int year) {

    public static Book of(Faker faker) {
        return new Book(faker.code().isbn13(), faker.book().title(), faker.number().numberBetween(1, 10),
                faker.number().numberBetween(1900, 2022));
    }
}
