package com.bzu.currencyexchange;

import com.bzu.currencyexchange.controller.CurrencyController;
import com.bzu.currencyexchange.entity.Currency;
import com.bzu.currencyexchange.service.CurrencyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class CurrencyControllerTest {
    private CurrencyService currencyService;
    private CurrencyController currencyController;

    @BeforeEach
    void setUp() {
        currencyService = Mockito.mock(CurrencyService.class);
        currencyController = new CurrencyController(currencyService);
    }

    @Test
    public void convertCurrencyWithValidCredentialTest() {
        // Mock the currencyService
        Currency toCurrency = new Currency("USD", 1.0);
        Currency fromCurrency = new Currency("EUR", 0.85);
        Mockito.when(currencyService.getCurrencyByCode("USD")).thenReturn(fromCurrency);
        Mockito.when(currencyService.getCurrencyByCode("EUR")).thenReturn(toCurrency);

        // Call the convertCurrency method
        ResponseEntity<?> response = currencyController.convertCurrency(100.0, "USD", "EUR");

        // Assert the response status code
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert the result
        ConversionResult result = (ConversionResult) response.getBody();
        Assertions.assertEquals(0.85, result.getRate(), 0.001);
        Assertions.assertEquals(85.0, result.getConvertedAmount(), 0.001);
    }

    @Test
    public void convertCurrencyWithNullParameterTest() {
        // Call the convertCurrency method with null parameters
        ResponseEntity<?> response = currencyController.convertCurrency(100.0, null, "EUR");

        // Assert the response status code for null parameter
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    public void convertCurrencyWithNonExistentCurrencyTest() {
        // Mock the currency service to return null for a currency
        when(currencyService.getCurrencyByCode(anyString())).thenReturn(null);

        // Call the convertCurrency method with a non-existent currency
        ResponseEntity<?> response = currencyController.convertCurrency(100.0, "USD", "XYZ");

        // Assert the response status code for non-existent currency
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    public void convertCurrencyWithInvalidAmountTest() {
        // Mock the currency service to return null for a currency
        Currency toCurrency = new Currency("USD", 1.0);
        Currency fromCurrency = new Currency("EUR", 0.85);
        Mockito.when(currencyService.getCurrencyByCode("USD")).thenReturn(fromCurrency);
        Mockito.when(currencyService.getCurrencyByCode("EUR")).thenReturn(toCurrency);

        // Call the convertCurrency method with a non-existent currency
        ResponseEntity<?> response = currencyController.convertCurrency(0, "USD", "XYZ");

        // Assert the response status code for non-existent currency
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }



}


