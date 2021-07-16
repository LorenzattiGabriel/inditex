package com.example.Inditex.prices.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    @Setter
    private Long id;

    @Setter
    @Getter
    private String name;

    @Id
    public Long getId() {
        return id;
    }


}
