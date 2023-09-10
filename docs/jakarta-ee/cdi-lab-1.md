# Jakarta CDI - Lab 1 - Basic part 1

In this lab, we will explore creating the first injection where Vehicle interface into a single implementation Car.

## 1. Defining the interface

### :material-play-box-multiple-outline: Steps

1. Open the `01-jakarta-ee` project and navigate to the `src/main/java`
2. Create an interface called `Vehicle` in the `expert.os.labs.persistence.cid.vehicle` package
3. Add a method called `move()`

	!!! info

		The `move()` method represents the action of moving a vehicle.
		Any class implementing this interface must provide an implementation for this method.

### :material-checkbox-multiple-outline: Expected results

* Interface `Vehicle` created in the `src/main/java` at the `expert.os.labs.persistence.cdi.vehicle`

### :material-check-outline: Solution

??? example "Click to see..."

	```java
	public interface Vehicle {

		void move();
	}
	```

## 2. Defining the class

### :material-play-box-multiple-outline: Steps

1. Create a class called `Car` in the `expert.os.labs.persistence.cid.vehicle` package
2. Implements the `Vehicle` interface
3. Annotate the `Car` class with the `@ApplicationScoped` annotation from `jakarta.enterprise.context` package

	!!! info

		This annotation is commonly used in Jakarta EE (formerly Java EE) to define a class as an application-scoped bean.
		It means that only one instance of this class will be created and managed by the Jakarta EE container throughout the application's lifetime.

4. Declare a logger for this class

	!!! Tip
		You can use the `java.util.logging.Logger` class
		```java
		private static final Logger LOGGER = Logger.getLogger(Car.class.getName());
		```

5. Add a field called `name` of type `String`
6. Add a constructor to the class associating a random Sxtring to the `name` field

	!!! Tip
		You can use the `UUID.randomUUID().toString()` method to generate a random String

7. Override the method `move()` logging the `name` of the veicle using the logger

### :material-checkbox-multiple-outline: Expected results

* Class `Car` implenmenting `Vehicle` created containing a method `move()`

### :material-check-outline: Solution

??? example "Click to see..."

	 ```java
	 import jakarta.enterprise.context.ApplicationScoped;

 	 import java.util.Objects;
 	 import java.util.UUID;
	 import java.util.logging.Logger;

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
	 }
	 ```

## 3. Explore the class usage

### :material-play-box-multiple-outline: Steps

1. Create a class called `AppVehicle` in the `expert.os.labs.persistence.cid` package
2. Add the following code to the class

	```java
	import jakarta.enterprise.inject.se.SeContainer;
	import jakarta.enterprise.inject.se.SeContainerInitializer;

	public class AppVehicle {

		public static void main(String[] args) {
			try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
				Vehicle vehicle = container.select(Vehicle.class).get();
				vehicle.move();

				Car car = container.select(Car.class).get();
				car.move();
			}
		}
	}
	```

3. Run the class

### :material-checkbox-multiple-outline: Expected results

* Two logs related to the `move()` method execution, one for the `vehicle.move()` and another from the `car.move()` showing:

	```
	INFO: My car is moving. The car's name is: <<UUID>>
	```

### :material-check-outline: Solution

??? example "Click to see..."

	```java
	import jakarta.enterprise.inject.se.SeContainer;
	import jakarta.enterprise.inject.se.SeContainerInitializer;

	public class AppVehicle {

		public static void main(String[] args) {
			try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
				Vehicle vehicle = container.select(Vehicle.class).get();
				vehicle.move();

				Car car = container.select(Car.class).get();
				car.move();
			}
		}
	}
	```
	