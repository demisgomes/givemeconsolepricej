package com.demisgomes.givemeconsolepricej.integration;

import com.demisgomes.givemeconsolepricej.service.TaxService;
import org.springframework.stereotype.Service;

@Service
public class TaxServiceMock implements TaxService{
    @Override
    public double getTaxPercentage() {
        return 0.4;
    }
}