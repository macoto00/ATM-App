package com.example.atmdemo.controllers;

import com.example.atmdemo.services.TransactionService;
import com.example.atmdemo.services.UserAccountService;
import com.example.atmdemo.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AtmController {

    private final UserService userService;
    private final TransactionService transactionService;
    private final UserAccountService userAccountService;

}
