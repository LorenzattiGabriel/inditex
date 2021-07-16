package com.example.Inditex.prices.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceIncomingDto {
    @JsonProperty(required = true)
    @NotNull
    @PastOrPresent
    @Setter
    @Getter
    private Instant startDate;

    @JsonProperty(required = true)
    @Positive
    @Setter
    @Getter
    @NotNull
    private Long productId;

    @JsonProperty(required = true)
    @Positive
    @Setter
    @Getter
    @NotNull
    private Long brandId;

}
