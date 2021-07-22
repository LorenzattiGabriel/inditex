package com.example.Inditex.prices.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Price implements Serializable {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @OneToOne
    private Brand brand;

    @Setter
    @Getter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @Setter
    @Getter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int priceList;

    @Setter
    @Getter
    @OneToOne
    private Product product;

    @Setter
    @Getter
    private int priority;

    @Setter
    @Getter
    private float finalPrice;

    @Setter
    @Getter
    private String curr;


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Price price;

        public Builder() {
            this.price = new Price();
        }

        public Builder withGroup(Brand group) {
            price.setBrand(group);
            return this;
        }

        public Builder withStartDate(LocalDateTime startDate) {
            price.setStartDate(startDate);
            return this;
        }

        public Builder withEndDate(LocalDateTime endDate) {
            price.setEndDate(endDate);
            return this;
        }

        public Builder withPriceList(int priceList) {
            price.setPriceList(priceList);
            return this;
        }

        public Builder withProduct(Product product) {
            price.setProduct(product);
            return this;
        }

        public Builder withPriority(int priority) {
            price.setPriority(priority);
            return this;
        }

        public Builder withPrice(float finalPrice) {
            price.setFinalPrice(finalPrice);
            return this;
        }

        public Builder withCurr(String actualCurr) {
            price.setCurr(actualCurr);
            return this;
        }

        public Price build() {
            return price;
        }
    }
}
