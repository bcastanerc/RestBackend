package com.esliceu.backend.repos;

import com.esliceu.backend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryBySlug(String slug);
}
