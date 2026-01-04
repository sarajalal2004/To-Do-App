package com.example.toDoApp.repository;

import com.example.toDoApp.model.Category;
import com.example.toDoApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailAddress(String userEmailAddress);
    User findUserByEmailAddress(String EmailAddress);
}
