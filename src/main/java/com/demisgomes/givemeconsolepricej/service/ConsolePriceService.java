package com.demisgomes.givemeconsolepricej.service;

import com.demisgomes.givemeconsolepricej.model.ConsolePrice;
import com.demisgomes.givemeconsolepricej.model.ConsolePriceCalculateRequest;
import com.demisgomes.givemeconsolepricej.model.ConsolePriceRegisterRequest;
import com.demisgomes.givemeconsolepricej.repository.ConsolePriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ConsolePriceService{
    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private TaxService taxService;

    @Autowired
    private ConsolePriceRepository consolePriceRepository;

    public ConsolePrice registerConsolePrice(ConsolePriceRegisterRequest consolePriceRegisterRequest){
        double exchangeRate = exchangeRateService.getExchangeRate();
        double taxPercentage = taxService.getTaxPercentage();
        double priceInBRLBeforeTax = consolePriceRegisterRequest.getPriceInUSD() * exchangeRate;
        double taxAmount = priceInBRLBeforeTax * taxPercentage;
        double profitAmount = (priceInBRLBeforeTax + taxAmount) * (consolePriceRegisterRequest.getProfitPercentage());

        double priceInBRL = priceInBRLBeforeTax + taxAmount + profitAmount;

        ConsolePrice consolePrice = new  ConsolePrice(
                        null,
                        consolePriceRegisterRequest.getConsoleName(),
                        consolePriceRegisterRequest.getPriceInUSD(),
                        exchangeRate,
                        taxPercentage,
                        taxAmount,
                        consolePriceRegisterRequest.getProfitPercentage(),
                        profitAmount,
                        priceInBRL
                );

        return consolePriceRepository.save(consolePrice);
    }

    public Optional<ConsolePrice> getConsolePriceById(int id) {
        return consolePriceRepository.findById(id);
    }

    public ConsolePrice calculateProfitFromBRL(ConsolePriceCalculateRequest consolePriceCalculateRequest) {
        double exchangeRate = exchangeRateService.getExchangeRate();
        double taxPercentage = taxService.getTaxPercentage();

        double priceInBRLBeforeTax = consolePriceCalculateRequest.getPriceInUSD() * exchangeRate;
        double taxAmount = priceInBRLBeforeTax * taxPercentage;
        double priceInBRLWithTax = priceInBRLBeforeTax + taxAmount;

        double profitAmount = consolePriceCalculateRequest.getPriceInBRL() - priceInBRLWithTax;
        double profitPercentage = profitAmount/(priceInBRLWithTax);

        return new ConsolePrice(
                        null,
                        consolePriceCalculateRequest.getConsoleName(),
                        consolePriceCalculateRequest.getPriceInUSD(),
                        exchangeRate,
                        taxPercentage,
                        taxAmount,
                        profitPercentage,
                        profitAmount,
                        consolePriceCalculateRequest.getPriceInBRL()
                );
    }
}