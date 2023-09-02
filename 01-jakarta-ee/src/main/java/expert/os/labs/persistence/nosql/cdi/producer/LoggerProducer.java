package expert.os.labs.persistence.nosql.cdi.producer;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;

import java.util.logging.Logger;

@ApplicationScoped
class LoggerProducer {

    private static final Logger LOGGER = Logger.getLogger(LoggerProducer.class.getName());

    @Produces
    Logger getLog(InjectionPoint ip) {
        String declaringClass = ip.getMember().getDeclaringClass().getName();
        LOGGER.info("Creating instance log to " + declaringClass);
        return Logger.getLogger(declaringClass);
    }
}
