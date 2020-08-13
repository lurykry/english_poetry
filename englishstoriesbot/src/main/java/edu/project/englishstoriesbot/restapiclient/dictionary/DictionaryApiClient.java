package edu.project.englishstoriesbot.restapiclient.dictionary;

import edu.project.englishstoriesbot.httpclient.CustomHttpClient;
import edu.project.englishstoriesbot.model.Author;
import edu.project.englishstoriesbot.model.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

@Component
@PropertySource("classpath:application.properties")
public class DictionaryApiClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(DictionaryApiClient.class);
    private static final String URL = "https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=";

    @Value("${yandex.api.key}")
    private String yandexToken;

    public Expression translateExpression(String expression) throws InterruptedException, IOException, ExecutionException, URISyntaxException {
        String request = URL + yandexToken + "&lang=en-ru&text=" + expression;
        LOGGER.info("request:{}", request);
        CustomHttpClient client = new CustomHttpClient();
        String translation = client.sendRequestGetResponse(request);
        LOGGER.info("translation:{}", translation);
        return DictionaryJsonProcessor.parse(translation);
    }
}
