package com.ap.homebanking_ap.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
@SpringBootTest
class CardUtilsTest {

    @Test
    void getRandomNumberCvv() {
        Integer randomNumber = CardUtils.getRandomNumberCvv(100,999);
        assertTrue(randomNumber >= 100  && randomNumber <= 999 );
    }

    @Test
    void CardNumberIsCreated() {
        String number = CardUtils.getRandomNumberCard();
        assertThat(number,is(not(emptyOrNullString())));
    }
}