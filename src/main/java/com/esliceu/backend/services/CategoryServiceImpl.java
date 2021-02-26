package com.esliceu.backend.services;

import com.esliceu.backend.entities.Category;
import com.esliceu.backend.entities.User;
import com.esliceu.backend.repos.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserService userService;

    @Override
    public Category findById(Long category_id) throws Exception {
        Optional<Category> c = categoryRepository.findById(category_id);
        return c.get();
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
    public Category save(Category category) {
        categoryRepository.save(category);
        return category;
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
    public Category createCatrgory(String title, String description, String email) {
        String slug = title.replaceAll("\\s+","-");
        if (findCategoryBySlug(slug) !=null) slug = slugGenerator(slug);
        Category c = new Category();
        c.setDescription(description);
        c.setTitle(title.replaceAll("\\s+","-"));
        c.setUser(userService.findUserByEmailEquals(email));
        c.setSlug(slug);
        c.setColor(assignColor());
        return c;
    }

    @Override
    public boolean userGotPermissions(String email, String slug) {
        User u = userService.findUserByEmailEquals(email);
        if (u.getRole().equals("addmin")) return true;
        return u.getRole().equals("moderator") && u.getCategory().getSlug().equals(slug);
    }

    public String slugGenerator(String slug){
        Category c = new Category();
        int id = 0;
        while(c != null) {
            id++;
            c = findCategoryBySlug(slug+"-"+id);
        }
        return slug+"-"+id;
    }
}
