package com.example.Inditex.prices.repository;

import com.example.Inditex.prices.model.Prices;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepository extends CrudRepository<Prices, Long> {

    @Query("select p from Prices p " +
            "where p.brand.id = :#{#brandId} " +
            "AND p.product.id = :#{#productId} " +
            "AND   p.startDate <=  :startDate " +
            "AND   p.endDate >=  :startDate")
    List<Prices> findPrices(@Param("startDate") LocalDateTime startDate,
                            @Param("brandId") long brandId,
                            @Param("productId") long productId);

}
