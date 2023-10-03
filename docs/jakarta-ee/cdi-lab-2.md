# Jakarta CDI - Lab 1 - Basic part 2

In this lab, we will explore using CDI qualifiers to work when there are several implementations of a single interface.

## 1. Define an instrument

### :material-play-box-multiple-outline: Steps

1. Open the `01-jakarta-ee` project and navigate to the `src/main/java`
2. Create an `interface` called `Instrument` in the `expert.os.labs.persistence.cid.music` package
3. Add a `sound()` method that returns `String`

### :material-checkbox-multiple-outline: Expected results

* Interface `Instrument` that will produce a `sound()` for the later instrument types

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    public interface Instrument {
        String sound();
    }
    ```

## 2. Define the instrument types

### :material-play-box-multiple-outline: Steps

1. Create an `enum` called `InstrumentType` in the `expert.os.labs.persistence.cid.music` package
2. Add the following constants, related to the different musical instrument types:
    - `STRING`
    - `PERCUSSION`
    - `KEYBOARD`

### :material-checkbox-multiple-outline: Expected results

* Enum `InstrumentType` created in the `src/main/java` at the `expert.os.labs.persistence.cdi.music`

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    public enum InstrumentType {
        STRING, PERCUSSION, KEYBOARD;
    }
    ```
    
## 3. Create a custom Qualifier Annotation (the musical instruments)

The annotated class will represent musical instruments and specify the instrument type.

### :material-play-box-multiple-outline: Steps

1. Create an `@interface` called `MusicalInstrument` in the `expert.os.labs.persistence.cid.music` package
2. Add the following code:

       ```java
       import jakarta.inject.Qualifier;
       import java.lang.annotation.Retention;
       import java.lang.annotation.Target;
       
       import static java.lang.annotation.ElementType.*;
       import static java.lang.annotation.RetentionPolicy.RUNTIME;
        
       @Qualifier
       @Retention(RUNTIME)
       @Target({TYPE, METHOD, FIELD, PARAMETER})
       public @interface MusicalInstrument {
           InstrumentType value();
       }
       ```
       
   3. Define different musical instruments (classes), implementing the `Instrument` interface:
        
      - the classes must be created at the `expert.os.labs.persistence.cid.music` package
      - use the `jakarta.enterprise.inject` package to import the `@Default` annotation
       
       | Class | Annotation | sound |
       |--|--|--|
       | `Piano` | `@MusicalInstrument(InstrumentType.KEYBOARD)` and `@Default` | `return "piano"` |
       | `Violin` | `@MusicalInstrument(InstrumentType.STRING)` | `return "violin"` |
       | `Xylophone` | `@MusicalInstrument(InstrumentType.PERCUSSION)` | `return "xylophone"` |
       

### :material-checkbox-multiple-outline: Expected results

* Three classes (`Piano`, `Violin`, and `Xylophone`) represent different musical instruments and are annotated with `MusicalInstrument` to specify their instrument types.
* `@Default` annotation is used on the `Piano` class, indicating it as the default implementation for the `Instrument` interface.

### :material-check-outline: Solution

??? example "Click to see..."

    Piano class
    ```java
    import jakarta.enterprise.inject.Default;

    @MusicalInstrument(InstrumentType.KEYBOARD)
    @Default
    class Piano implements Instrument {
        @Override
        public String sound() {
            return "piano";
        }
    }
    ```
    
    Violin class
    ```java
    @MusicalInstrument(InstrumentType.STRING)
    class Violin implements Instrument {
        @Override
        public String sound() {
            return "violin";
        }
    }
    ```
    
    Xylophone class
    ```java
    @MusicalInstrument(InstrumentType.PERCUSSION)
    class Xylophone implements Instrument {
        @Override
        public String sound() {
            return "xylophone";
        }
    }
    ```
       
## 4. Create the orchestra

The `Orchestra` class represents an orchestra and is annotated with `@ApplicationScoped`, indicating that there will be a single instance of this class per application.

It contains fields for different types of musical instruments (`percussion`, `keyboard`, `string`, and `solo`) and an `Instance<Instrument>` to handle multiple instrument instances.
The class includes methods to play specific types of instruments (`string()`, `percussion()`, `keyboard()`), play a solo instrument (`solo()`), and play all available instruments (`allSound()`).
    
### :material-play-box-multiple-outline: Steps

1. Create a class called `Orchestra` in the `expert.os.labs.persistence.cid.music` package
2. Annotate the class with `@ApplicationScoped` from the `jakarta.enterprise.context` package
3. Add a logger for this class
  
    ```java
    private static final Logger LOGGER = Logger.getLogger(Orchestra.class.getName());
    ```
    
