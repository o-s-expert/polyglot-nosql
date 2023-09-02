# Jakarta CDI - Lab 1



## 1. The fisrt injection

In this lab, we will explore creating the first injection where Vehicle
interface into a single implementation Car.

material-play-box-multiple-outline: Steps

Create a Vehicle interface with the move method without return.
Create a Car implementation defined as ApplicationScoped; the move method will log the move into the logs.


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