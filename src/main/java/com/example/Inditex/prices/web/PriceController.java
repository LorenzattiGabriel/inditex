package com.example.Inditex.prices.web;

import com.example.Inditex.prices.web.entity.PriceResponse;
import com.example.Inditex.prices.web.entity.PriceIncomingDto;
import com.example.Inditex.prices.model.Prices;
import com.example.Inditex.prices.services.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/inditex/prices/")
public class PriceController {

    private PriceService priceService;

    @Autowired
    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PriceResponse> newPrice(@Valid @RequestBody PriceIncomingDto priceIncomingDto) {
        final PriceResponse price = priceService.savePrice(priceIncomingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(price);
    }

    @RequestMapping(value = "/search/", method = RequestMethod.GET)
    public List<Prices> getPrices() {
        return Collections.emptyList();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Prices> getPrice(@PathVariable("id") Long id) {
        return null;
    }
}

