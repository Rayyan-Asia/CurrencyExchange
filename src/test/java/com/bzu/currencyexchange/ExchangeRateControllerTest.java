package com.bzu.currencyexchange;

import com.bzu.currencyexchange.controller.ExchangeRateController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;

class ExchangeRateControllerTest {
    @Mock
    private RestTemplate restTemplate;
    private ExchangeRateController exchangeRateController;

    @BeforeEach
    void setUp() {

        restTemplate = Mockito.mock(RestTemplate.class);
        exchangeRateController = new ExchangeRateController(restTemplate);
    }

    @Test
    void testConvertCurrency() {
        var apiResult = new ApiResult();
        apiResult.setConversion_rate(0.85);
        // Mock the restTemplate and API response
        ResponseEntity<ApiResult> responseEntity = new ResponseEntity<>(apiResult, HttpStatus.OK);
        Mockito.when(restTemplate.getForEntity(anyString(), eq(ApiResult.class))).thenReturn(responseEntity);

        // Call the convertCurrency method
        ConversionResult result = exchangeRateController.convertCurrency(100.0, "USD", "EUR");


        // Assert the result
        Assertions.assertEquals(0.85, result.getRate(), 0.001);
        Assertions.assertEquals(85.0, result.getConvertedAmount(), 0.001);
    }
}
