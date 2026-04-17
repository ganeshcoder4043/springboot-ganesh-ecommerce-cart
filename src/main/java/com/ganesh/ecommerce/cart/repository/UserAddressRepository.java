package com.ganesh.ecommerce.cart.repository;

import com.ganesh.ecommerce.cart.entity.AddressType;
import com.ganesh.ecommerce.cart.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

    List<UserAddress> findByUserId(Long userId);

    List<UserAddress> findByUserIdAndIsDefaultTrue(Long userId);

    List<UserAddress> findByUserIdAndAddressType(Long userId, AddressType addressType);
}
