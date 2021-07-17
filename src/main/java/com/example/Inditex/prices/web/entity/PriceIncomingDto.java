package com.example.Inditex.prices.web.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant startDate;

    @JsonProperty(required = true)
    @Positive
    @Setter
    @Getter
    @NotNull
    private int productId;

    @JsonProperty(required = true)
    @Positive
    @Setter
    @Getter
    @NotNull
    private int brandId;

}
