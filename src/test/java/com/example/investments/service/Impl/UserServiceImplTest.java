package com.example.investments.service.Impl;

import com.example.investments.dto.UserRequestDTO;
import com.example.investments.model.User;
import com.example.investments.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Nested
    class createUser {

        @Test
        @DisplayName("Should create user with success")
        void shouldCreateUserWithSucess() {

            //Arrange

            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );

            doReturn(user).when(userRepository).save(any());

            var input = new UserRequestDTO(
                    "username",
                    "email@email.com",
                    "123"
            );

            //Act

            var output = userServiceImpl.createUser(input);

            //Assert
        }
    }

}