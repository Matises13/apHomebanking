package com.ap.homebanking_ap.controllers;

import com.ap.homebanking_ap.dtos.LoanApplicationDTO;
import com.ap.homebanking_ap.dtos.LoanDTO;
import com.ap.homebanking_ap.models.*;
import com.ap.homebanking_ap.repositories.ClientLoanRepository;
import com.ap.homebanking_ap.services.AccountService;
import com.ap.homebanking_ap.services.ClientService;
import com.ap.homebanking_ap.services.LoanService;
import com.ap.homebanking_ap.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LoanApplicationController {
    @Autowired
    private LoanService loanService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @GetMapping("/loans")
    public List<LoanDTO> getLoans(){
        return loanService.getLoans();
    }
    @Transactional
    @RequestMapping(value = "/loans",method = RequestMethod.POST)
    ResponseEntity<Object> createdLoans (@RequestBody LoanApplicationDTO loanApplicationDTO,
                                         Authentication authentication){

        Long loanId = loanApplicationDTO.getLoanId();
        Double amount = loanApplicationDTO.getAmount();
        Integer payments = loanApplicationDTO.getPayments();
        String toAccountNumber = loanApplicationDTO.getToAccountNumber();

        Client clientAuth = clientService.getCurrentClient(authentication.getName());
        Account accountDestination = accountService.findByNumber(toAccountNumber);
        Loan loan = loanService.getLoanById(loanApplicationDTO.getLoanId());

        // Verificacion de datos correctos, no vacios, monto distinto de 0, cuotas distinta a 0.

        if (loanId == null){
            return new ResponseEntity<>("Missing data, loan is required", HttpStatus.FORBIDDEN);
        }

        if (amount <= 0){
            return new ResponseEntity<>("Amount can not be null",HttpStatus.FORBIDDEN);
        }

        if (payments <= 0){
            return new ResponseEntity<>("Payments can not be null", HttpStatus.FORBIDDEN);
        }

        if (toAccountNumber.isBlank()){
            return new ResponseEntity<>("Missing data, destination account is required",HttpStatus.FORBIDDEN);
        }

        // Verificacion existencia del prestamo

        if (!loanService.existsById(loanId)){
            return new ResponseEntity<>("This loan don't exists",HttpStatus.FORBIDDEN);
        }

        //Verificar que el monto solicitado no exceda el monto máximo del préstamo

        if (amount > loanService.getLoanById(loanId).getMaxAmount()){
            return new ResponseEntity<>("This amount is greater than what is allowed",HttpStatus.FORBIDDEN);
        }

        //Verifica que la cantidad de cuotas se encuentre entre las disponibles del préstamo

        if (loan.getPayments().equals(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("This numbers of payments is not available",HttpStatus.FORBIDDEN);
        }

        //Verificar que la cuenta de destino exista

        if (!accountService.existsByNumber(toAccountNumber)){
            return new ResponseEntity<>("This destination account don't exists",HttpStatus.FORBIDDEN);
        }

        //Verificar que la cuenta de destino pertenezca al cliente autenticado

        if (!clientAuth.getAccounts().contains(accountDestination)){
            System.out.println(clientAuth.getAccounts());
            return new ResponseEntity<>("This Account don't belong to authentication client",HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount() + amount*0.2, loanApplicationDTO.getPayments());
        Transaction transaction = new Transaction(TransactionType.CREDIT,amount,
                "Loan approved" + toAccountNumber, LocalDateTime.now());
        accountDestination.setBalance(accountDestination.getBalance() + amount);
        accountDestination.addTransaction(transaction);

        loan.addClientLoan(clientLoan);
        clientAuth.addClientLoan(clientLoan);

        clientLoanRepository.save(clientLoan);
        transactionService.createdTransaction(transaction);
        accountService.createdAccount(accountDestination);

        return new ResponseEntity<>("Loan approved", HttpStatus.CREATED);
    }
}
