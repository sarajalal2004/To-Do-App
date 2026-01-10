package com.example.toDoApp.service;

import com.example.toDoApp.exception.InformationExistException;
import com.example.toDoApp.exception.InformationNotFoundException;
import com.example.toDoApp.model.Category;
import com.example.toDoApp.model.Item;
import com.example.toDoApp.model.User;
import com.example.toDoApp.repository.CategoryRepository;
import com.example.toDoApp.repository.ItemRepository;
import com.example.toDoApp.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public static User getCurrentLoggedInUser(){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return myUserDetails.getUser();
    }

    public List<Item> getItems(Long categoryId){
        Category category = categoryRepository.findByIdAndUserId(categoryId, getCurrentLoggedInUser().getId()).orElseThrow(() -> new InformationNotFoundException("No category exist with " + categoryId));
        return category.getItemList();
    }

    public Item getItem(Long categoryId, Long itemId){
        Item item = itemRepository.findByIdAndCategoryIdAndUserId(itemId, categoryId, getCurrentLoggedInUser().getId()).orElseThrow(() -> new InformationNotFoundException("Item with id " + itemId + " not found in category " + categoryId + " for user" + getCurrentLoggedInUser().getUsername()));
        return item;
    }

    public Item createItem(Long categoryId, Item item){
        Optional<Item> itemObj = itemRepository.findByNameAndUserId(item.getName(), getCurrentLoggedInUser().getId());
        Category category = categoryRepository.findByIdAndUserId(categoryId, getCurrentLoggedInUser().getId()).orElseThrow(() -> new InformationNotFoundException("No category exist with " + categoryId + " for user " + getCurrentLoggedInUser().getUsername()));
        if(itemObj.isPresent() && itemRepository.findByIdAndCategoryIdAndUserId(itemObj.get().getId(), categoryId, getCurrentLoggedInUser().getId()).isPresent()){
            throw new InformationExistException("Item with name " + item.getName() + "is already exist in category with id " + categoryId + " for user " + getCurrentLoggedInUser().getUsername());
        }
        item.setUser(getCurrentLoggedInUser());
        item.setCategory(category);
        return itemRepository.save(item);
    }

    public Item updateItem(Long categoryId, Long itemId, Item item){
        Item itemObj = itemRepository.findByIdAndCategoryIdAndUserId(itemId, categoryId, getCurrentLoggedInUser().getId()).orElseThrow(() -> new  InformationNotFoundException("Item with id " + itemId + " not found in category " + categoryId + " for user "+ getCurrentLoggedInUser().getUsername()));
        itemObj.setName(item.getName());
        itemObj.setDescription(item.getDescription());
        itemObj.setDueDate(item.getDueDate());
        return itemRepository.save(itemObj);
    }

    public Item deleteItem(Long categoryId, Long itemId){
        Item itemObj = itemRepository.findByIdAndCategoryIdAndUserId(itemId, categoryId, getCurrentLoggedInUser().getId()).orElseThrow(() -> new  InformationNotFoundException("Item with id " + itemId + " not found in category " + categoryId + " for user " + getCurrentLoggedInUser().getUsername()));
        itemRepository.deleteById(itemId);
        return itemObj;
    }

}
