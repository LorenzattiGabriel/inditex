package com.example.Inditex.prices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceDto {

    @Setter
    @Getter
    private int productId;

    @Setter
    @Getter
    private int brandId;

    @Setter
    @Getter
    private Instant startDate;

    @Setter
    @Getter
    private Instant endDate;

    @Setter
    @Getter
    private float priceList;

    @Setter
    @Getter
    private float price;
}
