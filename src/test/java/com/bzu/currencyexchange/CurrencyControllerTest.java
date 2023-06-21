package com.bzu.currencyexchange;

import com.bzu.currencyexchange.controller.CurrencyController;
import com.bzu.currencyexchange.entity.Currency;
import com.bzu.currencyexchange.service.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrencyControllerTest {
    private CurrencyService currencyService;
    private CurrencyController currencyController;

    @BeforeEach
    void setUp() {
        currencyService = Mockito.mock(CurrencyService.class);
        currencyController = new CurrencyController(currencyService);
    }

    @Test
    void testConvertCurrency() {
        // Mock the currencyService
        Currency fromCurrency = new Currency("USD", 1.0);
        Currency toCurrency = new Currency("EUR", 0.85);
        Mockito.when(currencyService.getCurrencyByCode("USD")).thenReturn(fromCurrency);
        Mockito.when(currencyService.getCurrencyByCode("EUR")).thenReturn(toCurrency);

        // Call the convertCurrency method
        ConversionResult result = currencyController.convertCurrency(100.0, "USD", "EUR");

        // Assert the result
        assertEquals(0.85, result.getRate(), 0.001);
        assertEquals(85.0, result.getConvertedAmount(), 0.001);
    }
}

