package com.ap.homebanking_ap.controllers;

import com.ap.homebanking_ap.dtos.AccountDTO;
import com.ap.homebanking_ap.dtos.ClientDTO;
import com.ap.homebanking_ap.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/accounts")
    public Set<AccountDTO>getAccounts(){
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
    }

    @RequestMapping("/accounts/{id}")
    private AccountDTO getId(@PathVariable Long id){
        return accountRepository.findById(id).map(account -> new AccountDTO(account)).orElse(null);

                // new AccountDTO(accountRepository.findById(id).orElse(null));
    }
}
