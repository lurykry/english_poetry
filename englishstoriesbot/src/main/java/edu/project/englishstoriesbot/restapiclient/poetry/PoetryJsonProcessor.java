package edu.project.englishstoriesbot.restapiclient.poetry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import edu.project.englishstoriesbot.exeptions.PoemNotFoundException;
import edu.project.englishstoriesbot.model.Poem;
import edu.project.englishstoriesbot.util.PoemDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PoetryJsonProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PoetryJsonProcessor.class);
    private static final Pattern JSON_REGEX_TITLES = Pattern.compile("\\{(.+?)}", Pattern.DOTALL);
    private static final Pattern JSON_REGEX_AUTHORS = Pattern.compile("\\[(.+?)]", Pattern.DOTALL);

    protected static List<String> processJsonTitles(String poems) {
        List<String> titles = new ArrayList<>();
        Matcher matcher = JSON_REGEX_TITLES.matcher(poems);
        while (matcher.find()) {
            titles.add(matcher.group(1));
        }
        return titles;
    }

    protected static Poem processJsonPoem(String stringPoem) throws PoemNotFoundException {
        String formattedPoem = stringPoem.substring(1, stringPoem.length()-1);
        Gson gson = new GsonBuilder()
                .setLenient()
                .registerTypeAdapter(Poem.class, new PoemDeserializer())
                .create();
        Poem poem;
        try{
            poem = gson.fromJson(formattedPoem,Poem.class);
        }catch (JsonSyntaxException e){
            LOGGER.error("processJsonPoem(): JsonSyntaxException");
            throw new PoemNotFoundException();
        }
        return poem;
    }


    protected static List<String> processJsonAuthors(String authors) {
        List<String> listOfAuthors = new ArrayList<>();
        Matcher matcher = JSON_REGEX_AUTHORS.matcher(authors);
        while (matcher.find()) {
            listOfAuthors.add(matcher.group(1));
        }
        return listOfAuthors;
    }
}
