package com.ap.homebanking_ap.repositories;

import com.ap.homebanking_ap.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientLoanRepository extends JpaRepository <ClientLoan,Long> {
}
