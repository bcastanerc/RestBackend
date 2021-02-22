package com.esliceu.backend.services;

import com.esliceu.backend.entities.Reply;
import com.esliceu.backend.entities.Topic;
import com.esliceu.backend.entities.User;
import com.esliceu.backend.repos.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReplyServiceImpl implements ReplyService{

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    UserService userService;

    @Autowired
    TopicService topicService;

    @Override
    public Reply findById(Long reply_id) {
        return replyRepository.findById(reply_id).get();
    }

    @Override
    public void save(Reply reply) {
        replyRepository.save(reply);
    }

    @Override
    public Reply updateCreate(Long id, Long topic, String content, String actualUser) {
        LocalDateTime now = LocalDateTime.now();
        Reply r;
        if (id != null){
            r = findById(id);
        }else{
            r = new Reply();
            r.setUser(userService.findUserByEmailEquals(actualUser));
            r.setCreatedAt(now);
            r.setTopic(topicService.findByID(topic));
        }
        r.setUpdatedAt(now);
        r.setContent(content);
        save(r);
        return r;
    }

    @Override
    public void delete(Reply r) {

    }
}
