package expert.os.labs.persistence.persistence.cdi;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.math.BigDecimal;

public class AppNumberLogger {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            BigDecimal value = container.select(BigDecimal.class).get();
            
            NumberLogger logger = container.select(NumberLogger.class).get();
            logger.log(value);
        }
    }
}
