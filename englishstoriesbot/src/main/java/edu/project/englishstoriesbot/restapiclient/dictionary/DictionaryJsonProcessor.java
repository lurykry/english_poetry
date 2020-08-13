package edu.project.englishstoriesbot.restapiclient.dictionary;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import edu.project.englishstoriesbot.exeptions.TranslationNotFoundException;
import edu.project.englishstoriesbot.model.Expression;
import edu.project.englishstoriesbot.util.ExpressionDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DictionaryJsonProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DictionaryJsonProcessor.class);

    protected static Expression processJsonTranslation(String json) throws TranslationNotFoundException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Expression.class, new ExpressionDeserializer())
                .create();
        Expression expression;
        try{
            expression = gson.fromJson(json,Expression.class);
            return expression;
        }catch (JsonSyntaxException e){
            LOGGER.error("processJsonTranslation(): JsonSyntaxException");
            throw new TranslationNotFoundException(e);
        }
    }
}
