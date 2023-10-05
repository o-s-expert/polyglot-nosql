package expert.os.labs.persistence.persistence.cdi;

import expert.os.labs.persistence.persistence.cdi.decorator.Worker;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class AppWorker {

    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            Worker worker = container.select(Worker.class).get();
            String work = worker.work("Just a single button");
            System.out.println("The work result: " + work);
        }
    }
}
