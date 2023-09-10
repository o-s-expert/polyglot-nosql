package expert.os.labs.persistence.persistence.cdi;

import expert.os.labs.persistence.persistence.cdi.vehicle.Car;
import expert.os.labs.persistence.persistence.cdi.vehicle.Vehicle;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class AppVehicle {

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
