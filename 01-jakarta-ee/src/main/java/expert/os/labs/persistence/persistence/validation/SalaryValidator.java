package expert.os.labs.persistence.persistence.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.joda.money.Money;

public class SalaryValidator implements ConstraintValidator<SalaryConstraint, Money> {

    @Override
    public boolean isValid(Money salary, ConstraintValidatorContext context) {
        return salary != null && !salary.isNegativeOrZero();
    }
}
