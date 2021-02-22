package com.esliceu.backend.services;

import com.esliceu.backend.entities.Topic;
import com.esliceu.backend.repos.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

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
    public void save(Topic t) {
        topicRepository.save(t);
    }

    @Override
    public Topic updateCreate(Long id, String category, String content, String title, String email) {
        LocalDateTime now = LocalDateTime.now();
        Topic t;
        if (id != null){
            t = findByID(id);
        }else{
            t = new Topic();
            t.setUser(userService.findUserByEmailEquals(email));
            t.setCreatedAt(now);
        }
        t.setUpdatedAt(now);
        t.setCategory(categoryService.findCategoryBySlug(category));
        t.setContent(content);
        t.setTitle(title);
        save(t);
        return t;
    }

    @Override
    public void delete(Topic t) {
        topicRepository.delete(t);
    }
}
