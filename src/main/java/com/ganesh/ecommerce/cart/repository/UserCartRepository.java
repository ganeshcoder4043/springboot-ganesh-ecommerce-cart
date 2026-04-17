package com.ganesh.ecommerce.cart.repository;

import com.ganesh.ecommerce.cart.entity.UserCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCartRepository extends JpaRepository<UserCart, Long> {

    Optional<UserCart> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
