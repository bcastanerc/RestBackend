package com.esliceu.backend.controller;

import com.esliceu.backend.entities.Topic;
import com.esliceu.backend.serializers.TopicSerializer;
import com.esliceu.backend.serializers.TopicSerializerComplete;
import com.esliceu.backend.services.CategoryService;
import com.esliceu.backend.services.TopicService;
import com.esliceu.backend.services.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TopicController {

    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().
            registerTypeAdapter(Topic.class, new TopicSerializer())
            .create();

    Gson gsonComplete = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().
            registerTypeAdapter(Topic.class, new TopicSerializerComplete())
            .create();

    @Autowired
    TopicService topicService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @GetMapping("/categories/{slug}/topics")
    public ResponseEntity<String> getCategoryTopics(@PathVariable String slug) {
        return new ResponseEntity(gson.toJson(topicService.findAllByCategoryId(categoryService.findCategoryBySlug(slug).getId())), HttpStatus.OK);
    }

    @GetMapping("/topics/{id_topic}")
    public ResponseEntity<String> getTopicById(@PathVariable Long id_topic) {
        Topic topic = topicService.findByID(id_topic);
        topic.setViews(topic.getViews()+1);
        topicService.save(topic);
        return new ResponseEntity(gsonComplete.toJson(topic), HttpStatus.OK);
    }

    @PostMapping("/topics")
    public ResponseEntity<String> postTopics(@RequestAttribute String user, @RequestBody String payload) {
        Map map = gson.fromJson(payload, Map.class);
        HashMap<String, String> msg = new HashMap<>();
        msg.put("message","error");
        try {
            return new ResponseEntity(gson.toJson(topicService.updateCreate(null,(String) map.get("category"),
                    (String) map.get("content"),(String) map.get("title"), user)), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(gson.toJson(msg), HttpStatus.BAD_REQUEST);

        }
    }

    @PutMapping("/topics/{id_topic}")
    public ResponseEntity<String> putTopicById(@RequestAttribute String user, @PathVariable Long id_topic, @RequestBody String payload) {
        Map map = gson.fromJson(payload, Map.class);
        HashMap<String, String> msg = new HashMap<>();
        msg.put("message","error");
        try {
            return new ResponseEntity(gson.toJson(topicService.updateCreate(id_topic,(String) map.get("category"),
                    (String) map.get("content"),(String) map.get("title"), user)), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(gson.toJson(msg), HttpStatus.OK);
        }
    }

    @DeleteMapping("/topics/{id_topic}")
    public ResponseEntity<String> putTopicById(@PathVariable Long id_topic){
        try {
            topicService.delete(topicService.findByID(id_topic));
            return new ResponseEntity(true, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
        }
    }
}
