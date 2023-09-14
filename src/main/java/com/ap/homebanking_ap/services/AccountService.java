package com.ap.homebanking_ap.services;

import com.ap.homebanking_ap.dtos.AccountDTO;
import com.ap.homebanking_ap.models.Account;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Set;

public interface AccountService{
    List<AccountDTO> getAccounts();

    Account getAccountById(Long id);

    Account findByNumber(String number);

    boolean existsByNumber(String number);

    List<AccountDTO> getCurrentAccount(Authentication authentication);

    void createdAccount(Account account);
}
