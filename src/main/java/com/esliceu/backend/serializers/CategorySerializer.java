package com.esliceu.backend.serializers;

import com.esliceu.backend.entities.Category;
import com.google.gson.*;

import java.lang.reflect.Type;

public class CategorySerializer implements JsonSerializer<Category> {
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
