# Jakarta CDI - Intermediate Lab 1

In this lab, we will create an object and destroy objects using the Produces and Dispose annotations, respectively.

## 1. Create the `NumberProducer` class

### :material-play-box-multiple-outline: Steps

1. Open the `01-jakarta-ee` project and navigate to the `src/main/java`
2. Create a package named `producer` in the `expert.os.labs.persistence.cid` package
3. Create a class called `NumberProducer` in the `expert.os.labs.persistence.cid.producer` package
4. Annotate it with the `@ApplicationScoped` from the `jakarta.enterprise.context` package
5. Add a producer method, annotating it with `@Produces` from the `jakarta.enterprise.inject` package and generate a random `BigDecimal` value within the range [1, 100] using the `ThreadLocalRandom` and return it
6. Add a disposer method named `destroy()` to remove the value from the class where it has a `value` parameter annotated with `@Disposes` from the `jakarta.enterprise.inject` package

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.enterprise.context.ApplicationScoped;
    import jakarta.enterprise.inject.Disposes;
    import jakarta.enterprise.inject.Produces;

    import java.math.BigDecimal;
    import java.util.concurrent.ThreadLocalRandom;

    @ApplicationScoped
    public class NumberProducer {

        @Produces
        public BigDecimal producer() {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            double nextDouble = random.nextInt(1, 100);
            return new BigDecimal(nextDouble);
        }

        public void destroy(@Disposes BigDecimal value) {
            System.out.println("We don't need this number anymore: " + value);
        }
    }
    ```

## 2. Create the app class

### :material-play-box-multiple-outline: Steps

1. Create a class called `AppNumber` in the `expert.os.labs.persistence.cid` package
2. Add a main method

    ```java
    public static void main(String[] args) {
    }
    ```

3. Set up a try-with-resources block, inside the `main` method, to manage the Jakarta EE `SeContainer` that is responsible for dependency injection and managing resources

    ```java
    try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {     
    }
    ```

4. Obtain the value of the `BigDecimal` through the container instance
5. Printout the value
6. Run the `main()` method

### :material-checkbox-multiple-outline: Expected results

* The following output
   
    ```
    Value = <number>
    We don't need this number anymore: <number>
    ```

    !!! note

        The `destroy()` method is used as a disposer method for `BigDecimal` instances. It takes a parameter of type `BigDecimal` annotated with `@Disposes`. When an instance of `BigDecimal` is no longer needed, the CDI container will automatically call this method to perform cleanup or logging. In this case, it prints a message indicating that the number is no longer needed.

        In summary, this code defines an application-scoped CDI bean (`NumberProducer`) that produces random `BigDecimal` values using a producer method. It also includes a disposer method to handle the cleanup of `BigDecimal` instances when they are no longer needed within the CDI context.

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;

    import java.math.BigDecimal;

    public class AppNumber {

        public static void main(String[] args) {
            try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
                BigDecimal value = container.select(BigDecimal.class).get();
                System.out.println("Value = " + value);
            }
        }
    }
    ```
