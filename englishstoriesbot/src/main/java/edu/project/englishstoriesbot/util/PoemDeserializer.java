package edu.project.englishstoriesbot.util;

import com.google.gson.*;
import edu.project.englishstoriesbot.model.Poem;

import java.lang.reflect.Type;

public class PoemDeserializer implements JsonDeserializer<Poem> {

    @Override
    public Poem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Poem poem = new Poem();
        poem.setAuthor(jsonObject.get("author").getAsString());
        poem.setTitle(jsonObject.get("title").getAsString());
        JsonArray lines = jsonObject.getAsJsonArray("lines");
        StringBuilder content = new StringBuilder();
        for(JsonElement line: lines){
            content.append(line.getAsString()).append("\n");
        }
        poem.setContent(content.toString());
        return poem;
    }

}
