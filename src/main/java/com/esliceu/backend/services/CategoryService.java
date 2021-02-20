package com.esliceu.backend.services;

import com.esliceu.backend.entities.Category;

import java.util.List;

public interface CategoryService {
    Category findById(Long category_id) throws Exception;
    Category findCategoryBySlug(String slug);
    List<Category> findAll();
    void save(Category category);
}
