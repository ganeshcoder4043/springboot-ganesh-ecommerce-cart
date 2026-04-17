package com.ganesh.ecommerce.cart.repository;

import com.ganesh.ecommerce.cart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
