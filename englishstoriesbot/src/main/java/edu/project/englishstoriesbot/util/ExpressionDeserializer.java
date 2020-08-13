package edu.project.englishstoriesbot.util;

import com.google.gson.*;
import edu.project.englishstoriesbot.model.Expression;


import java.lang.reflect.Type;

public class ExpressionDeserializer implements JsonDeserializer<Expression> {

    @Override
    public Expression deserialize(JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context) throws JsonParseException {
        Expression expression = new Expression();
        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray defs = jsonObject.getAsJsonArray("def");
        for (JsonElement def : defs) {
            expression.getOriginalExpression().add(def.getAsJsonObject().get("text").getAsString());
            expression.getPartOfSpeech().add(def.getAsJsonObject().get("pos").getAsString());
            JsonArray translations = def.getAsJsonObject().getAsJsonArray("tr");
            if(translations != null){
                for (JsonElement translation : translations) {
                    expression.getTranslations().add(translation.getAsJsonObject().get("text").getAsString());
                    JsonArray synonyms = translation.getAsJsonObject().getAsJsonArray("mean");
                    if(synonyms != null){
                        for (JsonElement synonym : synonyms) {
                            expression.getSynonyms().add(synonym.getAsJsonObject().get("text").getAsString());
                        }
                    }
                    JsonArray examples = translation.getAsJsonObject().getAsJsonArray("ex");
                    if(examples != null){
                        for (JsonElement example : examples) {
                            expression.getExamples().add(example.getAsJsonObject().get("text").getAsString());
                        }
                    }

                }
            }

        }

        return expression;
    }
}
