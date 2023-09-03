package expert.os.labs.persistence.persistence.cdi.producer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@ApplicationScoped
class NumberProducer {

    @Produces
    public BigDecimal producer() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        double nextDouble = random.nextInt(1, 100);
        return new BigDecimal(nextDouble);
    }

    public void destroy(@Disposes BigDecimal value) {
        System.out.println("We don't need this number anymore: " + value);
    }
}
