package com.ap.homebanking_ap.controllers;

import com.ap.homebanking_ap.dtos.CardDTO;
import com.ap.homebanking_ap.models.Card;
import com.ap.homebanking_ap.models.CardType;
import com.ap.homebanking_ap.models.Client;
import com.ap.homebanking_ap.models.CardColor;
import com.ap.homebanking_ap.repositories.CardRepository;
import com.ap.homebanking_ap.repositories.ClientRepository;
import com.ap.homebanking_ap.services.CardService;
import com.ap.homebanking_ap.services.ClientService;
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
    private CardService cardService;
    @Autowired
    private ClientService clientService;

    @RequestMapping("/cards")
    public List<CardDTO> getCards() {
        return cardService.getCards();
    }

//    @RequestMapping("/cards/{id}")
//    public CardDTO getCard(@PathVariable Long id) {
//        return new CardDTO(cardService.findById(id).orElse(null));}

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createdCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor, Authentication authentication) {

        Client clientAuth = clientService.getCurrentClient(authentication.getName());

        List<Card> cardFiltered = clientAuth.getCards().stream()
                .filter(card -> card.getType() == cardType && card.getColor() == cardColor).collect(Collectors.toList());

        if ((long) cardFiltered.size() == 1) {
            return new ResponseEntity<>("Already this type of Card",HttpStatus.FORBIDDEN);
        }

        String numberCard;

        Integer cvv = getRandomNumberCvv(0, 999);

        do {
            numberCard = getRandomNumberCard();
        }
        while (cardService.exitsCardByNumber(numberCard));

        /*Card newCard = new Card(cardType,numberCard,cvv,LocalDate.now(),LocalDate.now().plusYears(5),clientAuth.getFirstName() + " " + clientAuth.getLastName(),
                cardColor);
        clientAuth.addCard(newCard);
        cardRepository.save(newCard);*/

        Card newCard = new Card(cardType,clientAuth.getFirstName() + " "+ clientAuth.getLastName(),
                cvv, LocalDate.now().plusYears(5), LocalDate.now(),numberCard,cardColor);
        clientAuth.addCard(newCard);
        cardService.createdCard(newCard);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}