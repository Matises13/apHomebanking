package com.ap.homebanking_ap.controllers;

import com.ap.homebanking_ap.dtos.ClientDTO;
import com.ap.homebanking_ap.models.Client;
import com.ap.homebanking_ap.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/clients")
    private List<ClientDTO> getClients(){
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }
    @RequestMapping("/clients/{id}")
    private ClientDTO getId(@PathVariable Long id){
        return new ClientDTO(clientRepository.findById(id).orElse(null));
    }
}
