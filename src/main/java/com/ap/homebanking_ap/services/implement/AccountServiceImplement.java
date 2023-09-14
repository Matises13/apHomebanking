package com.ap.homebanking_ap.services.implement;

import com.ap.homebanking_ap.dtos.AccountDTO;
import com.ap.homebanking_ap.models.Account;
import com.ap.homebanking_ap.repositories.AccountRepository;
import com.ap.homebanking_ap.repositories.ClientRepository;
import com.ap.homebanking_ap.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplement implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(Collectors.toList());
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public boolean existsByNumber(String number) {
        return accountRepository.existsByNumber(number);
    }


    @Override
    public List<AccountDTO> getCurrentAccount(Authentication authentication) {
        return clientRepository.findByEmail(authentication.getName()).getAccounts().stream().
                map(AccountDTO::new).collect(Collectors.toList());
    }

    @Override
    public void createdAccount(Account account) {
        accountRepository.save(account);
    }
}
