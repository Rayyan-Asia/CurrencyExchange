package com.bzu.currencyexchange;

import com.bzu.currencyexchange.entity.Currency;
import com.bzu.currencyexchange.repository.CurrencyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@SpringBootApplication
@EntityScan("com.bzu.currencyexchange.entity")
@EnableJpaRepositories("com.bzu.currencyexchange.repository")
public class CurrencyExchangeApplication {
    public static void main(String[] args) {
        SpringApplication.run(CurrencyExchangeApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(CurrencyRepository repository) {
        return (args) -> {
            repository.save(new Currency("USD",1.0));
            repository.save(new Currency("EUR",0.85));
            repository.save(new Currency("GBP",0.72));
        };
    }

}
