# Jakarta CDI - Intermediate Lab 3

In this lab, we will explore more about the CDI events.

## 1. Create the `LoggerProducer` class

### :material-play-box-multiple-outline: Steps

1. Create a new package called `news` in the `expert.os.labs.persistence.cid` package
2. Create the class `News` in the `expert.os.labs.persistence.cid.news`
3. Implements the `Supplier<String>` interface and it's method `get()`
4. Add a private `String` field named `name`
5. Create a constructor to associate its name
6. Add a factory method `of()` that will create a new instance of `News`
7. Make the `get()` method return the value of the `name` attribute

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    public final class News implements Supplier<String> {

        private String name;

        public News(String name) {
            this.name = name;
        }

        public static News of(String news) {
            return new News(news);
        }

        @Override
        public String get() {
            return name;
        }
    }
    ```

## 2. Create the `MAgazine` class

### :material-play-box-multiple-outline: Steps

1. Create a class called `Magazine` in the `expert.os.labs.persistence.cid.news` package
2. Implements the `Consumer<News>` and the method `accept()`
    - the `Consumer` class is from the `java.util.function` package
3. Define a private attribute `Logger` from the `java.util.logging` package
4. Add the `@Observes` annotation to the `News` attribute in the `accept()` method, where it's from the `jakarta.enterprise.event` package
5. Log the `News` in the `accept()` method

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
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
    ```

## 3. Create the `NewsPaper` class

### :material-play-box-multiple-outline: Steps

1. Create a class called `NewsPapper` in the `expert.os.labs.persistence.cid.news` package
2. Annotate it with `@ApplicationScoped` from the `jakarta.enterprise.context` package
3. Implements the `Consumer<News>` and the method `accept()`
    - the `Consumer` class is from the `java.util.function` package
4. Define a private attribute `Logger` from the `java.util.logging` package
5. Add the `@Observes` annotation to the `News` attribute in the `accept()` method, where it's from the `jakarta.enterprise.event` package
6. Log the `News` in the `accept()` method

### :material-check-outline: Solution

??? example "Click to see..."

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
            LOGGER.info("We got the news, we'll publish it on a newspaper: " + news.get());
        }
    }
    ```

## 4. Create the `SocialMedia` class

### :material-play-box-multiple-outline: Steps

1. Create a class called `SocialMedia` in the `expert.os.labs.persistence.cid.news` package
2. Annotate it with `@ApplicationScoped` from the `jakarta.enterprise.context` package
3. Implements the `Consumer<News>` and the method `accept()`
    - the `Consumer` class is from the `java.util.function` package
4. Define a private attribute `Logger` from the `java.util.logging` package
5. Add the `@Observes` annotation to the `News` attribute in the `accept()` method, where it's from the `jakarta.enterprise.event` package
6. Log the `News` in the `accept()` method

### :material-check-outline: Solution

??? example "Click to see..."

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

## 5. Create the `Journalist` class

### :material-play-box-multiple-outline: Steps

1. Create a class called `Journalist` in the `expert.os.labs.persistence.cid.news` package
2. Annotate it with `@ApplicationScoped` from the `jakarta.enterprise.context` package
3. Create a private `Event<News>` field and annotate it with `@Inject` from `jakarta.inject` package
    - The `Event` is from the `jakarta.enterprise.event` package
4. Create a method named `receiveNews` with a `News` parameter which will fire and event (`this.event.fire()`)

### :material-check-outline: Solution

??? example "Click to see..."

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

## 6. Create the app class

### :material-play-box-multiple-outline: Steps

1. Create a class called `AppJournalist` in the `expert.os.labs.persistence.cid` package
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

4. Obtain the value of the `Journalist` through the container instance
5. Printout the news using the method `receiveNews` from the `Journalist` using the `News.of()`

### :material-checkbox-multiple-outline: Expected results

* The following output
   
    ```
    INFO: We got the news, we'll publish it on Social Media: <the news you defined>
    ```

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import expert.os.labs.persistence.cdi.news.Journalist;
    import expert.os.labs.persistence.cdi.news.News;
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;

    public class AppJournalist {

        public static void main(String[] args) {
            try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
                Journalist journalist = container.select(Journalist.class).get();
                journalist.receiveNews(News.of("Java 21 has arrived!!"));
            }
        }
    }
    ```    
