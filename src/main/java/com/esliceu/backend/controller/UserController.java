package com.esliceu.backend.controller;

import com.esliceu.backend.entities.User;
import com.esliceu.backend.repos.UserRepository;
import com.esliceu.backend.serializers.UserPermisionsSerializer;
import com.esliceu.backend.serializers.UserSerializer;
import com.esliceu.backend.services.UserService;
import com.esliceu.backend.services.TokenService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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

    @Autowired
    UserRepository userRepository;

    @GetMapping("/setInfoUser")
    @ResponseBody
    public String setInfoUser() throws Exception {
        User u = new User();
        u.setEmail("tomca467@gmail.com");
        u.setName("Tolo");
        u.setRole("admin");
        userService.save(u);
    return "Ok";
    }

    @GetMapping("/getprofile")
    @ResponseBody
    public ResponseEntity<String> getProfile(HttpServletRequest request){
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        User u = userService.findUserByEmailEquals(tokenService.getSubject(token));
        return new ResponseEntity<>(gsonPermissions.toJson(u), HttpStatus.OK);
    }

}
