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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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

    @GetMapping("/setInfoReply")
    @ResponseBody
    public String setInfo() throws Exception {
        LocalDateTime now = LocalDateTime.now();

        Reply r = new Reply();
        r.setContent("Buenos Dias");
        r.setCreatedAt(now);
        r.setTopic(topicService.findByID(1L));
        r.setUser(userService.findById(1L));
        r.setUpdatedAt(now);

        replyService.save(r);

        return "Ok";
    }

    @GetMapping("/getReply/{id_reply}")
    @ResponseBody
    public ResponseEntity<String> getOneReply(@PathVariable Long id_reply) throws Exception {
        return new ResponseEntity(gson.toJson(replyService.findById(id_reply)), HttpStatus.OK);
    }
}