package com.demisgomes.givemeconsolepricej.controller;

import com.demisgomes.givemeconsolepricej.model.ConsolePrice;
import com.demisgomes.givemeconsolepricej.model.ConsolePriceCalculateRequest;
import com.demisgomes.givemeconsolepricej.model.ConsolePriceRegisterRequest;
import com.demisgomes.givemeconsolepricej.service.ConsolePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
public class ConsolePriceController {
    @Autowired
    private ConsolePriceService consolePriceService;

    @PostMapping("/consoles")
    public ResponseEntity<ConsolePrice> registerConsolePrice(@RequestBody ConsolePriceRegisterRequest consolePriceRegisterRequest) {
        ConsolePrice consolePrice = consolePriceService.registerConsolePrice(consolePriceRegisterRequest);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(consolePrice.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/calculate")
    public ResponseEntity<ConsolePrice> calculateProfit(@RequestBody ConsolePriceCalculateRequest consolePriceCalculateRequest) {
        ConsolePrice consolePrice = consolePriceService.calculateProfitFromBRL(consolePriceCalculateRequest);
        return ResponseEntity.ok(consolePrice);
    }

    @GetMapping("/consoles/{id}")
    public ResponseEntity<ConsolePrice> getConsolePriceByName(@PathVariable int id) {
        Optional<ConsolePrice> consolePrice = consolePriceService.getConsolePriceById(id);
        return ResponseEntity.of(consolePrice);
    }
}
