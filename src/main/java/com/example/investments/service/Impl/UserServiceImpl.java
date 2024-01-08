package com.example.investments.service.Impl;

import com.example.investments.dto.UserRequestDTO;
import com.example.investments.dto.UserUpdateDTO;
import com.example.investments.model.User;
import com.example.investments.repository.UserRepository;
import com.example.investments.service.UserService;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
    public UUID createUser(UserRequestDTO userRequestDTO) {

        var user = new User(
                UUID.randomUUID(),
                userRequestDTO.username(),
                userRequestDTO.email(),
                userRequestDTO.password(),
                Instant.now(),
                null);

        var userSaved = userRepository.save(user);

        return userSaved.getUserId();

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
    public void updateUserById(String userId, UserUpdateDTO userUpdateDTO) {

        var id = UUID.fromString(userId);

        var userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {

            var user = userEntity.get();

            if (userUpdateDTO.username() != null) {
                user.setUsername(userUpdateDTO.username());
            }

            if (userUpdateDTO.password() != null) {
                user.setPassword(userUpdateDTO.password());
            }

            userRepository.save(user);

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
