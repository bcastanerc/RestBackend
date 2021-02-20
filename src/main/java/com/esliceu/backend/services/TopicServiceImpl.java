package com.esliceu.backend.services;

import com.esliceu.backend.entities.Topic;
import com.esliceu.backend.repos.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicRepository topicRepository;

    @Override
    public Topic findByID(Long topic_id) {
        return topicRepository.findById(topic_id).get();
    }

    @Override
    public List<Topic> findAllByCategoryId(Long category_id) {
        return topicRepository.findAllByCategoryId(category_id);
    }

    @Override
    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

    @Override
    public void save(Topic topic) {
        topicRepository.save(topic);
    }
}
