package com.ap.homebanking_ap.controllers;

import com.ap.homebanking_ap.dtos.AccountDTO;
import com.ap.homebanking_ap.dtos.ClientDTO;
import com.ap.homebanking_ap.models.Account;
import com.ap.homebanking_ap.models.Client;
import com.ap.homebanking_ap.repositories.AccountRepository;
import com.ap.homebanking_ap.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ap.homebanking_ap.utils.AccountUtils.getRandomNumberAccount;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/accounts")
    public Set<AccountDTO>getAccounts(){
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
    }

    @RequestMapping("/accounts/{id}")
    private AccountDTO getId(@PathVariable Long id){
        return accountRepository.findById(id).map(account -> new AccountDTO(account)).orElse(null);
    }

    @RequestMapping(value = "/clients/current/accounts", method = RequestMethod.POST)

    public ResponseEntity<Object> createdAccount (Authentication authentication){
        Client clientAuth = clientRepository.findByEmail(authentication.getName());
        if (clientAuth.getAccounts().stream().count()==3){
            System.out.println("Tiene 3 cuentas, alcanzo el maximo");
            return new ResponseEntity<>("Already max number accounts", HttpStatus.FORBIDDEN);
        }

        Account account = null;
        do{
            String number = "VIN" + getRandomNumberAccount(10000000,99999999);
            account = new Account(number, LocalDate.now(), 0.0);
        }
        while(accountRepository.existsByNumber(account.getNumber()));

        clientAuth.addAccount(account);
        accountRepository.save(account);
        System.out.println("Creaste una cuenta");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
