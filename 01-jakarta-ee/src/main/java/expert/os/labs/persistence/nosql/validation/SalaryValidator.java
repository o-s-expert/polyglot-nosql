package expert.os.labs.persistence.nosql.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.joda.money.Money;

public class SalaryValidator implements ConstraintValidator<SalaryConstraint, Money> {

    @Override
    public boolean isValid(Money value, ConstraintValidatorContext context) {
        return value != null && !value.isNegativeOrZero();
    }
}
