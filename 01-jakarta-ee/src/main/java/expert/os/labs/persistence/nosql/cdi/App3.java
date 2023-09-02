package expert.os.labs.persistence.nosql.cdi;

import expert.os.labs.persistence.nosql.cdi.producer.NumberLogger;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.math.BigDecimal;

public class App3 {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            BigDecimal value = container.select(BigDecimal.class).get();
            NumberLogger logger = container.select(NumberLogger.class).get();
            logger.log(value);
        }
    }
}
