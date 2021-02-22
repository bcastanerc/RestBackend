package com.esliceu.backend.controller;

import com.esliceu.backend.entities.Category;
import com.esliceu.backend.entities.User;
import com.esliceu.backend.serializers.CategorySerializer;
import com.esliceu.backend.services.CategoryService;
import com.esliceu.backend.services.TokenService;
import com.esliceu.backend.services.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class CategoryController {

    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().
            registerTypeAdapter(Category.class, new CategorySerializer())
            .create();

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @GetMapping("/categories")
    public ResponseEntity<String> getCategories(){
        return new ResponseEntity<>(gson.toJson(categoryService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/categories/{slug}")
    public ResponseEntity<String> getCategorty(@PathVariable String slug){
        return new ResponseEntity(gson.toJson(categoryService.findCategoryBySlug(slug)),HttpStatus.OK);
    }

    @PostMapping("/categories")
    public ResponseEntity<String> postCategory(@RequestBody String payload, HttpServletRequest request) {
        Map map = gson.fromJson(payload, Map.class);

        String token = request.getHeader("Authorization").replace("Bearer ", "");
        try {
            User u = userService.findUserByEmailEquals(tokenService.getSubject(token));
            Category c = categoryService.createCatrgory((String) map.get("title"),(String) map.get("description"),u);
            categoryService.save(c);
            return new ResponseEntity(gson.toJson(c),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(gson.toJson(""),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/categories/{slug}")
    public ResponseEntity<String> putCategory(@PathVariable String slug, @RequestBody String payload){
        Map map = gson.fromJson(payload, Map.class);
        try {
            Category c = categoryService.findCategoryBySlug(slug);
            c.setTitle((String) map.get("title"));
            c.setDescription((String) map.get("description"));
            categoryService.save(c);
            return new ResponseEntity(gson.toJson(c),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(gson.toJson(""),HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("/categories/{slug}")
    public ResponseEntity<String> deleteCategorty(@PathVariable String slug){
        try {
            categoryService.delete(categoryService.findCategoryBySlug(slug));
            return new ResponseEntity(true ,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(gson.toJson(""),HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

}
