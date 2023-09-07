package com.ap.homebanking_ap.controllers;

import com.ap.homebanking_ap.dtos.ClientDTO;
import com.ap.homebanking_ap.models.Account;
import com.ap.homebanking_ap.models.Client;
import com.ap.homebanking_ap.repositories.AccountRepository;
import com.ap.homebanking_ap.repositories.ClientRepository;
import com.ap.homebanking_ap.utils.AccountUtils;
import com.ap.homebanking_ap.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/clients")
    private List<ClientDTO> getClients(){
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }
    @RequestMapping("/clients/{id}")
    private ClientDTO getId(@PathVariable Long id){
        return new ClientDTO(clientRepository.findById(id).orElse(null));
    }

    @RequestMapping(value = "/clients/current", method = RequestMethod.GET)
    public ClientDTO getCurrent (Authentication authentication){
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }
    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password){

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()){
            return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email) != null){
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }
        Account account = null;
        do {
            String number = "VIN" + AccountUtils.getRandomNumberAccount(100000000,1000000);
            account = new Account(number,LocalDate.now(),0.0);
        }
        while (accountRepository.existsByNumber(account.getNumber()));

        String numberCard = CardUtils.getRandomNumberCard();

        Integer cvv = CardUtils.getRandomNumberCvv(0,999);

        Client clientRegistered = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientRegistered.addAccount(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}



