package com.esliceu.backend.serializers;

import com.esliceu.backend.entities.Category;
import com.esliceu.backend.entities.User;
import com.esliceu.backend.services.CategoryService;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserPermisionsSerializer implements JsonSerializer<User> {

    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

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

        ArrayList<String> categoriesPermissions = new ArrayList<>();
        categoriesPermissions.add("categories_topics:write");
        categoriesPermissions.add("categories_topics:delete");
        categoriesPermissions.add("categories_replies:write");
        categoriesPermissions.add("categories_replies:delete");

        HashMap<String, Object> categoryPermissions = new HashMap<>();

        if (user.getRole().equals("moderator")){
            categoryPermissions.put(user.getCategoryModerated().getSlug(), categoriesPermissions);
        }
        HashMap<String, Object> permissions = new HashMap<>();
        permissions.put("categories", categoryPermissions);
        permissions.put("root", getPermissionsByRole(user));
        jsonObject.add("permissions",gson.toJsonTree(permissions));
        return jsonObject;
    }

    public List<String> getPermissionsByRole(User user) {
        List<String> root = new ArrayList<>();
        root.add("own_topics:write");
        root.add("own_topics:delete");
        root.add("own_replies:write");
        root.add("own_replies:delete");
        if (user.getRole().equals("admin")){
            root.add("categories:write");
            root.add("categories:delete");
        }
        return root;
    }
}
