package edu.project.englishstoriesbot.restapiclient.poetry;

import edu.project.englishstoriesbot.cache.UserData;
import edu.project.englishstoriesbot.exeptions.FailedToLoadAuthorsException;
import edu.project.englishstoriesbot.exeptions.FailedToLoadPoemException;
import edu.project.englishstoriesbot.exeptions.FailedToLoadTitlesException;
import edu.project.englishstoriesbot.exeptions.PoemNotFoundException;
import edu.project.englishstoriesbot.httpclient.CustomHttpClient;
import edu.project.englishstoriesbot.model.Poem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class PoetryApiClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(PoetryApiClient.class);
    private static final String URL = "https://poetrydb.org/";


    public List<String> getAllAuthors() throws FailedToLoadAuthorsException {
        String authors = null;
        try {
            authors = getAuthors();
        } catch (InterruptedException | IOException | ExecutionException | URISyntaxException e) {
            LOGGER.error("error has occurred in getAllAuthors", e);
            throw new  FailedToLoadAuthorsException(e);
        }
        List<String> listOfAuthors = PoetryJsonProcessor.processJsonAuthors(authors);
        return PoemFormatter.formatAuthors(listOfAuthors);
    }

    public List<String> getPoemsByAuthorName(String authorName) throws FailedToLoadTitlesException {
        String poemTitles = null;
        try {
            poemTitles = getPoemTitlesAsJsonByAuthor(authorName);
        } catch (InterruptedException | IOException | ExecutionException | URISyntaxException e) {
            LOGGER.error("error has occurred in getPoemsByAuthorName()", e);
            throw new FailedToLoadTitlesException(e);
        }
        List<String> titles = PoetryJsonProcessor.processJsonTitles(poemTitles);
        return PoemFormatter.formatPoemTitles(titles);
    }

    public Poem getUserRequiredPoem(UserData userData) throws FailedToLoadTitlesException, FailedToLoadPoemException, PoemNotFoundException {
        String poemTitles = null;
        String authorName = userData.getAuthorName();
        String poemTitle = userData.getPoemTitle();
        try {
            poemTitles = getPoemTitlesAsJsonByAuthor(authorName);
        } catch (InterruptedException | IOException | URISyntaxException | ExecutionException e) {
            LOGGER.error("error has occurred in getUserRequiredPoem", e);
            throw new FailedToLoadTitlesException(e);
        }

        List<String> titles = PoetryJsonProcessor.processJsonTitles(poemTitles);
        String title = PoemFormatter.findTitleAmongTitles(titles, poemTitle);

        try {
            return getRequiredPoem(authorName, title);
        } catch (InterruptedException | IOException | ExecutionException | URISyntaxException e) {
            LOGGER.error("error has occurred in getUserRequiredPoem", e);
            throw new FailedToLoadPoemException(e);
        }
    }

    private String getPoemTitlesAsJsonByAuthor(String authorName) throws InterruptedException, IOException, ExecutionException, URISyntaxException {
        String request = URL + "author/" + authorName.replaceAll(" ","%20") + "/title";

        CustomHttpClient client = new CustomHttpClient();
        return client.sendRequestGetResponse(request);
    }

    private Poem getRequiredPoem(String authorName, String title) throws InterruptedException, IOException, ExecutionException, URISyntaxException, PoemNotFoundException {
        String request = URL + "author,title/" + authorName.replaceAll(" ","%20") + ";" +
                title.trim().replaceAll(" ","%20");

        CustomHttpClient client = new CustomHttpClient();
        String poem =  client.sendRequestGetResponse(request);
        PoetryJsonProcessor.processJsonPoem(poem);
        return PoetryJsonProcessor.processJsonPoem(poem);
    }

    private String getAuthors() throws InterruptedException, IOException, ExecutionException, URISyntaxException {
        String request = URL + "author";
        CustomHttpClient client = new CustomHttpClient();
        return client.sendRequestGetResponse(request);
    }

}
