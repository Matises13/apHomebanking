package com.ap.homebanking_ap.services;

import com.ap.homebanking_ap.dtos.LoanDTO;
import com.ap.homebanking_ap.models.Loan;

import java.util.List;

public interface LoanService {
    List<LoanDTO> getLoans();

    Loan getLoanById (Long id);

    void createdLoans (Loan loan);

    boolean existsById(Long loanId);
}
