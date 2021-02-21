package com.esliceu.backend.serializers;

import com.esliceu.backend.entities.Category;
import com.esliceu.backend.entities.User;
import com.esliceu.backend.services.UserService;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CategorySerializer implements JsonSerializer<Category> {
     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    @Override
    public JsonElement serialize(Category category, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("_id",category.getId());
        jsonObject.addProperty("color",category.getColor());
        jsonObject.addProperty("description", category.getDescription());
        jsonObject.addProperty("slug", category.getSlug());
        jsonObject.addProperty("title",category.getTitle());
        jsonObject.addProperty("__v",0);

        return jsonObject;
    }
}
