package com.ap.homebanking_ap.controllers;

import com.ap.homebanking_ap.dtos.CardDTO;
import com.ap.homebanking_ap.models.Card;
import com.ap.homebanking_ap.models.CardType;
import com.ap.homebanking_ap.models.Client;
import com.ap.homebanking_ap.models.CardColor;
import com.ap.homebanking_ap.repositories.CardRepository;
import com.ap.homebanking_ap.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.ap.homebanking_ap.utils.CardUtils.getRandomNumberCard;
import static com.ap.homebanking_ap.utils.CardUtils.getRandomNumberCvv;

@RestController
@RequestMapping ("/api")
public class CardController {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/cards")
    public List<CardDTO> getCards() {
        return cardRepository.findAll().stream().map(CardDTO::new).collect(Collectors.toList());
    }

    @RequestMapping("/cards/{id}")
    public CardDTO getCard(@PathVariable Long id) {
        return new CardDTO(cardRepository.findById(id).orElse(null));
    }
    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createdCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor, Authentication authentication) {

        Client clientAuth = clientRepository.findByEmail(authentication.getName());

        List<Card> cardFiltered = clientAuth.getCards().stream()
                .filter(card -> card.getType() == cardType).collect(Collectors.toList());

        if (cardFiltered.stream().count() == 3) {
            return new ResponseEntity<>("Already max number of Card" + cardType, HttpStatus.FORBIDDEN);
        }

        String numberCard;

        Integer cvv = getRandomNumberCvv(0, 999);

        do {
            numberCard = getRandomNumberCard();
        }
        while (cardRepository.existsByNumber(numberCard));

        Card newCard = new Card(cardType,numberCard,cvv,LocalDate.now(),LocalDate.now().plusYears(5),clientAuth.getFirstName() + " " + clientAuth.getLastName(),
                cardColor);
        clientAuth.addCard(newCard);
        cardRepository.save(newCard);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}