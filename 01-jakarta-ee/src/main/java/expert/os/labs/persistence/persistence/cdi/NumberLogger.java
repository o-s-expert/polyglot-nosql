package expert.os.labs.persistence.persistence.cdi;

import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.logging.Logger;

public class NumberLogger {

    private Logger logger;

    NumberLogger() {
    }

    @Inject
    public NumberLogger(Logger logger) {
        this.logger = logger;
    }

    public void log(BigDecimal value) {
        this.logger.info("The BigDecimal value is " + value);
    }


}
