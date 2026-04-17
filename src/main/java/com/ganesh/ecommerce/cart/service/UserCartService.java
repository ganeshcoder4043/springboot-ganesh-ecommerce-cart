package com.ganesh.ecommerce.cart.service;

import com.ganesh.ecommerce.cart.dto.UserCartDTO;
import com.ganesh.ecommerce.cart.entity.User;
import com.ganesh.ecommerce.cart.entity.UserCart;
import com.ganesh.ecommerce.cart.entity.UserCartItem;
import com.ganesh.ecommerce.cart.mapper.UserCartItemMapper;
import com.ganesh.ecommerce.cart.mapper.UserCartMapper;
import com.ganesh.ecommerce.cart.repository.UserCartItemRepository;
import com.ganesh.ecommerce.cart.repository.UserCartRepository;
import com.ganesh.ecommerce.cart.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserCartService {

    private UserCartRepository userCartRepository;
    private UserRepository userRepository;
    private UserCartItemRepository userCartItemRepository;


    public UserCartDTO createUserCart(Long userId, UserCartDTO userCartDTO){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        UserCart userCart = UserCartMapper.toUserCartEntity(userCartDTO);
        userCart.setUser(user);
        userCart.setCreatedAt(LocalDateTime.now());
        userCart.setUpdatedAt(LocalDateTime.now());

        UserCart saveUserCart = userCartRepository.save(userCart);

        // Handle cart items from DTO
        if (userCartDTO.getUserCartItems() != null && !userCartDTO.getUserCartItems().isEmpty()) {
            userCartDTO.getUserCartItems().forEach(itemDTO -> {
                UserCartItem item = UserCartItemMapper.toUserCartItemEntity(itemDTO, saveUserCart);
                item.setAddedAt(LocalDateTime.now());
                userCartItemRepository.save(item);
            });
        }

        return UserCartMapper.toUserCartDTO(saveUserCart);
    }
}
