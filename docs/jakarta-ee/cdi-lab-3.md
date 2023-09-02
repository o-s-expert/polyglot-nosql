# Jakarta CDI - Lab 3

## 1. CDI decorator



In this lab, we will explore the CDI decorator using Workers as the sample, where we will decorate it with a manager.

material-play-box-multiple-outline: Steps

1. Create a `Worker` interface with a method work returning a `String` and a job as a parameter.
2. Create a `Programmer` class that will implement `Worker`, and the implementation method will concat the String "Convert coffee in code " with the `job` parameter.
3. Create a Manager class, also implementing the Worker interfaces.
4. Include the Decorator and Priority annotations as the code below:

```java
@Decorator
@Priority(Interceptor.Priority.APPLICATION)
```
5. Inject the Worker, including the Any and Delegate annotations, as the code below shows:

```java
@Inject
@Delegate
@Any
private Worker worker;
```
6. Implement the method in the Manager class with the result of the worker method.
7. Test your code creating the App5 with the code below:

```java

import expert.os.labs.persistence.nosql.cdi.decorator.Worker;
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

1. Create a `Timed` annotation, including the `InterceptorBinding`, the `Target`, and `Retentionv as the code below:

```java
import jakarta. Interceptor.InterceptorBinding;

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

2. Create a `TimedInterceptor` class annotated with `Timed`, `Interceptor`, and `Priority` annotation.
3. Create a method called timer receiving `InvocationContext` as a parameter and with AroundInvoke annotation at the method.

```java
@AroundInvoke
public Object timer(InvocationContext ctx) throws Exception {
}
```

4. Count the execution method time and log this information. To execute the intercepted method, complete the following code: `Object result = ctx.proceed();`
5. Return the value instance at the end.
6. Create two implementations of `Supplier<String>` called `FastSupplier` and `SlowSupplier`. Where the SlowSupplier will return the value after waiting 200 milliseconds. 

7. To test and explore the proposal, create an `App6` with the code below:

```java

import expert.os.labs.persistence.nosql.cdi.auditaded.FastSupplier;
import expert.os.labs.persistence.nosql.cdi.auditaded.SlowSupplier;
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