package com.esliceu.backend.controller;

import com.esliceu.backend.entities.User;
import com.esliceu.backend.serializers.UserPermisionsSerializer;
import com.esliceu.backend.serializers.UserSerializer;
import com.esliceu.backend.services.UserService;
import com.esliceu.backend.services.TokenService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().
            registerTypeAdapter(User.class, new UserSerializer())
            .create();

    Gson gsonPermissions = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().
            registerTypeAdapter(User.class, new UserPermisionsSerializer())
            .create();


    @PostMapping("/login")
    public ResponseEntity<String> postRegister(@RequestBody String input) throws NoSuchAlgorithmException {
        Map map = gson.fromJson(input, Map.class);
        User u = userService.login((String) map.get("email"),(String) map.get("password"));
        if (u != null) {
            String token = tokenService.newToken(u.getEmail());
            HashMap<String,Object> tokenUser = new HashMap<>();
            tokenUser.put("token", token);
            tokenUser.put("user", u);
            return new ResponseEntity<>(gsonPermissions.toJson(tokenUser), HttpStatus.OK);
        } else {
            HashMap<String,String> msg = new HashMap<>();
            msg.put("message","Incorrect email or password");
            return new ResponseEntity<>(gson.toJson(msg), HttpStatus.BAD_REQUEST);
        }
    }
}
