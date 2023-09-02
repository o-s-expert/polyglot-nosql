# Jakarta CDI - Lab 2


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