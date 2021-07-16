package com.example.Inditex.prices.services;

import com.example.Inditex.prices.dto.PriceDto;
import com.example.Inditex.prices.dto.PriceIncomingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService implements Price {

    @Autowired
    public PriceService() {
    }

    public PriceDto savePrice(PriceIncomingDto priceIncomingDto) {
        return null;
    }
}
