package com.example.investments.service.Impl;

import com.example.investments.dto.AccountStockRequestDTO;
import com.example.investments.dto.AccountStockResponseDTO;
import com.example.investments.model.AccountStock;
import com.example.investments.model.AccountStockId;
import com.example.investments.repository.AccountRepository;
import com.example.investments.repository.AccountStockRepository;
import com.example.investments.repository.StockRepository;
import com.example.investments.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private AccountStockRepository accountStockRepository;
    private StockRepository stockRepository;

    public AccountServiceImpl(AccountRepository accountRepository, AccountStockRepository accountStockRepository, StockRepository stockRepository) {
        this.accountRepository = accountRepository;
        this.accountStockRepository = accountStockRepository;
        this.stockRepository = stockRepository;
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
                                0D
                        ))
                .toList();
    }
}
