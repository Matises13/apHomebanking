package com.ap.homebanking_ap.dtos;

import com.ap.homebanking_ap.models.Card;
import com.ap.homebanking_ap.models.CardType;
import com.ap.homebanking_ap.models.ColorType;

import java.time.LocalDate;
import java.util.Set;

public class CardDTO {
    private Long id;
    private CardType type;
    private String number;
    private Integer cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;
    private String cardHolder;
    private ColorType color;

    public CardDTO(Card card){
        this.id = card.getId();
        this.type = card.getType();
        this.number = card.getNumber();
        this.cvv= card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
        this.cardHolder = card.getCardHolder();
        this.color = card.getColor();
    }

    public Long getId() {
        return id;
    }

    public CardType getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    public Integer getCvv() {
        return cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public ColorType getColor() {
        return color;
    }
}