package com.example.Inditex.prices.controller;

import com.example.Inditex.prices.model.Price;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/inditex/prices/")
public class Controller {

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Price> newPrice(@RequestBody String price) {
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

