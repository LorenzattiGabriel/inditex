package com.example.Inditex.prices.repository;

import com.example.Inditex.prices.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
