package com.example.Inditex.prices.services;

import com.example.Inditex.prices.exceptions.*;
import com.example.Inditex.prices.model.Prices;
import com.example.Inditex.prices.repository.PriceRepository;
import com.example.Inditex.prices.web.entity.PriceIncomingDto;
import com.example.Inditex.prices.web.entity.PriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.example.Inditex.prices.exceptions.rule.BusinessRulesError.BusinessError.*;

@Service
public class PriceService implements Price {

    private final PriceRepository priceRepository;
    private final PriceGenerator priceGenerator;

    @Autowired
    public PriceService(PriceRepository priceRepository, PriceGenerator priceGenerator) {
        this.priceRepository = priceRepository;
        this.priceGenerator = priceGenerator;
    }

    public PriceResponse savePrice(PriceIncomingDto priceIncomingDto) {
        Prices price = priceGenerator.getPriceForCurrentBrand(priceIncomingDto);

        priceRepository.save(price);

        return getPriceDtoFromNewPrice(price);
    }

    public PriceResponse getPrice(Integer brandId, Integer productId, Date startDate) {
        LocalDateTime convertToDateTime = priceGenerator.convertToDateTime(startDate);
        List<Prices> prices = priceRepository.findPrices(convertToDateTime, brandId, productId);

        Optional<Prices> priceWithPriority = getPriceWithMorePriority(prices);

        if (!priceWithPriority.isPresent()) {
            throw new BusinessException(PRICE_NOT_FOUND);
        }

        return getPriceDtoFromNewPrice(priceWithPriority.get());
    }

    private Optional<Prices> getPriceWithMorePriority(List<Prices> prices) {
        return prices.stream().
                max(Comparator.comparing(Prices::getPriority));
    }

    private PriceResponse getPriceDtoFromNewPrice(Prices price) {
        return PriceResponse.builder()
                .withBrandId(price.getBrand().getId())
                .withProductId(price.getProduct().getId())
                .withStartDate(price.getStartDate())
                .withEndDate(price.getEndDate())
                .withPriceList(price.getPriceList())
                .withFinalPrice(price.getFinalPrice())
                .build();
    }
}
