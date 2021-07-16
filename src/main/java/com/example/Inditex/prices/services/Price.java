package com.example.Inditex.prices.services;

import com.example.Inditex.prices.web.entity.PriceResponse;
import com.example.Inditex.prices.web.entity.PriceIncomingDto;

public interface Price {
    PriceResponse savePrice(PriceIncomingDto priceIncomingDto);
}
