# Jakarta CDI - Lab 1



## 1. The fisrt injection

In this lab, we will explore creating the first injection where Vehicle
interface into a single implementation Car.

material-play-box-multiple-outline: Steps


**Step 1:** Define the `Vehicle` Interface
```java
public interface Vehicle {
    void move();
}
```
- This interface declares a single method called `move()`, which represents the action of moving a vehicle. Any class implementing this interface must provide an implementation for this method.

**Step 2:** Create the `Car` Class
```java
@ApplicationScoped
public class Car implements Vehicle {
    // Fields and methods...
}
```
- The `Car` class is annotated with `@ApplicationScoped`. This annotation is commonly used in Jakarta EE (formerly Java EE) to define a class as an application-scoped bean. It means that only one instance of this class will be created and managed by the Jakarta EE container throughout the application's lifetime.

**Step 3:** Declare Logger and Fields
```java
private static final Logger LOGGER = Logger.getLogger(Car.class.getName());
private final String name;
```
- In the `Car` class, a private `LOGGER` field is declared. This field is used for logging messages related to the car's movement.
- Another private field, `name`, is declared to store the name of the car. It's initialized with a randomly generated UUID string in the constructor.

**Step 4:** Constructor
```java
public Car() {
    this.name = UUID.randomUUID().toString();
}
```
- The class defines a constructor that initializes the `name` field with a random UUID string. This means that each `Car` instance will have a unique name.

**Step 5:** Implement the `move` Method
```java
@Override
public void move() {
    LOGGER.info("My car is moving. The car's name is: " + name);
}
```
- The `move()` method is implemented as required by the `Vehicle` interface. It logs a message using the `LOGGER` with information about the car's movement, including its name.

In summary, the provided code defines an interface `Vehicle`, and a concrete class `Car` that implements this interface. The `Car` class is annotated as an application-scoped bean, ensuring only one instance exists throughout the application's lifecycle. It has a `move` method to log the car's movement and overrides `equals` and `hashCode` methods for proper object comparison based on the car's name.

**Step 6:**  Create a class to test and explore:

```java
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class App1 {

    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            Vehicle vehicle = container.select(Vehicle.class).get();
            vehicle.move();

            Car car = container.select(Car.class).get();
            car.move();

            System.out.println("Is the same vehicle? " + car.equals(vehicle));
        }
    }
}
```


### :material-check-outline: Solution

??? example "Click to see..."

  ```java

	public interface Vehicle {

	    void move();
	}

	@ApplicationScoped
	public class Car implements Vehicle {

	    private static final Logger LOGGER = Logger.getLogger(Car.class.getName());

	    private final String name;

	    public Car() {
	        this.name = UUID.randomUUID().toString();
	    }

	    @Override
	    public void move() {
	        LOGGER.info("My car is moving. The car's name is: " + name);
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) {
	            return true;
	        }
	        if (o == null || getClass() != o.getClass()) {
	            return false;
	        }
	        Car car = (Car) o;
	        return Objects.equals(name, car.name);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hashCode(name);
	    }
	}

    ```

## 2. Using Qualifier


In this lab, we will explore using CDI qualifiers to work when there are several implementations of a single interface.

material-play-box-multiple-outline: Steps

**Step 1:** Define the `InstrumentType` Enum
```java
public enum InstrumentType {
    STRING, PERCUSSION, KEYBOARD;
}
```
- This enum defines the different types of musical instruments: STRING, PERCUSSION, and KEYBOARD.

**Step 2:** Create a Custom Qualifier Annotation
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
- This is a custom qualifier annotation called `MusicalInstrument`. It is used to annotate classes representing musical instruments and specifies the instrument type.

**Step 3:** Define Musical Instrument Classes
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

@MusicalInstrument(InstrumentType.STRING)
class Violin implements Instrument {
    @Override
    public String sound() {
        return "violin";
    }
}

@MusicalInstrument(InstrumentType.PERCUSSION)
class Xylophone implements Instrument {
    @Override
    public String sound() {
        return "xylophone";
    }
}
```
- Three classes (`Piano`, `Violin`, and `Xylophone`) represent different musical instruments and are annotated with `MusicalInstrument` to specify their instrument types.
- `@Default` annotation is used on the `Piano` class, indicating it as the default implementation for the `Instrument` interface.

**Step 4:** Create the `Orchestra` Class
```java
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class Orchestra {
    // Fields and methods...
}
```
- The `Orchestra` class represents an orchestra and is annotated with `@ApplicationScoped`, indicating that there will be a single instance of this class per application.
- It contains fields for different types of musical instruments (`percussion`, `keyboard`, `string`, and `solo`) and an `Instance<Instrument>` to handle multiple instrument instances.
- The class includes methods to play specific types of instruments (`string()`, `percussion()`, `keyboard()`), play a solo instrument (`solo()`), and play all available instruments (`allSound()`).

**Step 5:** Inject Musical Instruments
```java
@Inject
@MusicalInstrument(InstrumentType.PERCUSSION)
private Instrument percussion;

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
```
- The `@Inject` annotation is used to inject different musical instruments into the `Orchestra` class.
- Each instrument is annotated with `@MusicalInstrument` to specify its type.
- `@Any` is used to inject all available instrument instances.

**Step 6:** Implement Methods to Play Instruments
```java
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
    LOGGER.info("The solo's sound: " + this.solo.sound());
}

public void allSound() {
    String sounds = this.instruments.stream().map(Instrument::sound).collect(Collectors.joining(", "));
    LOGGER.info("All instruments sounds are: " + sounds);
}
```
- These methods use the injected instruments to log their sounds.

By following these steps, you'll have the provided classes and annotations for your Jakarta EE orchestra application with different musical instruments. You can now use these classes to model and play various musical instruments in your application.
  
**Step 7:** Create a class to test and explore:

```java
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

### :material-check-outline: Solution

??? example "Click to see..."

  ```java

	public enum InstrumentType {
	    STRING, PERCUSSION, KEYBOARD;
	}

	import jakarta.inject.Qualifier;

	import java.lang.annotation.Retention;
	import java.lang.annotation.Target;

	import static java.lang.annotation.ElementType.FIELD;
	import static java.lang.annotation.ElementType.METHOD;
	import static java.lang.annotation.ElementType.PARAMETER;
	import static java.lang.annotation.ElementType.TYPE;
	import static java.lang.annotation.RetentionPolicy.RUNTIME;

	@Qualifier
	@Retention(RUNTIME)
	@Target({TYPE, METHOD, FIELD, PARAMETER})
	public @interface MusicalInstrument {
	    InstrumentType value();
	}

	import jakarta.enterprise.inject.Default;

	@MusicalInstrument(InstrumentType.KEYBOARD)
	@Default
	class Piano implements Instrument {
	    @Override
	    public String sound() {
	        return "piano";
	    }
	}

	@MusicalInstrument(InstrumentType.STRING)
	class Violin implements Instrument {
	    @Override
	    public String sound() {
	        return "violin";
	    }
	}

	@MusicalInstrument(InstrumentType.PERCUSSION)
	class Xylophone implements Instrument {
	    @Override
	    public String sound() {
	        return "xylophone";
	    }
	}

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
	    private Instrument percussion;
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