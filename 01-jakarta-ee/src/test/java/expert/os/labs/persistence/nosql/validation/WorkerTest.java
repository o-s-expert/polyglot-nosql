package expert.os.labs.persistence.nosql.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class WorkerTest {

    private Validator validator;
    private final CurrencyUnit usd = CurrencyUnit.of("USD");

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldNotCreateWorkerWithBlankNickname() {
        Worker worker = Worker.builder()
                .nickname("")
                .name("John Doe")
                .working(true)
                .bio("I am a software engineer")
                .age(30)
                .email("email@os.expert")
                .salary(Money.of(usd, 10)).build();
        Set<ConstraintViolation<Worker>> violations = validator.validate(worker);

        assertThat(violations)
                .isNotEmpty()
                .hasSize(1)
                .map(ConstraintViolation::getMessage).contains("Nickname cannot be blank");
    }

    @Test
    public void shouldNotCreateWorkerWithBlankName() {
        Worker worker = Worker.builder()
                .nickname("jonhdoe")
                .name("John Doe")
                .working(true)
                .bio("I am a software engineer")
                .age(30)
                .email("email@os.expert")
                .salary(Money.of(usd, -10)).build();
        Set<ConstraintViolation<Worker>> violations = validator.validate(worker);

        assertThat(violations)
                .isNotEmpty()
                .hasSize(1)
                .map(ConstraintViolation::getMessage).contains("Salary should be greater than zero");
    }

    @Test
    public void shouldCreateWorker(){
        Worker worker = Worker.builder()
                .nickname("jonhdoe")
                .name("John Doe")
                .working(true)
                .bio("I am a software engineer")
                .age(30)
                .email("email@os.expert")
                .salary(Money.of(usd, 10_000)).build();

        var violations = validator.validate(worker);
        assertThat(violations).isEmpty();

    }

}