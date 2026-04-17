package com.ganesh.ecommerce.cart.mapper;

import com.ganesh.ecommerce.cart.dto.UserCartItemDTO;
import com.ganesh.ecommerce.cart.entity.UserCart;
import com.ganesh.ecommerce.cart.entity.UserCartItem;

public class UserCartItemMapper {

                       /* Entity to DTO */
    public static UserCartItemDTO toUserCartItemDTO(UserCartItem userCartItem){
        UserCartItemDTO userCartItemDTO = new UserCartItemDTO();
        userCartItemDTO.setId(userCartItem.getId());
        userCartItemDTO.setAddedAt(userCartItem.getAddedAt());
        userCartItemDTO.setQuantity(userCartItem.getQuantity());
        userCartItemDTO.setProduct(ProductMapper.toProductDTO(userCartItem.getProduct()));
        return userCartItemDTO;
    }


                          /* DTO to Entity */
    public static UserCartItem toUserCartItemEntity(UserCartItemDTO userCartItemDTO, UserCart userCart){
        UserCartItem userCartItem = new UserCartItem();
        userCartItem.setAddedAt(userCartItemDTO.getAddedAt());
        userCartItem.setQuantity(userCartItemDTO.getQuantity());
        userCartItem.setId(userCartItemDTO.getId());
        if (userCartItemDTO.getProduct() != null) {
            userCartItem.setProduct(ProductMapper.toProductEntity(userCartItemDTO.getProduct()));
        }
        userCartItem.setUserCart(userCart);
        return userCartItem;
    }
}
