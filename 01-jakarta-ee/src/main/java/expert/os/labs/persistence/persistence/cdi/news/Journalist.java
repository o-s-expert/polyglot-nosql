package expert.os.labs.persistence.persistence.cdi.news;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@ApplicationScoped
public class Journalist {

    @Inject
    private Event<News> event;

    public void receiveNews(News news) {
        this.event.fire(news);
    }

}
