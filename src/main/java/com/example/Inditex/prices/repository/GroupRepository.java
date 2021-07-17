package com.example.Inditex.prices.repository;

import com.example.Inditex.prices.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Brand, Long> {
}
