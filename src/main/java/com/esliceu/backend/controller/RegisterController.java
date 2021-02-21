package com.esliceu.backend.controller;

import com.esliceu.backend.entities.Category;
import com.esliceu.backend.serializers.CategorySerializer;
import com.esliceu.backend.services.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class RegisterController {

    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().
            registerTypeAdapter(Category.class, new CategorySerializer())
            .create();

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> postRegister(@RequestBody String input){
        Map map = gson.fromJson(input, Map.class);
        HashMap<String,String> msg = new HashMap<>();
        try {
            userService.save(userService.registerUser((String) map.get("password"),(String)map.get("email"),
                    (String) map.get("role"),(String)map.get("name"),(String)map.get("moderateCategory")));
        }catch (Exception e){
            msg.put("message","This user already exists");
            return new ResponseEntity<>(gson.toJson(msg), HttpStatus.BAD_REQUEST);
        }
        msg.put("message","done");
        return new ResponseEntity<>(gson.toJson(msg), HttpStatus.OK);
    }
}
