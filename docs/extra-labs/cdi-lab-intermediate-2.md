# Jakarta CDI - Intermediate Lab 2

In this lab, we will create an object taking information from who needs this information using the **InjectionPoint**. Thus, we will create a Logger producer.

## 1. Create the `LoggerProducer` class

### :material-play-box-multiple-outline: Steps

1. Create a class called `LoggerProducer` in the `expert.os.labs.persistence.cid.producer` package
2. Create a constant to log results using the `Logger` from the `java.util.logging` package
3. Annotate it with the `@ApplicationScoped` from the `jakarta.enterprise.context` package
4. Add a producer method, annotating it with `@Produces` from the `jakarta.enterprise.inject` where:
    - it has a parameter using the `InjectionPoint` class from `jakarta.enterprise.inject.spi` package
    - get the class name using the Injection point, associating it with a variable
    - log the class name using the logger

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.enterprise.context.ApplicationScoped;
    import jakarta.enterprise.inject.Produces;
    import jakarta.enterprise.inject.spi.InjectionPoint;

    import java.util.logging.Logger;

    @ApplicationScoped
    public class LoggerProducer {

        private static final Logger LOGGER = Logger.getLogger(LoggerProducer.class.getName());

        @Produces
        Logger getLog(InjectionPoint injectionPoint) {
            String declaringClass = injectionPoint.getMember().getDeclaringClass().getName();
            LOGGER.info("Creating instance log to " + declaringClass);

            return Logger.getLogger(declaringClass);
        }
    }
    ```

    !!! note

        The `getLog()` method is annotated with `@Produces`, indicating that it is a CDI producer method. This method produces `java.util.logging.Logger` instances. It takes an `InjectionPoint` as a parameter, which provides information about the injection point. It retrieves the declaring class name from the injection point and creates a `Logger` instance for that class name. It also logs a message indicating the creation of the logger.

## 2. Define a class that uses the produced logger

### :material-play-box-multiple-outline: Steps

1. Create a class called `NumberLogger` in the `expert.os.labs.persistence.cid` package
2. Define a private attribute `Logger` from the `java.util.logging` package
3. Add a default constructor
4. Add a constructor that receives the `Logger` as a parameter and annotate it using the `@Inject` class from the `jakarta.inject` package
5. Add a log method to log a `BigDecimal` value

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.inject.Inject;

    import java.math.BigDecimal;
    import java.util.logging.Logger;

    public class NumberLogger {

        private Logger logger;

        NumberLogger() {
        }

        @Inject
        public NumberLogger(Logger logger) {
            this.logger = logger;
        }

        public void log(BigDecimal value) {
            this.logger.info("The BigDecimal value is " + value);
        }

    }
    ```

## 3. Create the app class

### :material-play-box-multiple-outline: Steps

1. Create a class called `AppNumberLog` in the `expert.os.labs.persistence.cid` package
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
5. Obtain the value of the `NumberLogger` through the container instance
5. Printout the value of the `NumberLogger`
6. Run the `main()` method

### :material-checkbox-multiple-outline: Expected results

* The following output
   
    ```
    expert.os.labs.persistence.cdi.producer.LoggerProducer getLog
    INFO: Creating instance log to expert.os.labs.persistence.cdi.NumberLogger
    expert.os.labs.persistence.cdi.NumberLogger log
    INFO: The BigDecimal value is 11
    We don't need this number anymore: 11
    ```

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;

    import java.math.BigDecimal;

    public class AppNumberLog {

        public static void main(String[] args) {
            try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
                BigDecimal value = container.select(BigDecimal.class).get();

                NumberLogger logger = container.select(NumberLogger.class).get();
                logger.log(value);
            }
        }
    }
    ```
