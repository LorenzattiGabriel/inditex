package com.example.Inditex.prices.services;

import com.example.Inditex.prices.web.entity.PriceResponse;
import com.example.Inditex.prices.dto.PriceIncomingDto;

public interface Price {
    PriceResponse savePrice(PriceIncomingDto priceIncomingDto);
}
