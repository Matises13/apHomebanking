package com.ap.homebanking_ap.repositories;

import com.ap.homebanking_ap.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LoanRepository extends JpaRepository<Loan,Long> {

}
