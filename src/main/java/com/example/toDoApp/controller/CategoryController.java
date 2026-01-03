package com.example.toDoApp.controller;

import com.example.toDoApp.model.Category;
import com.example.toDoApp.repository.CategoryRepository;
import com.example.toDoApp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class CategoryController {
    CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("/categories/")
    public List<Category> getCategories(){
        return categoryService.getCategories();
    }

    @GetMapping(path = "/categories/{categoryId}")
    public Category getCategories(@PathVariable("categoryId") Long categoryId){
        return categoryService.getCategory(categoryId);
    }

    @PostMapping("/categories/")
    public Category createCategory(@RequestBody Category category){
        return categoryService.createCategory(category);
    }

    @PutMapping(path = "/categories/{categoryId}")
    public Category updateCategory(@PathVariable("categoryId") Long categoryId, @RequestBody Category category){
        return categoryService.updateCategory(categoryId, category);
    }

    @DeleteMapping(path = "/categories/{categoryId}")
    public Category deleteCategory(@PathVariable("categoryId") Long categoryId){
        return categoryService.deleteCategory(categoryId);
    }
}
