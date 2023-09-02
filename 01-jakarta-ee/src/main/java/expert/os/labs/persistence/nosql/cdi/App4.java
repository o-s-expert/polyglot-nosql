package expert.os.labs.persistence.nosql.cdi;

import expert.os.labs.persistence.nosql.cdi.news.Journalist;
import expert.os.labs.persistence.nosql.cdi.news.News;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class App4 {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            Journalist journalist = container.select(Journalist.class).get();
            journalist.receiveNews(News.of("Java 17 has arrived!!"));
        }
    }
}
