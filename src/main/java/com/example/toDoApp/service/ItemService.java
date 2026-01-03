package com.example.toDoApp.service;

import com.example.toDoApp.exception.InformationExistException;
import com.example.toDoApp.exception.InformationNotFoundException;
import com.example.toDoApp.model.Category;
import com.example.toDoApp.model.Item;
import com.example.toDoApp.repository.CategoryRepository;
import com.example.toDoApp.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    ItemRepository itemRepository;
    CategoryRepository categoryRepository;

    @Autowired
    public void setItemRepository(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Item> getItems(Long categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new InformationNotFoundException("No category exist with " + categoryId));
        return category.getItemList();
    }

    public Item getItem(Long categoryId, Long itemId){
        Item item = itemRepository.findByIdAndCategoryId(itemId, categoryId).orElseThrow(() -> new InformationNotFoundException("Item with id " + itemId + " not found in category " + categoryId));
        return item;
    }

    public Item createItem(Long categoryId, Item item){
        Optional<Item> itemObj = itemRepository.findByName(item.getName());
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new InformationNotFoundException("No category exist with " + categoryId));
        if(itemObj.isPresent() && itemRepository.findByIdAndCategoryId(itemObj.get().getId(), categoryId).isPresent()){
            throw new InformationExistException("Item with name " + item.getName() + "is already exist in category with id " + categoryId);
        }
        item.setCategory(category);
        return itemRepository.save(item);
    }

    public Item updateItem(Long categoryId, Long itemId, Item item){
        Item itemObj = itemRepository.findByIdAndCategoryId(itemId, categoryId).orElseThrow(() -> new  InformationNotFoundException("Item with id " + itemId + " not found in category " + categoryId));
        itemObj.setName(item.getName());
        itemObj.setDescription(item.getDescription());
        itemObj.setDueDate(item.getDueDate());
        return itemRepository.save(itemObj);
    }

    public Item deleteItem(Long categoryId, Long itemId){
        Item itemObj = itemRepository.findByIdAndCategoryId(itemId, categoryId).orElseThrow(() -> new  InformationNotFoundException("Item with id " + itemId + " not found in category " + categoryId));
        itemRepository.deleteById(itemId);
        return itemObj;
    }

}
