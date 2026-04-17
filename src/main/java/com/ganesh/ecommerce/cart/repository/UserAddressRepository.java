package com.ganesh.ecommerce.cart.repository;

import com.ganesh.ecommerce.cart.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

    // Find all addresses for a specific user
    List<UserAddress> findByUserId(Long userId);

    // Find default address for a user
    List<UserAddress> findByUserIdAndIsDefaultTrue(Long userId);

    // Find addresses by address type for a user
    List<UserAddress> findByUserIdAndAddressType(Long userId, String addressType);
}
