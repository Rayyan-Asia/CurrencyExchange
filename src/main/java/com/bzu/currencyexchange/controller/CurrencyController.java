package com.bzu.currencyexchange.controller;

import com.bzu.currencyexchange.ConversionResult;
import com.bzu.currencyexchange.entity.Currency;
import com.bzu.currencyexchange.service.CurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class CurrencyController {

    private final CurrencyService _currencyService;

    public CurrencyController(CurrencyService currencyService) {
        _currencyService = currencyService;
    }

    @GetMapping("/convert")
    public ResponseEntity<?> convertCurrency(@RequestParam("amount") double amount,
                                             @RequestParam("from") String fromCurrency,
                                             @RequestParam("to") String toCurrency) {
        if (fromCurrency == null || fromCurrency.isEmpty() || toCurrency == null || toCurrency.isEmpty()) {
            // Invalid currency codes provided
            return ResponseEntity.badRequest().build();
        }

        Currency from = _currencyService.getCurrencyByCode(fromCurrency);
        Currency to = _currencyService.getCurrencyByCode(toCurrency);

        if (from == null || to == null) {
            // Invalid currency codes provided
            return ResponseEntity.notFound().build();
        }

        // Check if the result from the service is valid
        if (!isValidCurrencyConversionResult(from, to)) {
            return ResponseEntity.notFound().build();
        }

        double rate = from.getValue() / to.getValue();
        double convertedAmount = amount * rate;
        rate = Math.round(rate * 1000.0) / 1000.0;
        double roundedAmount = Math.round(convertedAmount * 1000.0) / 1000.0;

        ConversionResult conversionResult = new ConversionResult(rate, roundedAmount);
        return ResponseEntity.ok(conversionResult);
    }

    private boolean isValidCurrencyConversionResult(Currency from, Currency to) {
        // Implement your validation logic here
        // Example: Check if the exchange rate is greater than 0
        return from.getValue() > 0 && to.getValue() > 0;
    }
}
