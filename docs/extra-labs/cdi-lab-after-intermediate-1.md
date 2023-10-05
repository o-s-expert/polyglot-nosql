# Jakarta CDI - After Intermediate Lab 1

In this lab, we will explore the CDI decorator using Workers as the sample, and we will decorate it with a manager.

## 1. Create the `LoggerProducer` class

### :material-play-box-multiple-outline: Steps

1. Create a new package called `decorator` in the `expert.os.labs.persistence.cid` package
2. Create an interface called `Worker`
3. Create the `worker` method which will set a `job`

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    public interface Worker {

        String work(String job);
    }
    ```

## 2. Create the `Programmer` Class

1. Create a class called `Programmer` in the `expert.os.labs.persistence.cid.decorator` package
2. Implements `Worker` and the method `work()`
    - the `Consumer` class is from the `java.util.function` package
3. Define a private attribute `Logger` from the `java.util.logging` package
4. In the method `work()`, return a `String` based on its job

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.enterprise.context.ApplicationScoped;

    import java.util.logging.Logger;

    @ApplicationScoped
    public class Programmer implements Worker {

        private static final Logger LOGGER = Logger.getLogger(Programmer.class.getName());

        @Override
        public String work(String job) {
            return "A programmer has received a job, it will convert coffee in code: " + job;
        }
    }
    ```

## 3. Create the `Manager` Class (Decorator)

### :material-play-box-multiple-outline: Steps

1. Create a class called `Manager` in the `expert.os.labs.persistence.cid.decorator` package
2. Implements the `Worker` interface
3. Annotate the class with `@Decorator` from the `jakarta.decorator` package
4. Add a priority to the decorator using the `@Priority` from the `jakarta.annotation` package
    - the priority must be set as `Interceptor.Priority.APPLICATION`
5. Add a private field `Worker` annotating it with:
    - `@Inject` from the `jakarta.inject` package
    - `@Delegate` from the `jakarta.decorator` package
    - `@Any` from the `jakarta.enterprise.inject` package

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.annotation.Priority;
    import jakarta.decorator.Decorator;
    import jakarta.decorator.Delegate;
    import jakarta.enterprise.inject.Any;
    import jakarta.inject.Inject;
    import jakarta.interceptor.Interceptor;

    @Decorator
    @Priority(Interceptor.Priority.APPLICATION)
    public class Manager implements Worker {

        @Inject
        @Delegate
        @Any
        private Worker worker;

        @Override
        public String work(String job) {
            return "A manager has received a job and it will delegate to a programmer -> " + worker.work(job);
        }
    }
    ```

## 6. Create the app class

### :material-play-box-multiple-outline: Steps

1. Create a class called `AppWorker` in the `expert.os.labs.persistence.cid` package
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

4. Obtain the value of the `Worker` through the container instance
5. Define a work from the `worker` instance
6. Printout the work

### :material-checkbox-multiple-outline: Expected results

* The following output
   
    ```
    <the output you defined>
    ```

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import expert.os.labs.persistence.cdi.decorator.Worker;
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;

    public class AppWorker {

        public static void main(String[] args) {
            try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
                Worker worker = container.select(Worker.class).get();

                String work = worker.work("Just a single button");
                System.out.println("The work result: " + work);
            }
        }
    }
    ```
