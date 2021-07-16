package com.example.Inditex.prices.web;

import com.example.Inditex.prices.dto.PriceDto;
import com.example.Inditex.prices.dto.PriceIncomingDto;
import com.example.Inditex.prices.model.Price;
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
    public ResponseEntity<PriceDto> newPrice(@Valid @RequestBody PriceIncomingDto priceIncomingDto) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/search/", method = RequestMethod.GET)
    public List<Price> getPrices() {
        return Collections.emptyList();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Price> getPrice(@PathVariable("id") Long id) {
        return null;
    }
}

