# Jakarta Validation - Lab 1

To create the `Worker` class with Jakarta Validation annotations step by step, follow these instructions:

1. **Create a Java Class**: Start by creating a new Java class. You can name it `Worker.java` or any other name you prefer.

2. **Import Jakarta Validation and Other Necessary Libraries**:
   
   ```java
   import jakarta.validation.constraints.AssertTrue;
   import jakarta.validation.constraints.Email;
   import jakarta.validation.constraints.Max;
   import jakarta.validation.constraints.Min;
   import jakarta.validation.constraints.NotBlank;
   import jakarta.validation.constraints.Size;
   import org.joda.money.Money;
   ```

   These import statements include the Jakarta Validation annotations as well as the `Money` class from the Joda Money library, although it's not used in your example.

3. **Create the `Worker` Class**:
   
   ```java
   public class Worker {
   ```

4. **Add Fields with Validation Annotations**:

   Add the fields you want to validate and annotate them with Jakarta Validation annotations as specified in your example.

   - `nickname` field with `@NotBlank` annotation:

     ```java
     @NotBlank(message = "Nickname cannot be blank")
     private String nickname;
     ```

   - `name` field with `@NotBlank` annotation:

     ```java
     @NotBlank(message = "Name cannot be blank")
     private String name;
     ```

   - `working` field with `@AssertTrue` annotation:

     ```java
     @AssertTrue
     private boolean working;
     ```

   - `bio` field with `@Size` annotation:

     ```java
     @Size(min = 10, max = 200, message = "Bio must be between 10 and 200 characters")
     private String bio;
     ```

   - `age` field with `@Min` and `@Max` annotations:

     ```java
     @Min(value = 18, message = "A worker should not be less than 18")
     @Max(value = 80, message = "A worker should not be greater than 80")
     private int age;
     ```

   - `email` field with `@Email` annotation:

     ```java
     @Email(message = "Email should be valid")
     private String email;
     ```

5. **Close the Class**:

   Close the class by adding a closing brace (`}`) at the end.

#### :material-check-outline: Solution

??? example "Click to see..."

   ```java
   import jakarta.validation.constraints.AssertTrue;
   import jakarta.validation.constraints.Email;
   import jakarta.validation.constraints.Max;
   import jakarta.validation.constraints.Min;
   import jakarta.validation.constraints.NotBlank;
   import jakarta.validation.constraints.Size;
   import org.joda.money.Money;

   public class Worker {
       @NotBlank(message = "Nickname cannot be blank")
       private String nickname;

       @NotBlank(message = "Name cannot be blank")
       private String name;

       @AssertTrue
       private boolean working;

       @Size(min = 10, max = 200, message = "Bio must be between 10 and 200 characters")
       private String bio;

       @Min(value = 18, message = "A worker should not be less than 18")
       @Max(value = 80, message = "A worker should not be greater than 80")
       private int age;

       @Email(message = "Email should be valid")
       private String email;
   }
   ```