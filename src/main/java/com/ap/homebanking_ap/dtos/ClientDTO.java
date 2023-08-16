package com.ap.homebanking_ap.dtos;

import com.ap.homebanking_ap.models.Client;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private Set<AccountDTO> accounts;
    private Set<ClientLoanDTO> loans;
    public ClientDTO(Client client){
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts().stream().map
                (element -> new AccountDTO(element)).collect(Collectors.toSet());
        this.loans = client.getClientLoan().stream().map
                (clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO>getLoans() {
        return loans;
    }
}
