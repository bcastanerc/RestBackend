package com.esliceu.backend.controller;

import com.esliceu.backend.entities.Category;
import com.esliceu.backend.entities.User;
import com.esliceu.backend.serializers.CategorySerializer;
import com.esliceu.backend.services.CategoryService;
import com.esliceu.backend.services.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class CategoryController {

    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().
            registerTypeAdapter(Category.class, new CategorySerializer())
            .create();

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @GetMapping("/setInfo")
    @ResponseBody
    public String setInfo() throws Exception {
        Category c = new Category();

        c.setDescription("Segunda categoria 22");
        c.setTitle("Segunda prueba  22");
        c.setSlug("Prueba Segunda 22");
        c.setColor("hsl(300,50%,50%)");
        c.setUser(userService.findById(1L));

        categoryService.save(c);

        return "Ok";
    }

    @GetMapping("/categories")
    public ResponseEntity<String> getCategories(){
        return new ResponseEntity<>(gson.toJson(categoryService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/categories/{slug}")
    public ResponseEntity<String> getCategorty(@PathVariable String slug){
        Category category = categoryService.findCategoryBySlug(slug);
        return new ResponseEntity(gson.toJson(category),HttpStatus.OK);
    }
}
