# Jakarta CDI - Lab 2

## 1. Object lifecycle

  

In this lab, we will create an object and destroy objects using the Produces and Dispose annotations, respectively.

### :material-play-box-multiple-outline: Steps

**Step 1:** Import Required Annotations and Classes

```java
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;
```
- This step imports the necessary Jakarta EE annotations and Java classes used in the code.

**Step 2:** Define an Application-Scoped Class
```java
@ApplicationScoped
class NumberProducer {
    // Class definition...
}
```
- The `NumberProducer` class is annotated with `@ApplicationScoped`, indicating that it will be managed as an application-scoped bean by the CDI (Contexts and Dependency Injection) container. This means that there will be one instance of this class shared across the entire application.

**Step 3:** Create a Producer Method

```java
@Produces
public BigDecimal producer() {
    ThreadLocalRandom random = ThreadLocalRandom.current();
    double nextDouble = random.nextInt(1, 100);
    return new BigDecimal(nextDouble);
}
```

- The `producer()` method is annotated with `@Produces`, indicating that it is a CDI producer method. This method generates a random `BigDecimal` value within the range [1, 100) using `ThreadLocalRandom` and returns it. The produced value can be injected into other CDI-managed beans.

**Step 4:** Define a Disposer Method
```java
public void destroy(@Disposes BigDecimal value) {
    System.out.println("We don't need this number anymore: " + value);
}
```

**Step 5:** Create a class to test and explore:

```java
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.math.BigDecimal;

public class App3 {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            BigDecimal value = container.select(BigDecimal.class).get();
            logger.log(value);
        }
    }
}

```  

- The `destroy()` method is used as a disposer method for `BigDecimal` instances. It takes a parameter of type `BigDecimal` annotated with `@Disposes`. When an instance of `BigDecimal` is no longer needed, the CDI container will automatically call this method to perform cleanup or logging. In this case, it prints a message indicating that the number is no longer needed.

In summary, this code defines an application-scoped CDI bean (`NumberProducer`) that produces random `BigDecimal` values using a producer method. It also includes a disposer method to handle the cleanup of `BigDecimal` instances when they are no longer needed within the CDI context.


### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.enterprise.context.ApplicationScoped;
    import jakarta.enterprise.inject.Disposes;
    import jakarta.enterprise.inject.Produces;
    
    import java.math.BigDecimal;
    import java.util.concurrent.ThreadLocalRandom;
    
    @ApplicationScoped
    class NumberProducer {
    
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


## 2. Using InjectionPoint

  

In this lab, we will create an object taking information from who needs this information using the InjectionPoint. Thus, we will create a Logger producer.

### :material-play-box-multiple-outline: Steps

**Step 1:** Define an Application-Scoped Class for Logger Production

```java
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;

@ApplicationScoped
class LoggerProducer {
    // Class definition...
}
```
- The `LoggerProducer` class is annotated with `@ApplicationScoped`, indicating that it will be managed as an application-scoped bean by the CDI (Contexts and Dependency Injection) container.

**Step 2:** Create a Producer Method for Logger
```java
@Produces
Logger getLog(InjectionPoint ip) {
    String declaringClass = ip.getMember().getDeclaringClass().getName();
    LOGGER.info("Creating instance log to " + declaringClass);
    return Logger.getLogger(declaringClass);
}
```
- The `getLog()` method is annotated with `@Produces`, indicating that it is a CDI producer method. This method produces `java.util.logging.Logger` instances. It takes an `InjectionPoint` as a parameter, which provides information about the injection point. It retrieves the declaring class name from the injection point and creates a `Logger` instance for that class name. It also logs a message indicating the creation of the logger.

**Step 3:** Define a Class That Uses the Produced Logger

```java
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.util.logging.Logger;

public class NumberLogger {
    // Class definition...
}
```
- The `NumberLogger` class is a standard CDI bean that can be injected with a `Logger` instance.

**Step 4:** Create a Constructor for Injecting Logger
```java
@Inject
public NumberLogger(Logger logger) {
    this.logger = logger;
}
```
- The `NumberLogger` class defines a constructor that is annotated with `@Inject`. This constructor takes a `Logger` instance as a parameter, allowing CDI to inject the logger when creating an instance of `NumberLogger`.

**Step 5:** Use the Injected Logger
```java
public void log(BigDecimal value) {
    this.logger.info("The BigDecimal value is " + value);
}
```

**Step 6:** Create a class to test and explore:

```java
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.math.BigDecimal;

public class App3 {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            NumberLogger logger = container.select(NumberLogger.class).get();
            logger.log(value);
        }
    }
}


```  

- The `log()` method in the `NumberLogger` class uses the injected logger to log information. In this case, it logs the `BigDecimal` value provided as a parameter.

In summary, this code defines an application-scoped CDI bean (`LoggerProducer`) responsible for producing logger instances and a CDI-managed bean (`NumberLogger`) that uses the injected logger to log information. The producer method retrieves the declaring class name from the injection point, creates a logger for that class, and logs a message when creating the logger instance.

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    
    import jakarta.enterprise.context.ApplicationScoped;
    import jakarta.enterprise.inject.Produces;
    import jakarta.enterprise.inject.spi.InjectionPoint;
    
    import java.util.logging.Logger;
    
    @ApplicationScoped
    class LoggerProducer {
    
        private static final Logger LOGGER = Logger.getLogger(LoggerProducer.class.getName());
    
        @Produces
        Logger getLog(InjectionPoint ip) {
            String declaringClass = ip.getMember().getDeclaringClass().getName();
            LOGGER.info("Creating instance log to " + declaringClass);
            return Logger.getLogger(declaringClass);
        }
    }
    
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

## 3. CDI events


In this lab, we will explore more about the CDI events.

### :material-play-box-multiple-outline: Steps

**Step 1:** Create a `News` Class
```java
import java.util.Objects;
import java.util.function.Supplier;

public final class News implements Supplier<String> {

    private final String name;

    private News(String name) {
        this.name = name;
    }

    @Override
    public String get() {
        return name;
    }

    public static News of(String news) {
        Objects.requireNonNull(news, "news is required");
        return new News(news);
    }
}
```
- The `News` class implements the `Supplier<String>` interface, which means it can supply a `String`. It has a private constructor and a `get()` method that returns the `name` of the news.
- The static factory method `of(String news)` is provided to create instances of `News` and requires a non-null `news` argument.

**Step 2:** Create a `Magazine` Class
```java
import jakarta.enterprise.event.Observes;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class Magazine implements Consumer<News> {

    private static final Logger LOGGER = Logger.getLogger(Magazine.class.getName());

    @Override
    public void accept(@Observes News news) {
        LOGGER.info("We got the news, we'll publish it in a magazine: " + news.get());
    }
}
```
- The `Magazine` class implements `Consumer<News>` to consume `News` events. It uses the `@Observes` annotation to indicate that it observes events of type `News`.
- In the `accept()` method, it logs that the news will be published in a magazine.

**Step 3:** Create a `NewsPaper` Class
```java
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import java.util.function.Consumer;
import java.util.logging.Logger;

@ApplicationScoped
public class NewsPaper implements Consumer<News> {

    private static final Logger LOGGER = Logger.getLogger(NewsPaper.class.getName());

    @Override
    public void accept(@Observes News news) {
        LOGGER.info("We got the news, we'll publish it in a newspaper: " + news.get());
    }
}
```
- The `NewsPaper` class is similar to the `Magazine` class but is annotated with `@ApplicationScoped`, indicating that it is an application-scoped bean.
- In the `accept()` method, it logs that the news will be published in a newspaper.

**Step 4:** Create a `SocialMedia` Class
```java
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import java.util.function.Consumer;
import java.util.logging.Logger;

@ApplicationScoped
public class SocialMedia implements Consumer<News> {

    private static final Logger LOGGER = Logger.getLogger(SocialMedia.class.getName());

    @Override
    public void accept(@Observes News news) {
        LOGGER.info("We got the news, we'll publish it on Social Media: " + news.get());
    }
}
```
- The `SocialMedia` class is similar to the previous classes but is also annotated with `@ApplicationScoped`.
- In the `accept()` method, it logs that the news will be published on social media.

**Step 5:** Create a `Journalist` Class
```java
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@ApplicationScoped
public class Journalist {

    @Inject
    private Event<News> event;

    public void receiveNews(News news) {
        this.event.fire(news);
    }
}
```


**Step 5:** Create a class to test and explore:

```java
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class App4 {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            Journalist journalist = container.select(Journalist.class).get();
            journalist.receiveNews(News.of("Java 17 has arrived!!"));
        }
    }
}


```  

- The `Journalist` class is annotated with `@ApplicationScoped`.
- It injects an `Event<News>` object, which allows it to fire events of type `News`.
- The `receiveNews(News news)` method fires a `News` event using the injected `Event` object.

In summary, the provided code sets up a simple event-based news distribution system using CDI (Contexts and Dependency Injection). `News` represents a piece of news, `Magazine`, `NewsPaper`, and `SocialMedia` are consumers of news events, and `Journalist` is responsible for sending news events to the consumers.


### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    
    import java.util.Objects;
    import java.util.function.Supplier;
    
    public final class News implements Supplier<String> {
    
        private final String name;
    
        private News(String name) {
            this.name = name;
        }
    
        @Override
        public String get() {
            return name;
        }
    
        public static News of(String news) {
            Objects.requireNonNull(news, "news is required");
            return new News(news);
        }
    }
    
    import jakarta.enterprise.event.Observes;
    
    import java.util.function.Consumer;
    import java.util.logging.Logger;
    
    public class Magazine implements Consumer<News> {
    
        private static final Logger LOGGER = Logger.getLogger(Magazine.class.getName());
    
        @Override
        public void accept(@Observes News news) {
            LOGGER.info("We got the news, we'll publish it on a magazine: " + news.get());
        }
    }
    
    @ApplicationScoped
    public class NewsPaper implements Consumer<News> {
    
        private static final Logger LOGGER = Logger.getLogger(NewsPaper.class.getName());
    
        @Override
        public void accept(@Observes News news) {
            LOGGER.info("We got the news, we'll publish it on a newspaper: " + news.get());
        }
    }
    
    import jakarta.enterprise.context.ApplicationScoped;
    import jakarta.enterprise.event.Observes;
    
    import java.util.function.Consumer;
    import java.util.logging.Logger;
    
    @ApplicationScoped
    public class SocialMedia implements Consumer<News> {
    
        private static final Logger LOGGER = Logger.getLogger(SocialMedia.class.getName());
    
        @Override
        public void accept(@Observes News news) {
            LOGGER.info("We got the news, we'll publish it on Social Media: " + news.get());
        }
    }
    
    import jakarta.enterprise.context.ApplicationScoped;
    import jakarta.enterprise.event.Event;
    import jakarta.inject.Inject;
    
    @ApplicationScoped
    public class Journalist {
    
        @Inject
        private Event<News> event;
    
        public void receiveNews(News news) {
            this.event.fire(news);
        }
    
    }
    
    ```