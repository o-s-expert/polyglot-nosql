# Custom Bean Validation

The goal is to illustrate how to create a custom validation in Java using Jakarta Validation. We're developing a custom validation called `SalaryValidator` and an associated annotation named `SalaryConstraint`. This custom validation ensures that the `Money` field representing an employee's salary in the `Worker` class is non-null and greater than zero. This approach enhances data validation in Java applications, ensuring that salary values meet specific criteria and providing customized error messages for validation failures.

**Step 1: Create the `SalaryValidator` Class**

1. **Create the `SalaryValidator` Class**:

   Create a Java class named `SalaryValidator` that implements the `ConstraintValidator` interface for the custom validation.

   ```java
   public class SalaryValidator implements ConstraintValidator<SalaryConstraint, Money> {
   ```

2. **Implement the `isValid` Method**:

   Implement the `isValid` method, which contains the actual validation logic for the `Money` field. In this case, we are checking that the `Money` value is not null and is greater than zero.

   ```java
       @Override
       public boolean isValid(Money value, ConstraintValidatorContext context) {
           return value != null && !value.isNegativeOrZero();
       }
   ```

   Here, we return `true` if the `Money` value is not null and not negative or zero, indicating a valid salary.

**Step 2: Create the `SalaryConstraint` Annotation**

1. **Import Necessary Libraries**:

   Import the required libraries for creating custom annotations.

   ```java
   import jakarta.validation.Constraint;
   import jakarta.validation.Payload;
   import java.lang.annotation.Documented;
   import java.lang.annotation.ElementType;
   import java.lang.annotation.Retention;
   import java.lang.annotation.RetentionPolicy;
   import java.lang.annotation.Target;
   ```

2. **Create the `SalaryConstraint` Annotation**:

   Create a Java annotation named `SalaryConstraint` with the necessary meta-information. This annotation is used to annotate fields where the custom validation should be applied.

   ```java
   @Documented
   @Constraint(validatedBy = SalaryValidator.class)
   @Target({ ElementType.METHOD, ElementType.FIELD })
   @Retention(RetentionPolicy.RUNTIME)
   public @interface SalaryConstraint {
   ```

3. **Define Annotation Attributes**:

   Define the attributes for the annotation. In this case, we have a default error message attribute.

   ```java
       String message() default "Invalid salary";
       Class<?>[] groups() default {};
       Class<? extends Payload>[] payload() default {};
   }
   ```

   The `message` attribute specifies the default error message if the validation fails.

With these steps completed, you've created the `SalaryValidator` class, which contains the custom validation logic for salaries, and the `SalaryConstraint` annotation, which can be used to annotate fields in the `Worker` class to apply this custom validation.

??? example "Click to see..."

   
   ```java
   import jakarta.validation.ConstraintValidator;
   import jakarta.validation.ConstraintValidatorContext;
   import org.joda.money.Money;

   public class SalaryValidator implements ConstraintValidator<SalaryConstraint, Money> {

       @Override
       public boolean isValid(Money value, ConstraintValidatorContext context) {
           return value != null && !value.isNegativeOrZero();
       }
   }

   import jakarta.validation.Constraint;
   import jakarta.validation.Payload;

   import java.lang.annotation.Documented;
   import java.lang.annotation.ElementType;
   import java.lang.annotation.Retention;
   import java.lang.annotation.RetentionPolicy;
   import java.lang.annotation.Target;

   @Documented
   @Constraint(validatedBy = SalaryValidator.class)
   @Target( { ElementType.METHOD, ElementType.FIELD })
   @Retention(RetentionPolicy.RUNTIME)
   public @interface SalaryConstraint {

       String message() default "Invalid salary";
       Class<?>[] groups() default {};
       Class<? extends Payload>[] payload() default {};
   }
   ```