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


@SpringBootTest
class EnglishStoriesBotApplicationTests {
	@Test
	void contextLoads() {
		
	}

}
