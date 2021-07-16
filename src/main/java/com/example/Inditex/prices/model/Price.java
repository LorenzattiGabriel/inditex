package com.example.Inditex.prices.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Price implements Serializable {
    @Setter
    private Long id;

    @Setter
    @Getter
    private Group group;

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
    private Product product;

    @Setter
    @Getter
    private int priority;

    @Setter
    @Getter
    private float price;

    @Setter
    @Getter
    private String curr;

    @Id
    public Long getId() {
        return id;
    }
}
