package com.example.toDoApp.service;

import com.example.toDoApp.exception.InformationExistException;
import com.example.toDoApp.exception.InformationNotFoundException;
import com.example.toDoApp.model.Category;
import com.example.toDoApp.model.User;
import com.example.toDoApp.repository.CategoryRepository;
import com.example.toDoApp.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public static User getCurrentLoggedInUser(){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return myUserDetails.getUser();
    }

    public List<Category> getCategories(){
        List<Category> categories = categoryRepository.findByUserId(getCurrentLoggedInUser().getId());
        if(categories.isEmpty()){
            throw new InformationNotFoundException("no categories found for user id " + getCurrentLoggedInUser().getId());
        }else {
            return categories;
        }
    }

    public Category getCategory(Long categoryId){
        return categoryRepository.findByIdAndUserId(categoryId, getCurrentLoggedInUser().getId())
                .orElseThrow(() -> new InformationNotFoundException("No category exist with id " + categoryId));
    }

    public Category createCategory(Category category){
        if(categoryRepository.findByUserIdAndName(getCurrentLoggedInUser().getId(), category.getName()).isPresent()){
            throw new InformationExistException("Category with name "+ category.getName() + " already exist");
        }else {
            category.setUser(getCurrentLoggedInUser());
            return categoryRepository.save(category);
        }
    }

    public Category updateCategory(Long categoryId, Category category){
        Category categoryObject = categoryRepository.findByIdAndUserId(categoryId, getCurrentLoggedInUser().getId()).orElseThrow(() -> new InformationNotFoundException("No category exist with id " + categoryId));
        category.setId(categoryObject.getId());
        category.setCreatedAt(categoryObject.getCreatedAt());
        category.setUser(getCurrentLoggedInUser());
        return categoryRepository.save(category);
    }

    public Category deleteCategory(Long categoryId){
        Category categoryObject = categoryRepository.findByIdAndUserId(categoryId, getCurrentLoggedInUser().getId()).orElseThrow(() -> new InformationNotFoundException("No category exist with id " + categoryId));
        categoryRepository.deleteById(categoryObject.getId());
        return categoryObject;
    }



}
