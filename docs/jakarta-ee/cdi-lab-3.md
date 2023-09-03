# Jakarta CDI - Lab 3

## 1. CDI decorator



In this lab, we will explore the CDI decorator using Workers as the sample, where we will decorate it with a manager.

material-play-box-multiple-outline: Steps

**Step 1:** Create the `Worker` Interface
```java
public interface Worker {
    String work(String job);
}
```
- The `Worker` interface defines a method `work(String job)` that takes a job as input and returns a string.

**Step 2:** Create the `Programmer` Class
```java
import jakarta.enterprise.context.ApplicationScoped;
import java.util.logging.Logger;

@ApplicationScoped
public class Programmer implements Worker {

    private static final Logger LOGGER = Logger.getLogger(Programmer.class.getName());

    @Override
    public String work(String job) {
        return "A programmer has received a job, it will convert coffee into code: " + job;
    }
}
```
- The `Programmer` class implements the `Worker` interface.
- It is annotated with `@ApplicationScoped`, indicating that it's a CDI application-scoped bean.
- In the `work(String job)` method, it logs that a programmer has received a job and will convert coffee into code.

**Step 3:** Create the `Manager` Class (Decorator)
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

**Step 4:** Create a class to test and explore:

```java
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class App5 {

    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            Worker worker = container.select(Worker.class).get();
            String work = worker.work("Just a single button");
            System.out.println("The work result: " + work);
        }
    }
}


```  

- The `Manager` class is a decorator for the `Worker` interface.
- It's annotated with `@Decorator` to mark it as a decorator.
- `@Priority(Interceptor.Priority.APPLICATION)` specifies the priority of this decorator.
- It injects a `Worker` instance using `@Delegate` and `@Any`.
- In the `work(String job)` method, it logs that a manager has received a job and will delegate it to a programmer by invoking the `worker.work(job)` method.

In summary, the provided code demonstrates the use of decorators in CDI. The `Programmer` class implements the `Worker` interface, and the `Manager` class acts as a decorator that intercepts calls to the `work` method of the `Worker` interface and adds additional behavior.

### :material-check-outline: Solution

??? example "Click to see..."

```java

public interface Worker {

    String work(String job);
}


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

## 2. CDI Interceptor

In this lab, we will explore the CDI interceptor, creating a timer for methods.

material-play-box-multiple-outline: Steps

**Step 1:** Create the `Timed` Annotation
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
- The `Timed` annotation is marked as an interceptor binding using `@InterceptorBinding`.
- It can be applied to methods (`METHOD`) and types (`TYPE`).
- It is retained at runtime (`RUNTIME`).

**Step 2:** Create the `TimedInterceptor` Class
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
- The `TimedInterceptor` class is an interceptor.
- It is annotated with `@Timed` to specify that it intercepts methods annotated with `@Timed`.
- `@Priority(Interceptor.Priority.APPLICATION)` sets the priority of the interceptor.
- The `@AroundInvoke` annotation specifies that the `timer` method will intercept method invocations.
- Inside the `timer` method, it measures the execution time of the intercepted method using `System.currentTimeMillis()`.
- It logs the execution time and returns the result of the intercepted method.

**Step 3:** Create the `FastSupplier` Class
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
- The `FastSupplier` class implements the `Supplier<String>` interface.
- It is annotated with `@Timed`, indicating that the `get` method should be intercepted by the `TimedInterceptor`.
- The `get` method returns a fast result.

**Step 4:** Create the `SlowSupplier` Class
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
            throw new RuntimeException(e);
        }
        return "The slow result";
    }
}
```

**Step 4:** Create a class to test and explore:

```java
import expert.os.labs.persistence.persistence.cdi.auditaded.FastSupplier;
import expert.os.labs.persistence.persistence.cdi.auditaded.SlowSupplier;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.util.function.Supplier;

public class App6 {

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

- The `SlowSupplier` class also implements the `Supplier<String>` interface.
- It is annotated with `@Timed`, indicating that the `get` method should be intercepted by the `TimedInterceptor`.
- The `get` method simulates a slow operation by sleeping for 200 milliseconds.
- It returns a slow result.

In summary, this code demonstrates the use of CDI interceptors and annotations to measure the execution time of methods. The `TimedInterceptor` intercepts methods annotated with `@Timed` and logs the execution time. The `FastSupplier` and `SlowSupplier` classes are examples of methods that are intercepted by the `TimedInterceptor`.

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
        String message = String.format("Time to execute the class %s, the method %s is of %d milliseconds",
                ctx.getTarget().getClass(), ctx.getMethod(), end);
        LOGGER.info(message);
        return result;
    }
}

import java.util.function.Supplier;

public class FastSupplier implements Supplier<String> {

    @Timed
    @Override
    public String get() {
        return "The Fast supplier result";
    }
}


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