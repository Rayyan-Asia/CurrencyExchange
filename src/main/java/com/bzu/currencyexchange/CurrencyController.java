package com.bzu.currencyexchange;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyController {

    @GetMapping("/convert")
    public ConversionResult convertCurrency(@RequestParam("amount") double amount,
                                            @RequestParam("from") String fromCurrency,
                                            @RequestParam("to") String toCurrency) {
        // Perform currency conversion logic here
        // Replace this with your actual currency conversion code or API call

        // For demonstration purposes, let's assume the conversion rate is 0.85
        double rate = 0.85;
        double convertedAmount = amount * rate;

        return new ConversionResult(rate, convertedAmount);
    }


}
