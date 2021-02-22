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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
