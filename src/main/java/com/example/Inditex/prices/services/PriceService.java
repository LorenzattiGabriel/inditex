package com.example.Inditex.prices.services;

import com.example.Inditex.prices.dto.PriceDto;
import com.example.Inditex.prices.dto.PriceIncomingDto;
import com.example.Inditex.prices.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService implements Price {

    private final PriceRepository priceRepository;

    @Autowired
    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public PriceDto savePrice(PriceIncomingDto priceIncomingDto) {
        return null;
    }
}
