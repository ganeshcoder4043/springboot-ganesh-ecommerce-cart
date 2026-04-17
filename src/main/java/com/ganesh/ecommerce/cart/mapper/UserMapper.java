package com.ganesh.ecommerce.cart.mapper;

import com.ganesh.ecommerce.cart.dto.UserDTO;
import com.ganesh.ecommerce.cart.entity.UserCart;
import com.ganesh.ecommerce.cart.entity.User;

import java.util.ArrayList;

public class UserMapper {

    public static UserDTO toUserDTO(User user){
        if (user == null) return null;

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());

        if (user.getUserAddress() != null) {
            userDTO.setUserAddress(user.getUserAddress()
                    .stream()
                    .map(UserAddressMapper::toUserAddressDTO)
                    .toList());
        }

        userDTO.setUserCart(UserCartMapper.toUserCartDTO(user.getUserCart()));
        return userDTO;
    }

    public static User toUserEntity(UserDTO userDTO){
        if (userDTO == null) return null;

        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());

        if (userDTO.getUserAddress() != null && !userDTO.getUserAddress().isEmpty()) {
            user.setUserAddress(userDTO.getUserAddress()
                    .stream()
                    .map(dto -> UserAddressMapper.toUserAddressEntity(dto, user))
                    .toList());
        }

        if (userDTO.getUserCart() != null) {
            UserCart userCart = UserCartMapper.toUserCartEntity(userDTO.getUserCart());
            userCart.setUser(user);
            if (userCart.getUserCartItems() != null) {
                userCart.setUserCartItems(new ArrayList<>(userCart.getUserCartItems()));
                userCart.getUserCartItems().forEach(item -> item.setUserCart(userCart));
            }
            user.setUserCart(userCart);
        }

        return user;
    }
}
