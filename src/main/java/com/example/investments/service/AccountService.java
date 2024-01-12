package com.example.investments.service;

import com.example.investments.dto.AccountStockRequestDTO;

import java.util.List;

public interface AccountService {

    void associateStock(String accountId, AccountStockRequestDTO accountStockRequestDTO);

    List listStock(String accountId);
}
