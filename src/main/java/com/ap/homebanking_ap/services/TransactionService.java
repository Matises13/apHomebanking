package com.ap.homebanking_ap.services;

import com.ap.homebanking_ap.dtos.TransactionDTO;
import com.ap.homebanking_ap.models.Transaction;

import java.util.List;
import java.util.Set;

public interface TransactionService {
    Set<TransactionDTO> getTransactions();

    Transaction getTransactionById (Long id);

    void createdTransaction (Transaction transaction);

}
