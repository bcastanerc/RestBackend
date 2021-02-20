package com.esliceu.backend.repos;

import com.esliceu.backend.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {
   // FindAllByCategoryId
    List<Topic> findAllByCategoryId(Long category_id);
}
