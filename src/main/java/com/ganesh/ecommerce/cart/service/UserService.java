package com.ganesh.ecommerce.cart.service;

import com.ganesh.ecommerce.cart.dto.UserDTO;
import com.ganesh.ecommerce.cart.entity.User;
import com.ganesh.ecommerce.cart.entity.UserCart;
import com.ganesh.ecommerce.cart.entity.UserCartItem;
import com.ganesh.ecommerce.cart.mapper.UserCartItemMapper;
import com.ganesh.ecommerce.cart.mapper.UserMapper;
import com.ganesh.ecommerce.cart.repository.UserCartRepository;
import com.ganesh.ecommerce.cart.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private UserCartRepository userCartRepository;

    @Transactional
    public UserDTO createUser(UserDTO userDTO){

        User user = UserMapper.toUserEntity(userDTO);
        User savedUser = userRepository.save(user);

        UserCart userCart = new UserCart();
        userCart.setUser(savedUser);
        userCart.setCreatedAt(LocalDateTime.now());
        userCart.setUpdatedAt(LocalDateTime.now());


        if (userDTO.getUserCart() != null && userDTO.getUserCart().getUserCartItems() != null) {
            List<UserCartItem> cartItems = userDTO.getUserCart()
                    .getUserCartItems()
                    .stream()
                    .map(itemDTO -> {
                        UserCartItem item = UserCartItemMapper.toUserCartItemEntity(itemDTO, userCart);
                        item.setAddedAt(LocalDateTime.now());
                        return item;
                    })
                    .toList();
            userCart.setUserCartItems(cartItems);
        }

        userCartRepository.save(userCart);

        // 4. Update user with cart reference
        savedUser.setUserCart(userCart);

        return UserMapper.toUserDTO(savedUser);
    }
}
