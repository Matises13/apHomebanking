package com.ap.homebanking_ap.repositories;

import com.ap.homebanking_ap.models.Card;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase( replace = AutoConfigureTestDatabase.Replace.NONE)
class CardRepositoryTest {
    @Autowired
    CardRepository cardRepository;

    void existCard() {
        List<Card> cards = cardRepository.findAll();
        assertThat(cards,is(not(empty())));
    }

    @Test
    void numberNotNull() {
        List<Card> cards = cardRepository.findAll();
        assertThat(cards,hasItem(hasProperty("number",notNullValue())));
    }
}