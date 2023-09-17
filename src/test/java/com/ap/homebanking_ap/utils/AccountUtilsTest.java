package com.ap.homebanking_ap.utils;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AccountUtilsTest {

    @Test
    void getRandomNumberAccountGenerate() {
        Integer randomNumberAccount = AccountUtils.getRandomNumberAccount(10000000,99999999);
        assertTrue(randomNumberAccount <= 99999999);


    }
}