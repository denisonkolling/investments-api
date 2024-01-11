package com.example.investments.controller;


import com.example.investments.dto.AccountStockRequest;
import com.example.investments.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable("accountId") String accountId,
                                               @RequestBody AccountStockRequest accountStockRequest) {

        accountService.associateStock(accountId, accountStockRequest);

        return ResponseEntity.ok().build();
    }


}
