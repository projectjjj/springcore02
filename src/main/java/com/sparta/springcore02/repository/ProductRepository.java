package com.sparta.springcore02.repository;

import com.sparta.springcore02.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}