package com.example.toDoApp.controller;

import com.example.toDoApp.model.Item;
import com.example.toDoApp.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ItemController {
    ItemService itemService;

    @Autowired
    public void setItemService(ItemService itemService){
        this.itemService = itemService;
    }

    @GetMapping(path = "/categories/{categoryId}/items")
    public List<Item> getItems(@PathVariable("categoryId") Long categoryId){
        return itemService.getItems(categoryId);
    }

    @GetMapping(path = "/categories/{categoryId}/items/{itemId}")
    public Item getItem(@PathVariable("categoryId") Long categoryId, @PathVariable("itemId") Long itemId){
        return itemService.getItem(categoryId, itemId);
    }

    @PostMapping(path = "/categories/{categoryId}/items")
    public Item createItem(@PathVariable("categoryId") Long categoryId, @RequestBody Item item){
        return itemService.createItem(categoryId, item);
    }

    @PutMapping(path = "/categories/{categoryId}/items/{itemId}")
    public Item updateItem(@PathVariable("categoryId") Long categoryId, @PathVariable("itemId") Long itemId, @RequestBody Item item){
        return itemService.updateItem(categoryId, itemId, item);
    }

    @DeleteMapping(path = "/categories/{categoryId}/items/{itemId}")
    public Item deleteItem(@PathVariable("categoryId") Long categoryId, @PathVariable("itemId") Long itemId){
        return itemService.deleteItem(categoryId, itemId);
    }
}
