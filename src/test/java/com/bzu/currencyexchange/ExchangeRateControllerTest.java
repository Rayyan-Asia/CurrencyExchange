package com.bzu.currencyexchange;

import com.bzu.currencyexchange.controller.ExchangeRateController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExchangeRateControllerTest {

    private RestTemplate restTemplate;
    private ExchangeRateController exchangeRateController;

    @BeforeEach
    void setUp() {
        restTemplate = Mockito.mock(RestTemplate.class);
        exchangeRateController = new ExchangeRateController(restTemplate);
    }

    @Test
    void convertCurrencyWithNonExistentCurrencyTest() {
        // Mock the restTemplate and API response
        var apiResult = new ApiResult();
        apiResult.setConversion_rate(0);

        ResponseEntity<ApiResult> responseEntity = new ResponseEntity<>(apiResult, HttpStatus.NOT_FOUND);
        when(restTemplate.getForEntity(anyString(), eq(ApiResult.class)))
                .thenReturn(responseEntity);

        // Call the convertCurrency method with a non-existent currency
        ResponseEntity<?> response = exchangeRateController.convertCurrency(100.0, "USD", "XYZ");

        // Assert the response status code for non-existent currency
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void convertCurrencyWithNullParameterTest() {
        // Mock the restTemplate and API response
        var apiResult = new ApiResult();
        apiResult.setConversion_rate(0.85);

        ResponseEntity<ApiResult> responseEntity = new ResponseEntity<>(apiResult, HttpStatus.BAD_REQUEST);
        when(restTemplate.getForEntity(anyString(), eq(ApiResult.class)))
                .thenReturn(responseEntity);

        // Call the convertCurrency method with null parameters
        ResponseEntity<?> response = exchangeRateController.convertCurrency(100.0, null, "EUR");

        // Assert the response status code for null parameter
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void convertCurrencyWithValidConversionTest() {
        // Mock the restTemplate and API response
        var apiResult = new ApiResult();
        apiResult.setConversion_rate(0.85);

        ResponseEntity<ApiResult> responseEntity = new ResponseEntity<>(apiResult, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(ApiResult.class)))
                .thenReturn(responseEntity);

        // Call the convertCurrency method with valid parameters
        ResponseEntity<?> response = exchangeRateController.convertCurrency(100.0, "USD", "EUR");

        // Assert the response status code for valid conversion
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert the result
        ConversionResult result = (ConversionResult) response.getBody();
        assertEquals(0.85, result.getRate(), 0.001);
        assertEquals(85.0, result.getConvertedAmount(), 0.001);
    }


}
