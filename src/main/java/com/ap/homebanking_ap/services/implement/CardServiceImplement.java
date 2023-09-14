package com.ap.homebanking_ap.services.implement;

import com.ap.homebanking_ap.dtos.CardDTO;
import com.ap.homebanking_ap.models.Card;
import com.ap.homebanking_ap.repositories.CardRepository;
import com.ap.homebanking_ap.repositories.ClientRepository;
import com.ap.homebanking_ap.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImplement implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<CardDTO> getCards() {
        return cardRepository.findAll().stream().map(CardDTO::new).collect(Collectors.toList());
    }

    @Override
    public boolean exitsCardByNumber(String number) {
        return cardRepository.existsByNumber(number);
    }

    @Override
    public void createdCard(Card card) {
        cardRepository.save(card);
    }

}
