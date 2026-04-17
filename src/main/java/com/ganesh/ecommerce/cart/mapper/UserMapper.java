package com.ganesh.ecommerce.cart.mapper;

import com.ganesh.ecommerce.cart.dto.UserDTO;
import com.ganesh.ecommerce.cart.entity.User;

public class UserMapper {

                  /* entity to DTO */
    public static UserDTO toUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setUserAddress(user.getUserAddress().stream().map(UserAddressMapper::toUserAddressDTO).toList());
        userDTO.setUserCart(UserCartMapper.toUserCartDTO(user.getUserCart()));
        return userDTO;
    }

                  /* DTO to entity*/

    public static User toUserEntity(UserDTO userDTO){
        User user = new User();
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
            user.setUserCart(UserCartMapper.toUserCartEntity(userDTO.getUserCart()));
        }

        return user;
    }
}
