package com.ap.homebanking_ap.services;

import com.ap.homebanking_ap.dtos.CardDTO;
import com.ap.homebanking_ap.models.Card;

import java.util.List;

public interface CardService {
    List<CardDTO> getCards();

    boolean exitsCardByNumber (String number);

    public void createdCard (Card card);
}
