package com.example.investments.service.Impl;

import com.example.investments.client.BrapiClient;
import com.example.investments.dto.AccountStockRequestDTO;
import com.example.investments.dto.AccountStockResponseDTO;
import com.example.investments.model.AccountStock;
import com.example.investments.model.AccountStockId;
import com.example.investments.repository.AccountRepository;
import com.example.investments.repository.AccountStockRepository;
import com.example.investments.repository.StockRepository;
import com.example.investments.service.AccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    @Value("#{environment.BRAPI_SECRET_KEY}")
    private String TOKEN;

    private AccountRepository accountRepository;
    private AccountStockRepository accountStockRepository;
    private StockRepository stockRepository;
    private BrapiClient brapiClient;

    public AccountServiceImpl(AccountRepository accountRepository, AccountStockRepository accountStockRepository, StockRepository stockRepository, BrapiClient brapiClient) {
        this.accountRepository = accountRepository;
        this.accountStockRepository = accountStockRepository;
        this.stockRepository = stockRepository;
        this.brapiClient = brapiClient;
    }

    @Override
    public void associateStock(String accountId, AccountStockRequestDTO accountStockRequestDTO) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var stock = stockRepository.findById(accountStockRequestDTO.stockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var id = new AccountStockId(account.getAccountId(), stock.getStockId());
        var entity = new AccountStock(
                id,
                account,
                stock,
                accountStockRequestDTO.quantity()
        );

        accountStockRepository.save(entity);
    }

    @Override
    public List listStock(String accountId) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return account.getAccountStocks()
                .stream()
                .map(accountStock ->
                        new AccountStockResponseDTO(
                                accountStock.getStock().getStockId(),
                                accountStock.getQuantity(),
                                getTotal(accountStock.getQuantity(), accountStock.getStock().getStockId())
                        ))
                .toList();
    }

    private double getTotal(Integer quantity, String stockId) {

        var response = brapiClient.getPrice(TOKEN, stockId);

        var price = response.results().getFirst().regularMarketPrice();

        return quantity * price;
    }
}
