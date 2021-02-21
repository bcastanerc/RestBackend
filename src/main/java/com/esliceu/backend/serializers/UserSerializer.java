package com.esliceu.backend.serializers;

import com.esliceu.backend.entities.Category;
import com.esliceu.backend.entities.User;
import com.esliceu.backend.services.CategoryService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserSerializer implements JsonSerializer<User> {

    @Autowired
    CategoryService categoryService;

    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("avatarUrl",user.getAvatarUrl());
        jsonObject.addProperty("email", user.getEmail());
        jsonObject.addProperty("id",user.getId());
        jsonObject.addProperty("name", user.getName());
        jsonObject.addProperty("role",user.getRole());
        jsonObject.addProperty("__v",0);
        jsonObject.addProperty("_id",user.getId());

        return jsonObject;
    }
}
