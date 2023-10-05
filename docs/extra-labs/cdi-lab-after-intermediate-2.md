# Jakarta CDI - After Intermediate Lab 2

In this lab, we will explore the CDI interceptor, creating a timer for methods.

## 1. Create a `Timed` annotation

1. Create a new package called `audited` in the `expert.os.labs.persistence.cid` package
2. Create an interface called `Timed` in the `expert.os.labs.persistence.cid.audited` package
   - Annotate the interface as `@interface`
3. Add the following annotations:
    - `@InterceptorBinding` from the `jakarta.interceptor` package
    - `@Target({METHOD, TYPE})`
        - the `@Target` is from the `java.lang.annotation` package
        - the `METHOD` and `TYPE` is from the `java.lang.annotation.ElementType` package
    - `@Retention(RUNTIME)`
        - the `@Retention` is from the `java.lang.annotation` package
        - the `RUNTIME` is from the `java.lang.annotation.ElementType` package

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.interceptor.InterceptorBinding;

    import java.lang.annotation.Retention;
    import java.lang.annotation.Target;

    import static java.lang.annotation.ElementType.METHOD;
    import static java.lang.annotation.ElementType.TYPE;
    import static java.lang.annotation.RetentionPolicy.RUNTIME;

    @InterceptorBinding
    @Target({METHOD, TYPE})
    @Retention(RUNTIME)
    public @interface Timed {
    }
    ```

## 2. Create the `TimedInterceptor` Class

### :material-play-box-multiple-outline: Steps

1. Create a class called `TimedInterceptor` in the `expert.os.labs.persistence.cid.audited` package
2. Add the following annotations:
    - `@Timed`
    - `@Interceptor` from the `jakarta.interceptor` package
    - `@Priority(Interceptor.Priority.APPLICATION)` from the `jakarta.annotation` package
        - the `Interceptor` is from the `jakarta.interceptor` package
3. Define a private attribute `Logger` from the `java.util.logging` package
4. Create a method called `timer` that will return an `Object`
    - it has a parameter `InvocationContext` from the `jakarta.interceptor` package
5. Annotate the method with `@AroundInvoke` from the `jakarta.interceptor` package
6. In the `timer()` method, add:
    - the current time into a variable using the `System.currentTimeMillis()`
    - the next interceptor using `ctx.proceed();`, associating it to an object
    - the time slapsed into a variale (`System.currentTimeMillis() - start`)
    - the information about the time slapsed into a `String` showing it using `ctx.getTarget().getClass(), ctx.getMethod(), end`
    - Log the content of the information using the logger
    - return the information

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.annotation.Priority;
    import jakarta.interceptor.AroundInvoke;
    import jakarta.interceptor.Interceptor;
    import jakarta.interceptor.InvocationContext;

    import java.util.logging.Logger;

    @Timed
    @Interceptor
    @Priority(Interceptor.Priority.APPLICATION)
    public class TimedInterceptor {

        private static final Logger LOGGER = Logger.getLogger(TimedInterceptor.class.getName());

        @AroundInvoke
        public Object timer(InvocationContext ctx) throws Exception {
            long start = System.currentTimeMillis();
            Object result = ctx.proceed();
            long end = System.currentTimeMillis() - start;

            String message = String.format("Time to execute the class %s, the method %s is %d milliseconds",
                ctx.getTarget().getClass(), ctx.getMethod(), end);
            LOGGER.info(message);

            return result;
        }
    }
    ```

## 3. Create the `FastSupplier` Class

### :material-play-box-multiple-outline: Steps

1. Create a class `FastSupplier` in the in the `expert.os.labs.persistence.cid.audited` package
2. Implement the `Supplier<String>`
3. Annotate the method `get()` with `@Timed`
4. Return any `String`

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import java.util.function.Supplier;

    public class FastSupplier implements Supplier<String> {

        @Timed
        @Override
        public String get() {
            return "The Fast supplier result";
        }
    }
    ```

## 4. Create the `SlowSupplier` Class

### :material-play-box-multiple-outline: Steps

1. Create a class `SlowSupplier` in the in the `expert.os.labs.persistence.cid.audited` package
2. Implement the `Supplier<String>`
3. Annotate the method `get()` with `@Timed`
4. Add the following code to the method

    ```java
    try {
        TimeUnit.MILLISECONDS.sleep(200L);
    } catch (InterruptedException e) {
        //TODO it is only a sample, don't do it on production :)
        throw  new RuntimeException(e);
    }
    return "The slow result";
    ```

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import java.util.concurrent.TimeUnit;
    import java.util.function.Supplier;

    public class SlowSupplier implements Supplier<String> {

        @Timed
        @Override
        public String get() {
            try {
                TimeUnit.MILLISECONDS.sleep(200L);
            } catch (InterruptedException e) {
                //TODO it is only a sample, don't do it on production :)
                throw  new RuntimeException(e);
            }
            return "The slow result";
        }
    }
    ```

## 5. Create the app class

### :material-play-box-multiple-outline: Steps

1. Create a class called `AppInterceptor` in the `expert.os.labs.persistence.cid` package
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

4. Obtain the value of the `FastSupplier` through the container instance
5. Obtain the value of the `SlowSupplier` through the container instance
6. Printout both results

### :material-checkbox-multiple-outline: Expected results

* The following output
   
    ```
    INFO: Time to execute the class class expert.os.labs.persistence.cdi.audited.FastSupplier$Proxy$_$$_WeldSubclass, the method public java.lang.String expert.os.labs.persistence.cdi.audited.FastSupplier.get() is 0 milliseconds
    The result: The Fast supplier result
    
    INFO: Time to execute the class class expert.os.labs.persistence.cdi.audited.SlowSupplier$Proxy$_$$_WeldSubclass, the method public java.lang.
    
    String expert.os.labs.persistence.cdi.audited.SlowSupplier.get() is 203 milliseconds
    The result: The slow result    
    ```

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import expert.os.labs.persistence.cdi.audited.FastSupplier;
    import expert.os.labs.persistence.cdi.audited.SlowSupplier;
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;

    import java.util.function.Supplier;

    public class AppInterceptor {

        public static void main(String[] args) {
            try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
                Supplier<String> fastSupplier = container.select(FastSupplier.class).get();
                Supplier<String> slowSupplier = container.select(SlowSupplier.class).get();

                System.out.println("The result: " + fastSupplier.get());
                System.out.println("The result: " + slowSupplier.get());
            }
        }
    }
    ```
