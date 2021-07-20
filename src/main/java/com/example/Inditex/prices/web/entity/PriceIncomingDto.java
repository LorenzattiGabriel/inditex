package com.example.Inditex.prices.web.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceIncomingDto {

    @JsonProperty(required = true)
    @NotNull
    @PastOrPresent
    @Setter
    @Getter
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date startDate;

    @JsonProperty(required = true)
    @Positive
    @Setter
    @Getter
    @NotNull
    private int product;

    @JsonProperty(required = true)
    @Positive
    @Setter
    @Getter
    @NotNull
    private int brand;

}
