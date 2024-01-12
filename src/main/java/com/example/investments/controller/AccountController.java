package com.example.investments.controller;


import com.example.investments.dto.AccountStockRequestDTO;
import com.example.investments.dto.AccountStockResponseDTO;
import com.example.investments.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable("accountId") String accountId,
                                               @RequestBody AccountStockRequestDTO accountStockRequestDTO) {

        accountService.associateStock(accountId, accountStockRequestDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockResponseDTO>> listStock(@PathVariable("accountId") String accountId) {

        var stockList = accountService.listStock(accountId);

        return ResponseEntity.ok(stockList);
    }

}
