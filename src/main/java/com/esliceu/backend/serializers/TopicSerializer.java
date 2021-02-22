package com.esliceu.backend.serializers;

import com.esliceu.backend.entities.Category;
import com.esliceu.backend.entities.Topic;
import com.esliceu.backend.entities.User;
import com.google.gson.*;
import com.google.gson.internal.bind.JsonTreeWriter;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TopicSerializer  implements JsonSerializer<Topic> {

    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(User.class, new UserSerializer()).create();

    @Override
    public JsonElement serialize(Topic topic, Type type, JsonSerializationContext jsonSerializationContext) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("category",topic.getCategory().getId());
        jsonObject.addProperty("content",topic.getContent());
        jsonObject.addProperty("createdAt",topic.getCreatedAt().format(formatter));
        jsonObject.addProperty("id", topic.getId());
        jsonObject.addProperty("replies", "");
        jsonObject.addProperty("title", topic.getTitle());
        jsonObject.addProperty("updatedAt", topic.getUpdatedAt().format(formatter));
        jsonObject.add("user", gson.toJsonTree(topic.getUser()));
        jsonObject.addProperty("views", topic.getViews());
        jsonObject.addProperty("__v", 0);
        jsonObject.addProperty("_id", topic.getId());
        if (topic.getReplies() == null || topic.getReplies().isEmpty()){
            jsonObject.addProperty("numberOfReplies", 0);
        }else{
            jsonObject.addProperty("numberOfReplies", topic.getReplies().size());
        }

        return jsonObject;
    }
}
