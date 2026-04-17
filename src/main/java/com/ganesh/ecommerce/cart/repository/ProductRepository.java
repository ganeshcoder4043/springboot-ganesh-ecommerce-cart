package com.ganesh.ecommerce.cart.repository;

import com.ganesh.ecommerce.cart.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameAndPrice(String name, Double price);

    Optional<Product> findByNameAndDescriptionAndPrice(String name, String description, Double price);
}
