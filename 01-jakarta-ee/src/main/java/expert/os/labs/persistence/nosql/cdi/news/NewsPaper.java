package expert.os.labs.persistence.nosql.cdi.news;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import java.util.function.Consumer;
import java.util.logging.Logger;

@ApplicationScoped
public class NewsPaper implements Consumer<News> {

    private static final Logger LOGGER = Logger.getLogger(NewsPaper.class.getName());

    @Override
    public void accept(@Observes News news) {
        LOGGER.info("We got the news, we'll publish it on a newspaper: " + news.get());
    }
}
