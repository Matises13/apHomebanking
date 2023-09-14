package com.ap.homebanking_ap.services.implement;

import com.ap.homebanking_ap.dtos.TransactionDTO;
import com.ap.homebanking_ap.models.Transaction;
import com.ap.homebanking_ap.repositories.TransactionRepository;
import com.ap.homebanking_ap.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImplement implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Set<TransactionDTO> getTransactions() {
        return transactionRepository.findAll().stream().map(TransactionDTO::new).collect(Collectors.toSet());
    }

    @Override
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Override
    public void createdTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

}
