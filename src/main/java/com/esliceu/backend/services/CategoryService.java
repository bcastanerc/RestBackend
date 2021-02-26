package com.esliceu.backend.services;

import com.esliceu.backend.entities.Category;

import java.util.List;

public interface CategoryService {
    Category findById(Long category_id) throws Exception;
    Category findCategoryBySlug(String slug);
    List<Category> findAll();
    Category save(Category category);
    void delete(Category c);
    String assignColor();
    Category createCatrgory(String title, String description, String email);
    boolean userGotPermissions(String email, String slug);
}
