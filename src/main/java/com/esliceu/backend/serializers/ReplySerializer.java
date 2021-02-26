package com.esliceu.backend.serializers;

import com.esliceu.backend.entities.Reply;
import com.esliceu.backend.entities.User;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;

public class ReplySerializer implements JsonSerializer<Reply> {
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(User.class, new UserSerializer()).create();

    @Override
    public JsonElement serialize(Reply reply, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        jsonObject.addProperty("content", reply.getContent());
        jsonObject.addProperty("createdAt", reply.getCreatedAt().format(formatter));
        jsonObject.addProperty("topic",reply.getTopic().getId());
        jsonObject.addProperty("updatedAt", reply.getUpdatedAt().format(formatter));
        jsonObject.add("user", gson.toJsonTree(reply.getUser()));
        jsonObject.addProperty("__v",0);
        jsonObject.addProperty("_id",reply.getId());

        return jsonObject;
    }
}
