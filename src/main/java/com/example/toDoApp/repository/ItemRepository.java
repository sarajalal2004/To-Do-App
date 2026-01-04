package com.example.toDoApp.repository;

import com.example.toDoApp.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByName(String itemName);
    List<Item> findByCategoryId(Long categoryId);
    Optional<Item> findByIdAndCategoryId(Long id, Long categoryId);
}
