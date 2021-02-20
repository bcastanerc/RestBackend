package com.esliceu.backend.services;

import com.esliceu.backend.entities.Reply;
import com.esliceu.backend.repos.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyServiceImpl implements ReplyService{

    @Autowired
    ReplyRepository replyRepository;

    @Override
    public Reply findById(Long reply_id) {
        return replyRepository.findById(reply_id).get();
    }

    @Override
    public void save(Reply reply) {
        replyRepository.save(reply);
    }
}
