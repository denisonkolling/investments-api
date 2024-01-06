package com.example.investments.service.Impl;

import com.example.investments.model.User;
import com.example.investments.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UUID createUser(User user) {
        return null;
    }

    @Override
    public Optional<User> getUserById(String userId) {
        return Optional.empty();
    }

    @Override
    public List<User> listUsers() {
        return null;
    }

    @Override
    public void updateUserById(String userId, User User) {

    }

    @Override
    public void deleteById(String userId) {

    }
}
