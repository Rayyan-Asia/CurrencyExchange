package com.bzu.currencyexchange.controller;

import com.bzu.currencyexchange.ApiResult;
import com.bzu.currencyexchange.ConversionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ExchangeRateController {

    private final RestTemplate restTemplate;

    public ExchangeRateController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/convertExternal")
    public ResponseEntity<?> convertCurrency(@RequestParam("amount") double amount,
                                             @RequestParam("from") String fromCurrency,
                                             @RequestParam("to") String toCurrency) {
        if (fromCurrency == null || fromCurrency.isEmpty() || toCurrency == null || toCurrency.isEmpty()) {
            // Invalid currency codes provided
            return ResponseEntity.badRequest().build();
        }

        String apiKey = "ee25af1cb7a70ff82dbfd9b3";
        // Make a request to the external API to fetch the conversion rate
        String apiUrl = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + fromCurrency + "/" + toCurrency;
        ResponseEntity<ApiResult> responseEntity = restTemplate.getForEntity(apiUrl, ApiResult.class);

        if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
            // Handle invalid request to the external API
            return ResponseEntity.notFound().build();
        }else if (responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST) {
            // Handle invalid request to the external API
            return ResponseEntity.badRequest().build();
        }


        ApiResult apiResult = responseEntity.getBody();

        if (apiResult == null || !isValidCurrencyConversionResult(apiResult)) {
            // Handle invalid response from the external API
            return ResponseEntity.notFound().build();
        }

        // Extract the conversion rate from the API response
        double rate = apiResult.getConversion_rate();

        // Perform the currency conversion
        double convertedAmount = amount * rate;

        double roundedAmount = Math.round(convertedAmount * 1000.0) / 1000.0;

        return ResponseEntity.ok(new ConversionResult(rate, roundedAmount));
    }

    private boolean isValidCurrencyConversionResult(ApiResult apiResult) {
        // Implement your validation logic here
        // Example: Check if the conversion rate is greater than 0
        return apiResult.getConversion_rate() > 0;
    }
}
