package com.ganesh.ecommerce.cart.repository;

import com.ganesh.ecommerce.cart.entity.UserCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCartItemRepository extends JpaRepository<UserCartItem, Long> {

    List<UserCartItem> findByUserCartId(Long userCartId);
}
