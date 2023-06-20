package com.bzu.currencyexchange.controller;

import com.bzu.currencyexchange.ConversionResult;
import com.bzu.currencyexchange.entity.Currency;
import com.bzu.currencyexchange.service.CurrencyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyController {

    private final CurrencyService _currencyService;

    public CurrencyController(CurrencyService currencyService){
        _currencyService = currencyService;
    }
    @GetMapping("/convert")
    public ConversionResult convertCurrency(@RequestParam("amount") double amount,
                                            @RequestParam("from") String fromCurrency,
                                            @RequestParam("to") String toCurrency) {
        Currency from =  _currencyService.getCurrencyByCode(fromCurrency);
        Currency to = _currencyService.getCurrencyByCode(toCurrency);

        double rate = from.getValue()/to.getValue();


        double convertedAmount = amount * rate;
        rate = Math.round(rate*1000.0)/1000.0;
        double roundedAmount = Math.round(convertedAmount * 1000.0) / 1000.0;


        return new ConversionResult(rate, roundedAmount);
    }


}
