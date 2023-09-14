package com.ap.homebanking_ap.services;

import com.ap.homebanking_ap.dtos.ClientDTO;
import com.ap.homebanking_ap.models.Client;

import java.util.List;
import java.util.Set;

public interface ClientService {
    List<ClientDTO> getClients();

    Client getClientById (Long id);

    Client getCurrentClient (String email);

    void saveClient (Client client);
}
