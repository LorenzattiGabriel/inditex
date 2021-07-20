package com.example.Inditex.prices.repository;

import com.example.Inditex.prices.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
