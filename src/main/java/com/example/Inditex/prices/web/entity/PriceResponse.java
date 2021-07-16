package com.example.Inditex.prices.web.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceResponse {

    @Setter
    @Getter
    private Long productId;

    @Setter
    @Getter
    private Long brandId;

    @Setter
    @Getter
    private LocalDateTime startDate;

    @Setter
    @Getter
    private LocalDateTime endDate;

    @Setter
    @Getter
    private int priceList;

    @Setter
    @Getter
    private float price;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private PriceResponse priceDto;

        public Builder() {
            this.priceDto = new PriceResponse();
        }

        public Builder withBrandId(Long brandId) {
            priceDto.setBrandId(brandId);
            return this;
        }

        public Builder withProductId(Long productId) {
            priceDto.setProductId(productId);
            return this;
        }

        public Builder withStartDate(LocalDateTime startDate) {
            priceDto.setStartDate(startDate);
            return this;
        }

        public Builder withEndDate(LocalDateTime endDate) {
            priceDto.setEndDate(endDate);
            return this;
        }

        public Builder withPriceList(int priceList) {
            priceDto.setPriceList(priceList);
            return this;
        }

        public Builder withFinalPrice(float price) {
            priceDto.setPrice(price);
            return this;
        }

        public PriceResponse build() {
            return priceDto;
        }
    }
}
