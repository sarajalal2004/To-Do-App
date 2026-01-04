package com.example.toDoApp.controller;

import com.example.toDoApp.model.User;
import com.example.toDoApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/users")
public class UserController {
    UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public User createUser(@RequestBody User userObject){
        System.out.println("Calling createUser ==> ");
        return userService.createUser(userObject);
    }

}
