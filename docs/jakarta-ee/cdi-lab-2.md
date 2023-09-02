# Jakarta CDI - Lab 2



## 1. Object lifecycle

  

In this lab, we will create an object and destroy objects using the Produces and Dispose annotations, respectively.

material-play-box-multiple-outline: Steps

1. Create NumberProducer class that will return a random BigDecimal using the ThreadLocalRandom.
2. In the method that returns this BigDecimal put the jakarta.enterprise.inject.Produces annotation.
3. Create a destroy method with a BigDecimal parameter logging the information.
4. Include the Disposes annotation in the BigDecimal Parameter
5. To test and explore this functionality, create an App3 class and inject the BigDecimal.
  
```java 
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import java.math.BigDecimal;
 
public class App3 {

public static void main(String[] args) {
  
try (SeContainer container = SeContainerInitializer._newInstance_().initialize()) {
	BigDecimal value = container.select(BigDecimal.class).get();
}
}
}
```

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

material-play-box-multiple-outline: Steps

1. Create a LoggerProducer class
2. Create a method that returns a Logger with `InjectionPoint` as a parameter.
3. Create a Logger with the class name using the `InjectionPoint
.getMember().getDeclaringClass().getName()`.
4. Create a NumberLogger that will inject a `Logger` as a field.
5. To test and explore this functionality, update the `App3` as the show code below:

```java
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.math.BigDecimal;

public class App3 {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            BigDecimal value = container.select(BigDecimal.class).get();
            NumberLogger logger = container.select(NumberLogger.class).get();
            logger.log(value);
        }
    }
}
```

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

material-play-box-multiple-outline: Steps

1. Create a `News` class to implement `Supplier<String>` returning the News.
2. Create `Magazine`, `NewsPaper`, `SocialMedia` classes implementing `Consumer< News>`.
3. Consume the News, putting the class name and the News using the log. Also, include the `Observers` annotation in the parameter. E.g.:

```java
@Override
public void accept(@Observes News news) {
    LOGGER.info("We got the news, we'll publish it on a magazine: " + news.get());
}
```

4. Create a Journalist class that will inject the `Event<News>event`.
5. Create a method called `receiveNews` that will receive a `News` instance and then fire the `Event`.
6. For test, create an App4 class with the code below.

```java

import expert.os.labs.persistence.nosql.cdi.news.Journalist;
import expert.os.labs.persistence.nosql.cdi.news.News;
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