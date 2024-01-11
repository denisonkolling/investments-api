package com.example.investments.service;

import com.example.investments.dto.AccountStockRequest;

public interface AccountService {

    void associateStock(String accountId, AccountStockRequest accountStockRequest);
}
