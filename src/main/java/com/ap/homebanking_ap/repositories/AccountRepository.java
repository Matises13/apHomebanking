package com.ap.homebanking_ap.repositories;

import com.ap.homebanking_ap.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account,Long> {
    boolean existsByNumber (String number);
}
