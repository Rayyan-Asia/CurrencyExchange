package com.bzu.currencyexchange.controller;
import com.bzu.currencyexchange.ApiResult;
import com.bzu.currencyexchange.ConversionResult;
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
    public ConversionResult convertCurrency(@RequestParam("amount") double amount,
                                            @RequestParam("from") String fromCurrency,
                                            @RequestParam("to") String toCurrency) {
        String apiKey = "ee25af1cb7a70ff82dbfd9b3";
        // Make a request to the external API to fetch the conversion rate
        String apiUrl = "https://v6.exchangerate-api.com/v6/"+apiKey+"/pair/"+fromCurrency+"/" + toCurrency;
        ResponseEntity<ApiResult> responseEntity = restTemplate.getForEntity(apiUrl, ApiResult.class);
        ApiResult apiResult = responseEntity.getBody();

        // Extract the conversion rate from the API response
        double rate = apiResult.getConversion_rate();

        // Perform the currency conversion
        double convertedAmount = amount * rate;

        double roundedAmount = Math.round(convertedAmount * 1000.0) / 1000.0;

        return new ConversionResult(rate, roundedAmount);
    }


}
