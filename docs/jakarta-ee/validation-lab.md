# Jakarta Validation - Lab 1

In this lab we will create a model class with the Jakarta Validation annotations.

## 1. Create the validation class

### :material-play-box-multiple-outline: Steps

1. Open the `01-jakarta-ee` project and navigate to the `src/main/java`
2. Create a class called `Worker` in the `expert.os.labs.persistence.validation` package
3. Add the following fields, adding the related annotations from the `jakarta.validation.constraints` package
    - `nickname` field with `@NotBlank` annotation

        ```java
        @NotBlank(message = "Nickname cannot be blank")
        private String nickname;
        ```

      - `name` field with `@NotBlank` annotation

        ```java
        @NotBlank(message = "Name cannot be blank")
        private String name;
        ```

      - `working` field with `@AssertTrue` annotation

        ```java
        @AssertTrue
        private boolean working;
        ```

      - `bio` field with `@Size` annotation:

        ```java
        @Size(min = 10, max = 200, message = "Bio must be between 10 and 200 characters")
        private String bio;
        ```

      - `age` field with `@Min` and `@Max` annotations

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

### :material-checkbox-multiple-outline: Expected results

* Class called `Worker` created in the `src/main/java` at the `expert.os.labs.persistence.validation` with all the Jakarta Validation annotations

### :material-check-outline: Solution

??? example "Click to see..."

      ```java
      import jakarta.validation.constraints.AssertTrue;
      import jakarta.validation.constraints.Email;
      import jakarta.validation.constraints.Max;
      import jakarta.validation.constraints.Min;
      import jakarta.validation.constraints.NotBlank;
      import jakarta.validation.constraints.Size;

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
## 2. Add the constructor, getters, setters, and toString

### :material-play-box-multiple-outline: Steps

!!! tip "Tips"
    You can use the shortcuts in your preffered IDE to generate the constructor, getters, setterns and `toString()`

    * __IntelliJ IDEA__: press `Command + N` and click in the related item
    * __Visual Studio Code__: right-click in the code, click in the `Source Action` and then in the related item

1. Add a constructor with all the fields present in the class
2. Add the getters and setters for all the fields present in the class
3. Add the `toString()` method with all the fields present in the class
    * don't forget to add the `@Override`

### :material-checkbox-multiple-outline: Expected results

* The `Worker` class with the:
    * constructor
    * getters and setters
    * `toString()`

### :material-check-outline: Solution

??? example "Click to see..."

      ```java
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

             public Worker(String nickname, String name, boolean working, String bio, int age, String email) {
                this.nickname = nickname;
                this.name = name;
                this.working = working;
                this.bio = bio;
                this.age = age;
                this.email = email;
             }

             public String getNickname() {
                return nickname;
             }

             public void setNickname(String nickname) {
                this.nickname = nickname;
             }

             public String getName() {
                return name;
             }

             public void setName(String name) {
                this.name = name;
             }

             public boolean isWorking() {
                return working;
             }

             public void setWorking(boolean working) {
                this.working = working;
             }

             public String getBio() {
                return bio;
             }

             public void setBio(String bio) {
                this.bio = bio;
             }

             public int getAge() {
                return age;
             }

             public void setAge(int age) {
                this.age = age;
             }

             public String getEmail() {
                return email;
             }

             public void setEmail(String email) {
                this.email = email;
             }

             @Override
             public String toString() {
                return "Worker{" +
                "nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", working=" + working +
                ", bio='" + bio + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
             }
          }
      ```

## 3. Create a builder for the `Worker` class

### :material-play-box-multiple-outline: Steps

1. Create a class called `WorkerBuilder` in the `src/main/java` folder at the `expert.os.labs.persistence.validation` package
2. Add the same fields we had previously added to the `Worker` class, without the annotations
    ```java
    private String nickname;
    private String name;
    private boolean working;
    private String bio;
    private int age;
    private String email;
    ```
3. Add the builder methods for each field
      - example using the `nickname` field

         ```java
         public WorkerBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
         }
         ```

4. Add the `build()` method creating a new instance of `Worker` using its constructor
5. In the `Worker` class add the builder method referring to the `WorkerBuilder`

    ```java
    public static WorkerBuilder builder() {
       return new WorkerBuilder();
    }
    ```

### :material-checkbox-multiple-outline: Expected results

* A new `WorkerBuilder` class implementing the Builder pattern for the `Worker` class
* A new `builder()` method in the `Worker` class

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    public class WorkerBuilder {
       private String nickname;
       private String name;
       private boolean working;
       private String bio;
       private int age;
       private String email;

       public WorkerBuilder nickname(String nickname) {
          this.nickname = nickname;
          return this;
       }

       public WorkerBuilder name(String name) {
          this.name = name;
          return this;
       }

       public WorkerBuilder working(boolean working) {
          this.working = working;
          return this;
       }

       public WorkerBuilder bio(String bio) {
          this.bio = bio;
          return this;
       }

       public WorkerBuilder age(int age) {
          this.age = age;
          return this;
       }

       public WorkerBuilder email(String email) {
          this.email = email;
          return this;
       }

       public Worker build() {
          return new Worker(nickname, name, working, bio, age, email);
       }
    }
    ```

## 3. Test the validations

### :material-play-box-multiple-outline: Steps

1. Create a class called `WorkerTest` in the `src/test/java` folder at the `expert.os.labs.persistence.validation` package
2. Add a field called `validator` using the `Validator` class from the `jakarta.validation.Validator` package

    ```java
    private Validator validator;
    ```

3. Create a `setup` that will create a validator

    ```java
    @BeforeEach
    public void setUp() {
       ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
       validator = factory.getValidator();
    }
    ```

4. Create, at least, one of the following possible tests based on the validations we have added into the `Worker` class

      | field      | test                                 |
      |------------|--------------------------------------|
      | `nickname` | blank nickname                       |
      | `name`     | blank name                           |
      | `working`  | work set as false                    |
      | `bio`      | bio less than 10 or greater than 200 |
      | `age`      | age less than 18 or greater than 80  |
      | `email`    | invalid e-mail                       |

     - use the following code template as a test

        ```java
        @Test
        void shouldNotCreateWorkerWithBlankNickname() {
           Worker worker = Worker.builder().nickname("").name("John Doe").working(true)
               .bio("I am a software engineer").age(38).email("john.doe@gmail.com").build();
           Set<ConstraintViolation<Worker>> violations = validator.validate(worker);

           Assertions.assertThat(violations).isNotEmpty().hasSize(1)
              .map(ConstraintViolation::getMessage).contains("Nickname cannot be blank");
           }
        ```

     - note that the `Assertions` class comes from the `org.assertj.core.api.Assertions.assertThat` and must be statically imported

5. Run the test

### :material-checkbox-multiple-outline: Expected results

* A new `WorkerTest` class created
* At least one test implemented
* Tests running without problems

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    class WorkerTest {

       private Validator validator;

       @BeforeEach
       public void setUp() {
          ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
          validator = factory.getValidator();
       }

       @Test
       void shouldNotCreateWorkerWithBlankNickname1() {
          Worker worker = Worker.builder().nickname("").name("John").working(true).bio("Bio of Elias").age(38).email("elias@elias.com").build();
          Set<ConstraintViolation<Worker>> violations = validator.validate(worker);

             assertThat(violations)
             .isNotEmpty()
             .hasSize(1)
             .map(ConstraintViolation::getMessage).contains("Nickname cannot be blank");
          }
       }
    ```
    