package edu.project.englishstoriesbot.restapiclient.dictionary;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.project.englishstoriesbot.model.Expression;
import edu.project.englishstoriesbot.util.ExpressionDeserializer;

public final class DictionaryJsonProcessor {

    protected static Expression parse(String json){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Expression.class, new ExpressionDeserializer())
                .create();
        return gson.fromJson(json,Expression.class);
    }
}
