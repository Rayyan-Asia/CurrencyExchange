package com.bzu.currencyexchange.service;

import com.bzu.currencyexchange.entity.Currency;
import com.bzu.currencyexchange.repository.CurrencyRepository;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public Currency getCurrencyByCode(String code) {
        return (Currency) currencyRepository.findByCode(code);
    }

}

