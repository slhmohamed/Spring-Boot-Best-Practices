package com.example.springbootbestpractices.repository;

import com.example.springbootbestpractices.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
