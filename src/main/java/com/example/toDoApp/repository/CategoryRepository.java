package com.example.toDoApp.repository;

import com.example.toDoApp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String categoryName);
    Optional<Category> findByIdAndUserId(Long categoryId, Long userId);
    List<Category> findByUserId(Long userId);
    Optional<Category> findByUserIdAndName(Long userId, String categoryName);
}
