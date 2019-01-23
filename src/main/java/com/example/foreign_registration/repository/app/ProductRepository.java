package com.example.foreign_registration.repository.app;

import com.example.foreign_registration.model.app.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
