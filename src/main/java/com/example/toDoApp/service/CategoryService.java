package com.example.toDoApp.service;

import com.example.toDoApp.exception.InformationExistException;
import com.example.toDoApp.exception.InformationNotFoundException;
import com.example.toDoApp.model.Category;
import com.example.toDoApp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public Category getCategory(Long categoryId){
        return categoryRepository.findById(categoryId).orElseThrow(() -> new InformationNotFoundException("No category exist with id " + categoryId));
    }

    public Category createCategory(Category category){
        if(categoryRepository.findByName(category.getName()).isPresent()){
            throw new InformationExistException("Category with name "+ category.getName() + " already exist");
        }else {
            return categoryRepository.save(category);
        }
    }

    public Category updateCategory(Long categoryId, Category category){
        Category categoryObject = categoryRepository.findById(categoryId).orElseThrow(() -> new InformationNotFoundException("No category exist with id " + categoryId));
        category.setId(categoryObject.getId());
        category.setCreatedAt(categoryObject.getCreatedAt());
        return categoryRepository.save(category);
    }

    public Category deleteCategory(Long categoryId){
        Category categoryObject = categoryRepository.findById(categoryId).orElseThrow(() -> new InformationNotFoundException("No category exist with id " + categoryId));
        categoryRepository.deleteById(categoryObject.getId());
        return categoryObject;
    }



}
