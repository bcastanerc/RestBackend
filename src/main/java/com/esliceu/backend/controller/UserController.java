package com.esliceu.backend.controller;

import com.esliceu.backend.entities.User;
import com.esliceu.backend.serializers.UserSerializer;
import com.esliceu.backend.services.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().
            registerTypeAdapter(User.class, new UserSerializer())
            .create();

    @Autowired
    UserService userService;

    @GetMapping("/setInfoUser")
    @ResponseBody
    public String setInfoUser() throws Exception {
        User u = new User();
        u.setEmail("bcastaner@gmail.com");
        u.setName("Tolo");
        u.setRole("admin");
        userService.save(u);
    return "Ok";
    }

}
