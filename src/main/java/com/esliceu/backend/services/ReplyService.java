package com.esliceu.backend.services;

import com.esliceu.backend.entities.Reply;

public interface ReplyService {
    Reply findById(Long reply_id);
    void save(Reply reply);
}
