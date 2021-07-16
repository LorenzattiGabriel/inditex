package com.example.Inditex.prices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.Instant;

public class PriceIncomingDto {

    @JsonProperty(required = true)
    @NotNull
    @PastOrPresent
    private Instant startDate;

    @JsonProperty(required = true)
    @Positive
    private int productId;

    @JsonProperty(required = true)
    @Positive
    private int brandId;
}
