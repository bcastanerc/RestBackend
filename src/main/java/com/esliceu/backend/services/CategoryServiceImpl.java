package com.esliceu.backend.services;

import com.esliceu.backend.entities.Category;
import com.esliceu.backend.repos.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category findById(Long category_id) throws Exception{
        return categoryRepository.findById(category_id).get();
    }

    @Override
    public Category findCategoryBySlug(String slug) {
        return categoryRepository.findCategoryBySlug(slug);
    }

    @Override
    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }
}
