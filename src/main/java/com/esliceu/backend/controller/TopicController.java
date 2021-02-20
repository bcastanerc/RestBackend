package com.esliceu.backend.controller;

import com.esliceu.backend.entities.Category;
import com.esliceu.backend.entities.Topic;
import com.esliceu.backend.entities.User;
import com.esliceu.backend.repos.TopicRepository;
import com.esliceu.backend.serializers.CategorySerializer;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    public ResponseEntity<String> getCategoryTopics(@PathVariable String slug){
        Category category = categoryService.findCategoryBySlug(slug);
        List<Topic> topics = topicService.findAllByCategoryId(category.getId());
        return new ResponseEntity(gson.toJson(topics), HttpStatus.OK);
    }

    @GetMapping("/topics/{id_topic}")
    public ResponseEntity<String> getTopicById(@PathVariable Long id_topic) {
        Topic topic = topicService.findByID(id_topic);
        topic.setViews(topic.getViews()+1);
        topicService.save(topic);
        return new ResponseEntity(gsonComplete.toJson(topic), HttpStatus.OK);
    }


    @GetMapping("/setInfoTopic")
    @ResponseBody
    public String setInfo() throws Exception {
        LocalDateTime now = LocalDateTime.now();

        Topic t = new Topic();
        t.setContent("Topic2 Category2 user 1");
        t.setTitle("Hola");
        t.setCreatedAt(now);
        t.setUpdatedAt(now);
        t.setCategory(categoryService.findById(1L));
        t.setUser(userService.findById(1L));

        topicService.save(t);

        return "Ok";
    }

   /* @Autowired
    CategoryService categoryService;

    @Autowired
    TopicService topicService;

    @Autowired
    TopicRepository topicRepository;

    @GetMapping("/categories/{slug}/topics")
    public ResponseEntity<String> getCategoryTopics(@PathVariable String slug){
        Category category = categoryService.findCategoryBySlug(slug);
        // List<Topic> topics = topicService.findAll();
        System.out.println(topicRepository.findById(1).toString());
        return new ResponseEntity(topicRepository.findById(1), HttpStatus.OK);
    }*/
}
