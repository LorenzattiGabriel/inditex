package com.example.Inditex.prices.services;

import com.example.Inditex.prices.dto.PriceDto;
import com.example.Inditex.prices.dto.PriceIncomingDto;

public interface Price {
    PriceDto savePrice(PriceIncomingDto priceIncomingDto);
}
