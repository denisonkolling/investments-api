package com.example.investments.service.Impl;

import com.example.investments.dto.UserRequestDTO;
import com.example.investments.model.User;
import com.example.investments.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

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

            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());

            var input = new UserRequestDTO(
                    "username",
                    "email@email.com",
                    "123"
            );

            //Act

            var output = userServiceImpl.createUser(input);

            //Assert

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(input.username(), userCaptured.getUsername());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());
        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        void shouldThrowExceptionWhenErrorOccurs() {

            // Arrange

            doThrow(new RuntimeException()).when(userRepository).save(any());

            var input = new UserRequestDTO(
                    "username",
                    "email@email.com",
                    "123"
            );

            // Act & Assert
            assertThrows(RuntimeException.class, () -> userServiceImpl.createUser(input));
        }
    }

    @Nested
    class getUserById {
        @Test
        @DisplayName("Return user by id with success")
        void getUserByIdWithSuccess() {

            // Arrange

            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );


            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            // Act
            var output = userServiceImpl.getUserById(user.getUserId().toString());

            // Assert

            assertTrue(output.isPresent());
            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());

        }


    }

    @Nested
    class listUsers {

        @Test
        @DisplayName("Return all users with success")
        void returnAllUsersWithSucess() {

            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );

            var userList = List.of(user);

            doReturn(userList)
                    .when(userRepository)
                    .findAll();

            var output = userServiceImpl.listUsers();

            assertNotNull(output);
            assertEquals(userList.size(), output.size());


        }
    }
}