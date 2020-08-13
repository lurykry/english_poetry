package edu.project.englishstoriesbot.restapiclient.poetry;

import edu.project.englishstoriesbot.exeptions.PoemNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class PoemFormatter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PoemFormatter.class);
    private static final String NON_ALPHABETIC = "[^a-zA-Z0-9]";

    protected static String formPoemTitle(String title){

        String formattedTitle = title.replaceAll("\"title\": ","")
                .replaceAll("--","").replaceAll("\\\\","")
                .replaceAll("\"","").replaceAll("\\n","");

        LOGGER.info("The user input name of the poem after formation:{}", formattedTitle);

        return formattedTitle;
    }

    protected static List<String> formatPoemTitles(List<String> titles){
        List<String> formattedTitles = titles
                .stream()
                .map(title -> formPoemTitle(title).trim())
                .collect(Collectors.toList());

        return formattedTitles;
    }

    protected static String findTitleAmongTitles(List<String> titles, String poemName) throws PoemNotFoundException {
        LOGGER.info("The user input name of the poem:{}", poemName);

        Optional<String> poemTitle = titles
                .stream()
                .filter(title -> PoemFormatter.formPoemTitle(title)
                        .replaceAll(" ","").replaceAll(NON_ALPHABETIC,"").equalsIgnoreCase(poemName.replaceAll(" ","")
                                .replaceAll(NON_ALPHABETIC,"")))
                .findFirst();

        return   poemTitle.orElseThrow(PoemNotFoundException::new)
                .replaceAll("\"title\": ","").replaceAll("\"","")
                .replaceAll("\\n","");
    }

    protected static List<String> formatAuthors(List<String> listOfAuthors){
        return listOfAuthors
                .stream()
                .map(author -> author.replaceAll("\"","").replaceAll(",",""))
                .collect(Collectors.toList());
    }
}
