package com.example.Inditex.prices.repository;

import com.example.Inditex.prices.model.Prices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository  extends JpaRepository<Prices, Long> {
}
