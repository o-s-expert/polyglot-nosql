# Jakarta CDI - Lab 1



## 1. The fisrt injection

In this lab, we will explore creating the first injection where Vehicle
interface into a single implementation Car.

material-play-box-multiple-outline: Steps

1. Create a Vehicle interface with the move method without return.
2. Create a Car implementation defined as ApplicationScoped; the move method will log the move into the logs.


You can test and explore your solution by creating the App1 class as shown below:

  ```java
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

  

1.  Create an instrument interface with the method sound returning the instrument name.
2.  Create Piano, Violin, and Xylophone as implementation.
3.  Create an enum InstrumentType with `STRING`, `PERCUSSION`, and `KEYBOARD` as elements.
4.  Create our first Qualifier called the MusicalInstrument annotation that has InstrumentType as the method, as the code below:

```java

import jakarta.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType._FIELD_;
import static java.lang.annotation.ElementType._METHOD_;
import static java.lang.annotation.ElementType._PARAMETER_;
import static java.lang.annotation.ElementType._TYPE_;
import static java.lang.annotation.RetentionPolicy._RUNTIME_;

@Qualifier
@Retention(_RUNTIME_)
@Target({_TYPE_, _METHOD_, _FIELD_, _PARAMETER_})
public @interface MusicalInstrument {
InstrumentType value();
}
```

5. In the instrument implementations and using the new qualifier, define `Piano` as `KEYBOARD`, `Violin` as `STRING`, and `Xylophone` as `PERCUSSION`.

6. Also, define Piano as the default implementation using the Default annotation provided by CDI.

7. The next step is creating a class to manage these instruments: The Orchestra will have to inject the instruments individually using CDI injections and the default implementation. E.g.:

  ```java
@Inject
@MusicalInstrument(InstrumentType._STRING_)
private Instrument string;

@Inject
private Instrument solo;
```
  

The next step is injecting the instrument as a collection using the Instance, allowing any implementation to be injected with the `Any` annotation.

```java  
@Inject
@Any
private Instance<Instrument> instruments;
```
  
Create a method to return the log of each instrument based on the type and the default implementation as a solo method. E.g.:
 
```java  
public void solo() {
LOGGER_.info("The solo's sound: " + this.keyboard.sound());
}

public void keyboard() {
 LOGGER_.info("The keyboard's sound: " + this.keyboard.sound());
}
```  

For test and exploring, create the App2 class with the following structure:


```java

import expert.os.labs.persistence.nosql.cdi.music.Orchestra;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class App2 {

public static void main(String[] args) {

try (SeContainer container = SeContainerInitializer._newInstance_().initialize()) {

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