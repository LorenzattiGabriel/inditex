package com.example.Inditex.prices.services;

import com.example.Inditex.config.PriceConfig;
import com.example.Inditex.prices.exceptions.*;
import com.example.Inditex.prices.exceptions.TechnicalException;
import com.example.Inditex.prices.model.Brand;
import com.example.Inditex.prices.model.Prices;
import com.example.Inditex.prices.model.Product;
import com.example.Inditex.prices.repository.GroupRepository;
import com.example.Inditex.prices.repository.PriceRepository;
import com.example.Inditex.prices.repository.ProductRepository;
import com.example.Inditex.prices.web.entity.PriceIncomingDto;
import com.example.Inditex.prices.web.entity.PriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.Inditex.prices.exceptions.rule.BusinessRulesError.BusinessError.*;
import static com.example.Inditex.prices.model.Priority.HIGH;
import static com.example.Inditex.prices.model.Priority.LOW;

@Service
public class PriceService implements Price {

    public static final int PERCENTAGE = 100;
    private final String CURRENCY = "curr";

    private final PriceRepository priceRepository;
    private final GroupRepository groupRepository;
    private final ProductRepository productRepository;
    private final Map<String, String> pricesConfig;

    @Autowired
    public PriceService(PriceRepository priceRepository, GroupRepository groupRepository, ProductRepository productRepository,
                        PriceConfig priceConfig) {
        this.priceRepository = priceRepository;
        this.groupRepository = groupRepository;
        this.productRepository = productRepository;
        this.pricesConfig = priceConfig.getProperties();
    }

    public PriceResponse savePrice(PriceIncomingDto priceIncomingDto) {
        Prices price = getPriceForCurrentBrand(priceIncomingDto);

        priceRepository.save(price);

        return PriceDtoFromNewPrice(price);
    }

    private PriceResponse PriceDtoFromNewPrice(Prices price) {
        return PriceResponse.builder()
                .withBrandId(price.getGroup().getId())
                .withProductId(price.getProduct().getId())
                .withStartDate(price.getStartDate())
                .withEndDate(price.getEndDate())
                .withPriceList(price.getPriceList())
                .withFinalPrice(price.getFinalPrice())

                .build();
    }

    private Prices getPriceForCurrentBrand(PriceIncomingDto priceIncomingDto) {

        Brand group = getGroupOfBrand((long) priceIncomingDto.getBrand());
        Product product = getProductOfBrand((long) priceIncomingDto.getProduct());
        LocalDateTime startDate = convertInstantToDateTime(priceIncomingDto.getStartDate());

        return Prices.builder()
                .withGroup(group)
                .withPrice(getPrice())
                .withCurr(getCurrency())
                .withEndDate(getEndDate(startDate))
                .withStartDate(startDate)
                .withPriority(getPriority())
                .withProduct(product)
                .build();
    }

    private Brand getGroupOfBrand(Long id) {
        Optional<Brand> brand = Optional.of(groupRepository.getById(id));
        return brand.orElseThrow(() -> new BusinessException(GROUP_NOT_FOUND));
    }

    private Product getProductOfBrand(Long id) {
        Optional<Product> group = Optional.of(productRepository.getById(id));
        return group.orElseThrow(() -> new BusinessException(PRODUCT_NOT_FOUND));
    }

    private LocalDateTime convertInstantToDateTime(Date date) {
        try {
            return date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
        } catch (DateTimeParseException e) {
            throw new TechnicalException("Error trying to parse to Local Time the start date", e);
        }
    }

    private String getCurrency() {
        Optional<String> currency = Optional.ofNullable(pricesConfig.get(CURRENCY));
        return currency.orElseThrow(() -> new BusinessException(CURRENCY_NOT_FOUND));
    }

    private Float getPrice() {
        float taxes = 0;
        Optional<String> coeficent = Optional.ofNullable(pricesConfig.get(CURRENCY));
        if (coeficent.isPresent()) {
            taxes = Float.parseFloat(coeficent.get());
        } else {
            throw new BusinessException(CURRENCY_NOT_FOUND);
        }
        return (float) ((Math.random() * PERCENTAGE) * taxes);
    }

    private int getPriority() {
        return ThreadLocalRandom.current().nextInt(LOW.getPriority(), (HIGH.getPriority() + 1));
    }

    private LocalDateTime getEndDate(LocalDateTime startDate) {
        int afterDays = 0;
        Optional<String> days = Optional.ofNullable(pricesConfig.get(CURRENCY));

        if (days.isPresent()) {
            afterDays = Integer.parseInt(days.get());
        } else {
            return null;
        }
        return startDate.plusDays(afterDays);
    }
}
