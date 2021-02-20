package com.esliceu.backend.services;

import com.esliceu.backend.entities.Topic;

import java.util.List;

public interface TopicService {
    Topic findByID(Long topic_id);
    List<Topic> findAllByCategoryId(Long category_id);
    List<Topic> findAll();
    void save(Topic topic);
}
