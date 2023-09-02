package expert.os.labs.persistence.nosql.cdi;

import expert.os.labs.persistence.nosql.cdi.auditaded.FastSupplier;
import expert.os.labs.persistence.nosql.cdi.auditaded.SlowSupplier;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.util.function.Supplier;

public class App6 {

    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            Supplier<String> fastSupplier = container.select(FastSupplier.class).get();
            Supplier<String> slowSupplier = container.select(SlowSupplier.class).get();
            System.out.println("The result: " + fastSupplier.get());
            System.out.println("The result: " + slowSupplier.get());
        }
    }
}
