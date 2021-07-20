package com.example.Inditex.prices.services;

import com.example.Inditex.prices.web.entity.PriceResponse;
import com.example.Inditex.prices.web.entity.PriceIncomingDto;

import java.time.LocalDateTime;
import java.util.Date;

public interface Price {
    PriceResponse savePrice(PriceIncomingDto priceIncomingDto);

    PriceResponse getPrice(Integer brandId, Integer productId, Date startDate);
}
