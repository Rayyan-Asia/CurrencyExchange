package com.bzu.currencyexchange.repository;

import com.bzu.currencyexchange.entity.Currency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Long> {

    List<Currency> findByCode(String code);
}