# Jakarta Validation - Lab 2

The goal is to illustrate how to create a custom validation in Java using Jakarta Validation.
We're developing a custom validation called `SalaryValidator` and an associated annotation named `SalaryConstraint`.

This custom validation ensures that the `Money` field representing an employee's salary in the `Worker` class is non-null and greater than zero.
This approach enhances data validation in Java applications, ensuring that salary values meet specific criteria and providing customized error messages for validation failures.

## 1. Create the custom constraint class

### :material-play-box-multiple-outline: Steps

1. Open the `01-jakarta-ee` project and navigate to the `src/main/java`
2. Create an interface called `SalaryConstraint` in the `expert.os.labs.persistence.validation` package
3. Add the following annotations to the interface where:
    - the `@Constraint` annotation come from the `jakarta.validation` package
    - the other annotations come from the `java.lang.annotation` package

    ```java
    @Documented
    @Constraint(validatedBy = SalaryValidator.class)
    @Target( { ElementType.METHOD, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    ```

    !!! warning

        The `SalaryValidator.class` doesn't exist yet. We will create it in the next steps.
        For now, let your IDE complain about this missing class.

4. Define the annotation attributes:
    - `message`, as the default message
    - `groups` as the class group
    - `payload` as the `Payload` from the `jakarta.validation` package

        ```java
        String message() default "Invalid salary";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
        ```

### :material-checkbox-multiple-outline: Expected results

* A new class `SalaryConstraint` that will be used for the custom validation

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.validation.Constraint;

    import java.lang.annotation.Documented;
    import java.lang.annotation.ElementType;
    import java.lang.annotation.Retention;
    import java.lang.annotation.RetentionPolicy;
    import java.lang.annotation.Target;

    @Documented
    @Constraint(validatedBy = SalaryValidator.class)
    @Target({ElementType.METHOD, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SalaryConstraint {

    }
    ```

## 2. Create the custom validator class

### :material-play-box-multiple-outline: Steps

1. Create a class `SalaryValidator` in the `expert.os.labs.persistence.validation` package
2. Make the class implements the `ConstraintValidator` interface, typing `SalaryConstraint` and the `Money` classes
    - the `ConstraintValidator` class comes from the `jakarta.validation` package
    - the `Money` class comes from the `org.joda.money` package

    ```java
    public class SalaryValidator implements ConstraintValidator<SalaryConstraint, Money> {
    }
    ```

3. Implements the `isValid` method (because, probably, your IDE will complain about it!)

    !!! tip "Tip"
        Name the `Money` parameter as `salary` for clarity purposes

    ```java
    @Override
    public boolean isValid(Money salary, ConstraintValidatorContext constraintValidatorContext) {
       return false;
    }
    ```

4. Add the validation logic in the `isValid` method containing:
    - check if the `salary` is not null
    - check if the `salary` is not negative or zero

    !!! tip "Tips"
        * both checks can be connected to an `&&` operator
        * you can use the method `isNegativeOrZero()` from the `value` field

### :material-checkbox-multiple-outline: Expected results

* A new class `SalaryValidator` that will be used to validate if the salary is not null and greater than zero

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.validation.ConstraintValidator;
    import jakarta.validation.ConstraintValidatorContext;
    import org.joda.money.Money;

    public class SalaryValidator implements ConstraintValidator<SalaryConstraint, Money> {
       @Override
       public boolean isValid(Money salary, ConstraintValidatorContext constraintValidatorContext) {
          return salary != null && salary.isNegativeOrZero();
       }
    }
    ```

## 3. Add the salary field to the `Worker` class

### :material-play-box-multiple-outline: Steps

1. Open the `Worker` class
2. Add a new field called `salary` of type `Money` from the `org.joda.money` package
3. Add the `@SalaryValidator` annotation to its field and associate the `message` attribute with the String `Salary should be greater than zero`
4. Add the field `salary` into the constructor
5. Add the getter and setter for the `salary` field
6. Add the field `salary` into the `toString()` method

### :material-checkbox-multiple-outline: Expected results

* The class `Worker` with a new field `salary` with a custom constraint

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    public class Worker {
       // previous fields ignored

       @SalaryConstraint(message = "Salary should be greater than zero")
       private Money salary;

       public Worker(String nickname, String name, boolean working, String bio, int age, String email, Money salary) {
          // previous attributes ignored
          this.salary = salary;
       }

       public void setSalary(Money salary) {
          this.salary = salary;
       }

       public void getSalary() {
          return salary;
       }
    }
    ```

## 4. Add the salary field to the `WorkerBuilder` class

### :material-play-box-multiple-outline: Steps

1. Open the `WorkerBuilder` class
2. Add a new field called `salary` of type `Money` from the `org.joda.money` package
3. Add a new method for the `salary` field
4. Include the `salary` field into the `build()` method

### :material-checkbox-multiple-outline: Expected results

* The class `WorkerBuilder` with a new field `salary`

### :material-check-outline: Solution

??? example "Click to see..."

    ``` java
    public class WorkerBuilder {
       // previous fields ignored

       private Money salary;

       // previous methods ignored

       public WorkerBuilder salary(Money salary) {
          this.salary = salary;
          return this;
       }

       public Worker build() {
          return new Worker(nickname, name, working, bio, age, email, salary);
       }
    }
    ```

## 5. Create a test for the salary constraint

### :material-play-box-multiple-outline: Steps

1. Open the `WorkerTest` in the `src/test/java` at the `expert.os.labs.persistence.validation` package
2. Add a new test called `shouldNotCreateWorkerWithNegativeSalary()`
3. Implement the test setting a negative value to the `salary()` method from the builder
    - the negative value can be set using `Money.of(CurrencyUnit.of("USD"), -10)`
    - the assertion must validate if the message `Salary should be greater than zero` is present

### :material-checkbox-multiple-outline: Expected results

* A new test to validate the implementation of the custom validation for the `salary` field

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    @Test
    void shouldNotCreateWorkerWithNegativeSalary() {
       Worker worker = Worker.builder()
       .nickname("jonhdoe")
       .name("John Doe")
       .working(true)
       .bio("I am a software engineer")
       .age(30)
       .email("john.doe@gmail.com")
       .salary(Money.of(CurrencyUnit.of("USD"), -10)).build();

       Set<ConstraintViolation<Worker>> violations = validator.validate(worker);

       Assertions.assertThat(violations).isNotEmpty().hasSize(1)
          .map(ConstraintViolation::getMessage).contains("Salary should be greater than zero");
    }
    ```
