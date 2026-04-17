package com.ganesh.ecommerce.cart.service;

import com.ganesh.ecommerce.cart.dto.UserAddressDTO;
import com.ganesh.ecommerce.cart.entity.User;
import com.ganesh.ecommerce.cart.entity.UserAddress;
import com.ganesh.ecommerce.cart.mapper.UserAddressMapper;
import com.ganesh.ecommerce.cart.repository.UserAddressRepository;
import com.ganesh.ecommerce.cart.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserAddressService {

    private UserAddressRepository userAddressRepository;
    private UserRepository userRepository;

    // Add new address to user
    @Transactional
    public UserAddressDTO addAddress(Long userId, UserAddressDTO userAddressDTO) {
        // Find user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        UserAddress userAddress = UserAddressMapper.toUserAddressEntity(userAddressDTO, user);
        UserAddress savedAddress = userAddressRepository.save(userAddress);
        return UserAddressMapper.toUserAddressDTO(savedAddress);
    }

}
