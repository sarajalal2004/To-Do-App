package com.example.toDoApp.repository;

import com.example.toDoApp.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByNameAndUserId(String itemName, Long userId);
    Optional<Item> findByIdAndCategoryIdAndUserId(Long id, Long categoryId, Long userId);
}
