package com.example.Inditex.prices.repository;

import com.example.Inditex.prices.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository  extends JpaRepository<Price, Long> {
}
