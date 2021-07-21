package com.example.Inditex.prices.generator;

import com.example.Inditex.config.PriceConfig;
import com.example.Inditex.prices.exceptions.*;
import com.example.Inditex.prices.exceptions.TechnicalException;
import com.example.Inditex.prices.model.Brand;
import com.example.Inditex.prices.model.Prices;
import com.example.Inditex.prices.model.Product;
import com.example.Inditex.prices.repository.BrandRepository;
import com.example.Inditex.prices.repository.ProductRepository;
import com.example.Inditex.prices.web.entity.PriceIncomingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.Inditex.prices.exceptions.rule.BusinessRulesError.BusinessError.*;
import static com.example.Inditex.prices.exceptions.rule.BusinessRulesError.BusinessError.CURRENCY_NOT_FOUND;
import static com.example.Inditex.prices.model.Priority.HIGH;
import static com.example.Inditex.prices.model.Priority.LOW;

@Component
public class PriceGenerator {

    public static final int PERCENTAGE = 100;

    private final PriceConfig pricesConfig;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    @Autowired
    public PriceGenerator(
            PriceConfig priceConfig, BrandRepository brandRepository, ProductRepository productRepository) {
        this.pricesConfig = priceConfig;
        this.brandRepository = brandRepository;
        this.productRepository = productRepository;
    }

    public Prices getPriceForCurrentBrand(PriceIncomingDto priceIncomingDto) {

        Brand brand = getGroupOfBrand((long) priceIncomingDto.getBrand());
        Product product = getProductOfBrand((long) priceIncomingDto.getProduct());
        LocalDateTime startDate = convertToDateTime(priceIncomingDto.getStartDate());

        return Prices.builder()
                .withGroup(brand)
                .withPrice(getFinalPrice())
                .withCurr(getCurrency())
                .withEndDate(getEndDate(startDate))
                .withStartDate(startDate)
                .withPriority(getPriority())
                .withProduct(product)
                .build();
    }

    public LocalDateTime convertToDateTime(Date date) {
        try {
            return date.toInstant()
                    .atZone(OffsetDateTime.now().getOffset())
                    .toLocalDateTime();
        } catch (Exception e) {
            throw new TechnicalException("Error trying to parse to Local Time the start date", e);
        }
    }

    private Brand getGroupOfBrand(Long id) {
        Optional<Brand> brand = brandRepository.findById(id);
        return brand.orElseThrow(() -> new BusinessException(GROUP_NOT_FOUND));
    }

    private Product getProductOfBrand(Long id) {
        Optional<Product> group = productRepository.findById(id);
        return group.orElseThrow(() -> new BusinessException(PRODUCT_NOT_FOUND));
    }

    private String getCurrency() {
        Optional<String> currency = Optional.ofNullable(pricesConfig.getCurr());
        return currency.orElseThrow(() -> new BusinessException(CURRENCY_NOT_FOUND));
    }

    private float getFinalPrice() {
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
