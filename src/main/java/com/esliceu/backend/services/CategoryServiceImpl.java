package com.esliceu.backend.services;

import com.esliceu.backend.entities.Category;
import com.esliceu.backend.entities.User;
import com.esliceu.backend.repos.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

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

    @Override
    public void delete(Category c) {
        categoryRepository.delete(c);
    }

    @Override
    public String assignColor() {
        Random r = new Random();
        int color = r.nextInt((300 - 1) + 1) + 1;
        return "hsl(" + color + ", 50%, 50%)";
    }

    @Override
    public Category createCatrgory(String title, String description, User u) {
        Category c = new Category();
        c.setDescription(description);
        c.setTitle(title.replaceAll("\\s+","-"));
        c.setUser(u);
        c.setSlug(title.replaceAll("\\s+","-"));
        c.setColor(assignColor());
        return c;
    }
}
