package com.example.investments.service;

import com.example.investments.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UUID createUser(User user);

    Optional<User> getUserById(String userId);

    List<User> listUsers();

    void updateUserById(String userId, User User);

    void deleteById(String userId);

}

