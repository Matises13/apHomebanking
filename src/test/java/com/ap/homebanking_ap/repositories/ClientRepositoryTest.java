package com.ap.homebanking_ap.repositories;

import com.ap.homebanking_ap.models.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClientRepositoryTest {
    @Autowired
    ClientRepository clientRepository;
    @Test
    public void emailNotNull(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, hasItem(hasProperty("email", notNullValue())));
    }
    @Test
    public void exitsClients (){
        List <Client> clients = clientRepository.findAll();
        assertThat(clients,is(not(empty())));
    }
}