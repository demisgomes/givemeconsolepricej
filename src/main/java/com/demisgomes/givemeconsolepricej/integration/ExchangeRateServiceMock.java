package com.demisgomes.givemeconsolepricej.integration;

import com.demisgomes.givemeconsolepricej.service.ExchangeRateService;
import org.springframework.stereotype.Service;

@Service
public class ExchangeRateServiceMock implements ExchangeRateService {

    @Override
    public double getExchangeRate() {
        return 5.43;
    }
}