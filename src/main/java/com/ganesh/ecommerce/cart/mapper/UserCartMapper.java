package com.ganesh.ecommerce.cart.mapper;

import com.ganesh.ecommerce.cart.dto.UserCartDTO;
import com.ganesh.ecommerce.cart.entity.UserCart;

import java.util.ArrayList;

public class UserCartMapper {

    public static UserCartDTO toUserCartDTO(UserCart userCart){
        if (userCart == null) return null;

        UserCartDTO userCartDTO = new UserCartDTO();
        userCartDTO.setId(userCart.getId());
        userCartDTO.setCreatedAt(userCart.getCreatedAt());
        userCartDTO.setUpdatedAt(userCart.getUpdatedAt());

        if (userCart.getUserCartItems() != null) {
            userCartDTO.setUserCartItems(userCart
                    .getUserCartItems()
                    .stream()
                    .map(UserCartItemMapper::toUserCartItemDTO)
                    .toList());
        }
        return userCartDTO;
    }

    public static UserCart toUserCartEntity(UserCartDTO userCartDTO){
        if (userCartDTO == null) return null;

        UserCart userCart = new UserCart();
        userCart.setId(userCartDTO.getId());
        userCart.setCreatedAt(userCartDTO.getCreatedAt());
        userCart.setUpdatedAt(userCartDTO.getUpdatedAt());
        if (userCartDTO.getUserCartItems() != null) {
            userCart.setUserCartItems(new ArrayList<>(userCartDTO.getUserCartItems()
                    .stream()
                    .map(userCartItemDTO -> UserCartItemMapper.toUserCartItemEntity(userCartItemDTO,userCart))
                    .toList()));
        }
        return userCart;
    }

}
