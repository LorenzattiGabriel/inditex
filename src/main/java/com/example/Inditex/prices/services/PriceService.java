package com.example.Inditex.prices.services;

import com.example.Inditex.config.PriceConfig;
import com.example.Inditex.prices.exceptions.*;
import com.example.Inditex.prices.exceptions.TechnicalException;
import com.example.Inditex.prices.model.Brand;
import com.example.Inditex.prices.model.Prices;
import com.example.Inditex.prices.model.Product;
import com.example.Inditex.prices.repository.BrandRepository;
import com.example.Inditex.prices.repository.PriceRepository;
import com.example.Inditex.prices.repository.ProductRepository;
import com.example.Inditex.prices.web.entity.PriceIncomingDto;
import com.example.Inditex.prices.web.entity.PriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.Inditex.prices.exceptions.rule.BusinessRulesError.BusinessError.*;
import static com.example.Inditex.prices.model.Priority.HIGH;
import static com.example.Inditex.prices.model.Priority.LOW;

@Service
public class PriceService implements Price {

    public static final int PERCENTAGE = 100;

    private final PriceRepository priceRepository;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final PriceConfig pricesConfig;

    @Autowired
    public PriceService(PriceRepository priceRepository, BrandRepository brandRepository, ProductRepository productRepository,
                        PriceConfig priceConfig) {
        this.priceRepository = priceRepository;
        this.brandRepository = brandRepository;
        this.productRepository = productRepository;
        this.pricesConfig = priceConfig;
    }

    public PriceResponse savePrice(PriceIncomingDto priceIncomingDto) {
        Prices price = getPriceForCurrentBrand(priceIncomingDto);

        priceRepository.save(price);

        return PriceDtoFromNewPrice(price);
    }

    public PriceResponse getPrice(Integer brandId, Integer productId, Date startDate) {
        Iterable<Prices> all = priceRepository.findAll();
        List<Prices> prices = priceRepository.findPrices(convertToDateTime(startDate), brandId, productId);

        Optional<Prices> priceWithPriority = getPriceWithMorePriority(prices);

        if (!priceWithPriority.isPresent()) {
            throw new BusinessException(PRICE_NOT_FOUND);
        }

        return PriceDtoFromNewPrice(priceWithPriority.get());
    }

    private Optional<Prices> getPriceWithMorePriority(List<Prices> prices) {
        return prices.stream().
                max(Comparator.comparing(Prices::getPriority));
    }

    private PriceResponse PriceDtoFromNewPrice(Prices price) {
        return PriceResponse.builder()
                .withBrandId(price.getBrand().getId())
                .withProductId(price.getProduct().getId())
                .withStartDate(price.getStartDate())
                .withEndDate(price.getEndDate())
                .withPriceList(price.getPriceList())
                .withFinalPrice(price.getFinalPrice())

                .build();
    }

    private Prices getPriceForCurrentBrand(PriceIncomingDto priceIncomingDto) {

        Brand brand = getGroupOfBrand((long) priceIncomingDto.getBrand());
        Product product = getProductOfBrand((long) priceIncomingDto.getProduct());
        LocalDateTime startDate = convertToDateTime(priceIncomingDto.getStartDate());

        return Prices.builder()
                .withGroup(brand)
                .withPrice(getPrice())
                .withCurr(getCurrency())
                .withEndDate(getEndDate(startDate))
                .withStartDate(startDate)
                .withPriority(getPriority())
                .withProduct(product)
                .build();
    }

    private Brand getGroupOfBrand(Long id) {
        Optional<Brand> brand = brandRepository.findById(id);
        return brand.orElseThrow(() -> new BusinessException(GROUP_NOT_FOUND));
    }

    private Product getProductOfBrand(Long id) {
        Optional<Product> group = productRepository.findById(id);
        return group.orElseThrow(() -> new BusinessException(PRODUCT_NOT_FOUND));
    }

    private LocalDateTime convertToDateTime(Date date) {
        try {
            return date.toInstant()
                    .atZone(ZoneOffset.UTC)
                    .toLocalDateTime();
        } catch (DateTimeParseException e) {
            throw new TechnicalException("Error trying to parse to Local Time the start date", e);
        }
    }

    private String getCurrency() {
        Optional<String> currency = Optional.ofNullable(pricesConfig.getCurr());
        return currency.orElseThrow(() -> new BusinessException(CURRENCY_NOT_FOUND));
    }

    private float getPrice() {
        Optional<Float> taxes = Optional.ofNullable(pricesConfig.getTaxes());
        if (taxes.isPresent()) {
            return (float) ((Math.random() * PERCENTAGE) * taxes.get());
        } else {
            throw new BusinessException(CURRENCY_NOT_FOUND);
        }
    }

    private int getPriority() {
        return ThreadLocalRandom.current().nextInt(LOW.getPriority(), (HIGH.getPriority() + 1));
    }

    private LocalDateTime getEndDate(LocalDateTime startDate) {
        int minDay = (int) LocalDate.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth()).toEpochDay();
        int maxDay = (int) LocalDate.of(startDate.getYear(), 12, 31).toEpochDay();
        long randomDay = minDay + ThreadLocalRandom.current().nextInt(maxDay - minDay);

        return LocalDate.ofEpochDay(randomDay).atStartOfDay();
    }
}
