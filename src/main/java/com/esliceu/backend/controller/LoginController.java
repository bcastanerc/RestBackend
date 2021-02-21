package com.esliceu.backend.controller;

import com.esliceu.backend.entities.Category;
import com.esliceu.backend.entities.User;
import com.esliceu.backend.serializers.CategorySerializer;
import com.esliceu.backend.serializers.UserSerializer;
import com.esliceu.backend.services.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().
            registerTypeAdapter(User.class, new UserSerializer())
            .create();


    @PostMapping("/login")
    public ResponseEntity<String> postRegister(@RequestBody String input) throws NoSuchAlgorithmException {
        Map map = gson.fromJson(input, Map.class);
        User u = userService.login((String) map.get("email"),(String) map.get("password"));
        if (u != null) return new ResponseEntity<>(gson.toJson(u), HttpStatus.OK);
        else return new ResponseEntity<>(gson.toJson("error"), HttpStatus.BAD_REQUEST);
    }
}
