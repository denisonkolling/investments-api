package com.example.investments.service;

import com.example.investments.dto.UserRequestDTO;
import com.example.investments.dto.UserUpdateDTO;
import com.example.investments.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UUID createUser(UserRequestDTO user);

    Optional<User> getUserById(String userId);

    List<User> listUsers();

    void updateUserById(String userId, UserUpdateDTO User);

    void deleteById(String userId);

}

