package com.example.investments.service.Impl;

import com.example.investments.dto.AccountRequestDTO;
import com.example.investments.dto.AccountResponseDTO;
import com.example.investments.dto.UserRequestDTO;
import com.example.investments.dto.UserUpdateDTO;
import com.example.investments.model.Account;
import com.example.investments.model.BillingAddress;
import com.example.investments.model.User;
import com.example.investments.repository.AccountRepository;
import com.example.investments.repository.BillingAddressRepository;
import com.example.investments.repository.UserRepository;
import com.example.investments.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private AccountRepository accountRepository;

    private BillingAddressRepository billingAddressRepository;

    public UserServiceImpl(UserRepository userRepository, AccountRepository accountRepository, BillingAddressRepository billingAddressRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAddressRepository = billingAddressRepository;
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

    @Override
    public void createAccount(String userId, AccountRequestDTO accountRequestDTO) {

        var user = userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var account = new Account(
                UUID.randomUUID(),
                user,
                null,
                accountRequestDTO.description(),
                new ArrayList<>()
        );

        var accountCreated = accountRepository.save(account);

        var billingAddress = new BillingAddress(
                accountCreated.getAccountId(),
                account,
                accountRequestDTO.street(),
                accountRequestDTO.number(),
                accountRequestDTO.city()
        );

        billingAddressRepository.save(billingAddress);

    }

    @Override
    public List<AccountResponseDTO> listAccounts(String userId) {

        var user = userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return user.getAccounts()
                .stream()
                .map(account ->
                        new AccountResponseDTO(account.getAccountId().toString(), account.getDescription()))
                .toList();
    }
}
