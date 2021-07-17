package com.example.Inditex.prices.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prices implements Serializable {

    @Id
    @Setter
    private Long id;

    @Setter
    @Getter
    @OneToOne
    private Brand group;

    @Setter
    @Getter
    private LocalDateTime startDate;

    @Setter
    @Getter
    private LocalDateTime endDate;

    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        private Prices prices;

        public Builder() {
            this.prices = new Prices();
        }

        public Builder withGroup(Brand group) {
            prices.setGroup(group);
            return this;
        }

        public Builder withStartDate(LocalDateTime startDate) {
            prices.setStartDate(startDate);
            return this;
        }

        public Builder withEndDate(LocalDateTime endDate) {
            prices.setEndDate(endDate);
            return this;
        }

        public Builder withPriceList(int priceList) {
            prices.setPriceList(priceList);
            return this;
        }

        public Builder withProduct(Product product) {
            prices.setProduct(product);
            return this;
        }

        public Builder withPriority(int priority) {
            prices.setPriority(priority);
            return this;
        }

        public Builder withPrice(float finalPrice) {
            prices.setFinalPrice(finalPrice);
            return this;
        }

        public Builder withCurr(String actualCurr) {
            prices.setCurr(actualCurr);
            return this;
        }

        public Prices build() {
            return prices;
        }
    }
}