4. Add the following `Instrument` fields with the related annotations
   
    | Field name | Annotation 1 | Annotation 2 |
    |--|--|--|
    | `percussion` | `@Inject` | `@MusicalInstrument(InstrumentType.PERCUSSION)` |
    | `keyboard` | `@Inject` | `@MusicalInstrument(InstrumentType.KEYBOARD)` |
    | `string` | `@Inject` | `@MusicalInstrument(InstrumentType.STRING)` |
    | `solo` | `@Inject` |  |

5. Add the `Instrument` instance as a new field as follows:

    ```java
    @Inject
    @Any
    private Instance<Instrument> instruments;
    ```
    
6. Add the following methods, that are related to each instrument type
    - you can use the following method template, replacing `${instrumentType}` with the list provided below:
    
        ```java
        public void ${instrumentType}() {
            LOGGER.info("The ${instrumentType}'s sound: " + this.${instrumentType}.sound());
        }
        ```
        
        ??? abstract "Example"
        
            ```java
            public void percussion() {
                LOGGER.info("The percussion's sound: " + this.percussion.sound());
            }
            ```

    - methods to add:
        
        | method |
        |--|
        | `percussion`|
        | `keyboard`|
        | `solo`|
        
7. Add a method to list all sounds

    ```java
    public void allSound() {
        String sounds = this.instruments.stream().map(Instrument::sound).collect(Collectors.joining(", "));
        LOGGER.info("All instruments sounds are: " + sounds);
    }
    ```
        
### :material-checkbox-multiple-outline: Expected results

* Class `Orchestra` that can call any musical instrument to play

### :material-check-outline: Solution

??? example "Click to see..."    

    ```java
    import jakarta.enterprise.context.ApplicationScoped;
    import jakarta.enterprise.inject.Any;
    import jakarta.enterprise.inject.Instance;
    import jakarta.inject.Inject;

    import java.util.logging.Logger;
    import java.util.stream.Collectors;

    @ApplicationScoped
    public class Orchestra {

        private static final Logger LOGGER = Logger.getLogger(Orchestra.class.getName());

        @Inject
        @MusicalInstrument(InstrumentType.PERCUSSION)
        private Instrument  <;

        @Inject
        @MusicalInstrument(InstrumentType.KEYBOARD)
        private Instrument keyboard;

        @Inject
        @MusicalInstrument(InstrumentType.STRING)
        private Instrument string;

        @Inject
        private Instrument solo;

        @Inject
        @Any
        private Instance<Instrument> instruments;

            public void string() {
                LOGGER.info("The string's sound: " + this.string.sound());
            }

            public void percussion() {
                LOGGER.info("The percussion's sound: " + this.percussion.sound());
            }

            public void keyboard() {
                LOGGER.info("The keyboard's sound: " + this.keyboard.sound());
            }

            public void solo() {
                LOGGER.info("The solo's sound: " + this.keyboard.sound());
            }

            public void allSound() {
                String sounds = this.instruments.stream().map(Instrument::sound).collect(Collectors.joining(", "));
                LOGGER.info("All instruments sounds are: " + sounds);
            }
        }
    ```
    
## 5. Explore the class usage

### :material-play-box-multiple-outline: Steps

1. Create a class called `AppOrchestra` in the `expert.os.labs.persistence.cid` package
2. Add the following code to the class

    ```java
    import expert.os.labs.persistence.persistence.cdi.music.Orchestra;
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;

    public class App2 {

        public static void main(String[] args) {
            try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
                Orchestra orchestra = container.select(Orchestra.class).get();
                
                orchestra.percussion();
                orchestra.keyboard();
                orchestra.string();
                orchestra.solo();
                orchestra.allSound();
            }
        }
    }
    ```

3. Run the class

### :material-checkbox-multiple-outline: Expected results

* List of `INFO` messages related to each instrument called by the Orchestra

    ```
    INFO: The percussion's sound: xylophone
    INFO: The keyboard's sound: piano
    INFO: The string's sound: violin
    INFO: The solo's sound: piano
    INFO: All instruments sounds are: piano, violin, xylophone
    ```

### :material-check-outline: Solution

??? example "Click to see..."    

    ```java
    import expert.os.labs.persistence.persistence.cdi.music.Orchestra;
    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;

    public class AppOrchestra {

        public static void main(String[] args) {
            try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
                Orchestra orchestra = container.select(Orchestra.class).get();

                orchestra.percussion();
                orchestra.keyboard();
                orchestra.string();
                orchestra.solo();
                orchestra.allSound();
            }
        }
    }
    ```