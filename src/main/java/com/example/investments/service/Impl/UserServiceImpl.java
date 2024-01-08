package com.example.investments.service.Impl;

import com.example.investments.model.User;
import com.example.investments.repository.UserRepository;
import com.example.investments.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId));
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public void updateUserById(String userId, User user) {

        var id = UUID.fromString(userId);

        var userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {

            var userData = userEntity.get();

            if (userData.getUsername() != null) {
                user.setUsername(userData.getUsername());
            }

            if (user.getPassword() != null) {
                user.setPassword(user.getPassword());
            }

            userRepository.save(userData);

        }

    }

    @Override
    public void deleteById(String userId) {

        var id = UUID.fromString(userId);

        var userExists = userRepository.existsById(id);

        if (userExists) {
            userRepository.deleteById(id);
        }

    }
}
