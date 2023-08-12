package com.ap.homebanking_ap.repositories;

import com.ap.homebanking_ap.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
