package com.esliceu.backend.controller;

import com.esliceu.backend.entities.Reply;
import com.esliceu.backend.serializers.ReplySerializer;
import com.esliceu.backend.services.ReplyService;
import com.esliceu.backend.services.TopicService;
import com.esliceu.backend.services.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ReplyController {

    @Autowired
    ReplyService replyService;

    @Autowired
    TopicService topicService;

    @Autowired
    UserService userService;

    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().
            registerTypeAdapter(Reply.class, new ReplySerializer())
            .create();

    @GetMapping("/getReply/{id_reply}")
    public ResponseEntity<String> getOneReply(@PathVariable Long id_reply) throws Exception {
        return new ResponseEntity(gson.toJson(replyService.findById(id_reply)), HttpStatus.OK);
    }

    @PostMapping("/topics/{id_topic}/replies")
    public ResponseEntity<String> postReply(@PathVariable Long id_topic, @RequestAttribute String user, @RequestBody String payload){
        Map map = gson.fromJson(payload, Map.class);
        return new ResponseEntity(gson.toJson(replyService.updateCreate(null,id_topic,(String) map.get("content"), user)), HttpStatus.OK);
    }

    @PutMapping("/topics/{id_topic}/replies/{id_reply}")
    public ResponseEntity<String> postReply(@PathVariable Long id_topic, @PathVariable Long id_reply, @RequestBody String payload){
        Map map = gson.fromJson(payload, Map.class);
        return new ResponseEntity(gson.toJson(replyService.updateCreate(id_reply, id_topic,(String) map.get("content"), null)), HttpStatus.OK);
    }

    @DeleteMapping("/topics/{id_topic}/replies/{id_reply}")
    public ResponseEntity<String> deleteMapping(@PathVariable Long id_topic, @PathVariable Long id_reply){
        try {
            replyService.delete(replyService.findById(id_reply));
            return new ResponseEntity(true, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
        }
    }
}
