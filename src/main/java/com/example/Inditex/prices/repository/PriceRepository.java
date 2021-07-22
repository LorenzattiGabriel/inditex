package com.example.Inditex.prices.repository;

import com.example.Inditex.prices.model.Price;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepository extends CrudRepository<Price, Long> {

    @Query("select p from Price p " +
            "where p.brand.id = :#{#brandId} " +
            "AND p.product.id = :#{#productId} " +
            "AND   p.startDate <=  :applicationDate " +
            "AND   p.endDate >=  :applicationDate")
    List<Price> findPrices(@Param("applicationDate") LocalDateTime applicationDate,
                           @Param("brandId") long brandId,
                           @Param("productId") long productId);

}
