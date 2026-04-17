package com.ganesh.ecommerce.cart.mapper;

import com.ganesh.ecommerce.cart.dto.UserCartItemDTO;
import com.ganesh.ecommerce.cart.entity.Product;
import com.ganesh.ecommerce.cart.entity.UserCart;
import com.ganesh.ecommerce.cart.entity.UserCartItem;

public class UserCartItemMapper {

    public static UserCartItemDTO toUserCartItemDTO(UserCartItem userCartItem){
        if (userCartItem == null) return null;

        UserCartItemDTO userCartItemDTO = new UserCartItemDTO();
        userCartItemDTO.setId(userCartItem.getId());
        userCartItemDTO.setAddedAt(userCartItem.getAddedAt());
        userCartItemDTO.setQuantity(userCartItem.getQuantity());
        userCartItemDTO.setProduct(ProductMapper.toProductDTO(userCartItem.getProduct()));
        return userCartItemDTO;
    }

    public static UserCartItem toUserCartItemEntity(UserCartItemDTO userCartItemDTO, UserCart userCart){
        if (userCartItemDTO == null) return null;

        UserCartItem userCartItem = new UserCartItem();
        userCartItem.setId(userCartItemDTO.getId());
        userCartItem.setAddedAt(userCartItemDTO.getAddedAt());
        userCartItem.setQuantity(userCartItemDTO.getQuantity());

        if (userCartItemDTO.getProduct() != null) {
            userCartItem.setProduct(ProductMapper.toProductEntity(userCartItemDTO.getProduct()));
        }
        userCartItem.setUserCart(userCart);
        return userCartItem;
    }

    public static UserCartItem toUserCartItemEntity(UserCartItemDTO userCartItemDTO, UserCart userCart, Product product){
        if (userCartItemDTO == null) return null;

        UserCartItem userCartItem = new UserCartItem();
        userCartItem.setId(userCartItemDTO.getId());
        userCartItem.setAddedAt(userCartItemDTO.getAddedAt());
        userCartItem.setQuantity(userCartItemDTO.getQuantity());
        userCartItem.setProduct(product);
        userCartItem.setUserCart(userCart);
        return userCartItem;
    }
}
