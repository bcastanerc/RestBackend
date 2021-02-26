package com.esliceu.backend.controller;

import com.esliceu.backend.entities.User;
import com.esliceu.backend.serializers.UserPermisionsSerializer;
import com.esliceu.backend.serializers.UserSerializer;
import com.esliceu.backend.services.UserService;
import com.esliceu.backend.services.TokenService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().
            registerTypeAdapter(User.class, new UserSerializer())
            .create();

    Gson gsonPermissions = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().
            registerTypeAdapter(User.class, new UserPermisionsSerializer())
            .create();

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @GetMapping("/getprofile")
    public ResponseEntity<String> getProfile(@RequestAttribute String user) {
        return new ResponseEntity<>(gsonPermissions.toJson(userService.findUserByEmailEquals(user)), HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<String> putProfile(@RequestAttribute String user, @RequestBody String payload) {
        Map map = gson.fromJson(payload, Map.class);
       try {
           HashMap<String, Object> tokenUser = userService.updateUserToken(user, (String) map.get("email"),
                   (String) map.get("name"), (String) map.get("avatar"), null, null);
           if (tokenUser != null) {
               return new ResponseEntity<>(gsonPermissions.toJson(tokenUser), HttpStatus.OK);
           }
       }catch (Exception e){
           return new ResponseEntity(gson.toJson(JsonParser.parseString("{\"message\":\"An error occured while updating your profile.\"}")),HttpStatus.BAD_REQUEST);
       }
        return new ResponseEntity(gson.toJson(JsonParser.parseString("{\"message\":\"The introduced email allready exists.\"}")),HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/profile/password")
    public ResponseEntity<String> putProfilePassword(@RequestAttribute String user, @RequestBody String payload) {
        Map map = gson.fromJson(payload, Map.class);
        HashMap<String, String> msg = new HashMap<>();
        try {
            HashMap<String, Object> tokenUser = userService.updateUserToken(user, null,null,null,(String) map.get("currentPassword"), (String) map.get("newPassword"));
            if (tokenUser != null) {
                return new ResponseEntity<>(gsonPermissions.toJson(tokenUser), HttpStatus.OK);
            }
        }catch (Exception e){
            msg.put("message", " Your new password cannot be the same as the old password.");
            return new ResponseEntity<>(gson.toJson(msg),HttpStatus.BAD_REQUEST);
        }
        msg.put("message",  "There is an unexpected error.");
        return new ResponseEntity<>(gson.toJson(msg),HttpStatus.BAD_REQUEST);
    }
}
