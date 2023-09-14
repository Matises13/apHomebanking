package com.ap.homebanking_ap.controllers;

import com.ap.homebanking_ap.dtos.AccountDTO;
import com.ap.homebanking_ap.dtos.ClientDTO;
import com.ap.homebanking_ap.models.Account;
import com.ap.homebanking_ap.models.Client;
import com.ap.homebanking_ap.repositories.AccountRepository;
import com.ap.homebanking_ap.repositories.ClientRepository;
import com.ap.homebanking_ap.services.AccountService;
import com.ap.homebanking_ap.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ap.homebanking_ap.utils.AccountUtils.getRandomNumberAccount;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    @RequestMapping("/accounts")
    public List<AccountDTO>getAccounts(){
        return accountService.getAccounts();
    }

    @RequestMapping("/accounts/{id}")
    private AccountDTO getAccount(@PathVariable Long id){
        return new AccountDTO(accountService.getAccountById(id));
    }
    @RequestMapping("/clients/current/accounts")
    public List<AccountDTO> getCurrentAccount (Authentication authentication){
        return accountService.getCurrentAccount(authentication);
    }

    @RequestMapping(value = "/clients/current/accounts", method = RequestMethod.POST)

    public ResponseEntity<Object> createdAccount (Authentication authentication){
        //Client clientAuth = clientRepository.findByEmail(authentication.getName());
        if (clientService.getCurrentClient(authentication.getName()).getAccounts().stream().count()==3){
            System.out.println("Tiene 3 cuentas, alcanzo el maximo");
            return new ResponseEntity<>("Already max number accounts", HttpStatus.FORBIDDEN);
        }

        Client clientAuth = clientService.getCurrentClient(authentication.getName());
        Account account = null;
        do{
            String number = "VIN" + getRandomNumberAccount(10000000,99999999);
            account = new Account(number, LocalDate.now(), 0.0);
        }
        while(accountService.existsByNumber(account.getNumber()));

        clientAuth.addAccount(account);
        accountService.createdAccount(account);
        System.out.println("Creaste una cuenta");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
