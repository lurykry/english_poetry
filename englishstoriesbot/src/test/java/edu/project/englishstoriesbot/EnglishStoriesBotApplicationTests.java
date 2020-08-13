package edu.project.englishstoriesbot;

import edu.project.englishstoriesbot.exeptions.FailedToLoadAuthorsException;
import edu.project.englishstoriesbot.httpclient.CustomHttpClient;
import edu.project.englishstoriesbot.model.Expression;
import edu.project.englishstoriesbot.model.Poem;
import edu.project.englishstoriesbot.restapiclient.dictionary.DictionaryApiClient;
import edu.project.englishstoriesbot.restapiclient.dictionary.DictionaryJsonProcessor;
import edu.project.englishstoriesbot.restapiclient.poetry.PoetryApiClient;
import edu.project.englishstoriesbot.util.ExpressionDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

@SpringBootTest
class EnglishStoriesBotApplicationTests {

	@Autowired
	private DictionaryApiClient apiClient;
	@Autowired
	private PoetryApiClient poetryApiClient;
	@Test
	void contextLoads() throws InterruptedException, IOException, ExecutionException, URISyntaxException, FailedToLoadAuthorsException {
		poetryApiClient.getAllAuthors().forEach(System.out::println);
	}

}
