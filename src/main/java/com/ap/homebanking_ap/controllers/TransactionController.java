package com.ap.homebanking_ap.controllers;

import com.ap.homebanking_ap.dtos.ClientDTO;
import com.ap.homebanking_ap.dtos.TransactionDTO;
import com.ap.homebanking_ap.models.Account;
import com.ap.homebanking_ap.models.Client;
import com.ap.homebanking_ap.models.Transaction;
import com.ap.homebanking_ap.models.TransactionType;
import com.ap.homebanking_ap.repositories.AccountRepository;
import com.ap.homebanking_ap.repositories.ClientRepository;
import com.ap.homebanking_ap.repositories.TransactionRepository;
import com.ap.homebanking_ap.services.AccountService;
import com.ap.homebanking_ap.services.ClientService;
import com.ap.homebanking_ap.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @RequestMapping("/transaction")
    private Set<TransactionDTO> getTransactions(){
        return transactionService.getTransactions();
    }
    @RequestMapping("/transaction/{id}")
    private TransactionDTO getTransactions(@PathVariable Long id) {
        return new TransactionDTO(transactionService.getTransactionById(id));
    }
    @Transactional
    @RequestMapping (value = "/transactions",method = RequestMethod.POST)
    public ResponseEntity<Object> createdTransaction (
            @RequestParam Double amount, @RequestParam String description,
            @RequestParam String  fromAccountNumber, @RequestParam String toAccountNumber, Authentication authentication
    ) {
        Client clientAuth = clientService.getCurrentClient(authentication.getName());
        Account accountSource = accountService.findByNumber(fromAccountNumber);
        Account accountDestination = accountService.findByNumber(toAccountNumber);

        if (amount == null) {
            return new ResponseEntity<>("Missing Data, amount is required", HttpStatus.FORBIDDEN);
        }

        if (description.isBlank()) {
            return new ResponseEntity<>("Missing Data, description is required", HttpStatus.FORBIDDEN);
        }

        if (fromAccountNumber.isBlank()) {
            return new ResponseEntity<>("Missing Data, source account is required", HttpStatus.FORBIDDEN);
        }

        if (toAccountNumber.isBlank()) {
            return new ResponseEntity<>("Missing Data, destination account is required", HttpStatus.FORBIDDEN);
        }

        // Verificación de N° de cuentas no iguales.

        if (fromAccountNumber.equals(toAccountNumber)) {
            return new ResponseEntity<>("You are not allowed to perfom this operation", HttpStatus.FORBIDDEN);
        }

        // Verificación de existencia de cuenta origen.

        if (!accountService.existsByNumber(fromAccountNumber)) {
            return new ResponseEntity<>("Source account don't exists", HttpStatus.FORBIDDEN);
        }

        // Verficicacíon de cuenta origen de cliente autenticado

        if (!clientAuth.getAccounts().contains(accountSource)) {
            return new ResponseEntity<>("The source account does not belong to the authenticated client", HttpStatus.FORBIDDEN);
        }

        // Verificacion de existencia de la cuenta destino

        if (!accountService.existsByNumber(toAccountNumber)) {
            return new ResponseEntity<>("Account destination don't exists", HttpStatus.FORBIDDEN);
        }

        //Verificación que la cuenta origen tenga saldo suficiente

        if (accountSource.getBalance() < amount) {
            return new ResponseEntity<>("Insufificient funds", HttpStatus.FORBIDDEN);
        }

        // Creacion de tipos de transacciones

        // Debit Transaction
        Transaction transactionDebit = new Transaction(TransactionType.DEBIT, -amount, description + "DEBIT - " + fromAccountNumber,
                LocalDateTime.now());
        accountSource.addTransaction(transactionDebit);
        accountSource.setBalance(accountSource.getBalance() - amount);
        transactionService.createdTransaction(transactionDebit);
        accountService.createdAccount(accountSource);

        // Credit Transaction
        Transaction transactionCredit = new Transaction(TransactionType.CREDIT,amount,description + "CREDIT" + toAccountNumber, LocalDateTime.now());
        accountDestination.setBalance(accountDestination.getBalance() + amount);
        accountDestination.addTransaction(transactionCredit);
        transactionService.createdTransaction(transactionCredit);
        accountService.createdAccount(accountDestination);

        return new ResponseEntity<>(HttpStatus.CREATED);


    }
}
