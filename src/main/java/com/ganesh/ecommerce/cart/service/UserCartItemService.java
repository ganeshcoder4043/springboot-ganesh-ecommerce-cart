package com.ganesh.ecommerce.cart.service;

import com.ganesh.ecommerce.cart.dto.UserCartItemDTO;
import com.ganesh.ecommerce.cart.entity.UserCart;
import com.ganesh.ecommerce.cart.entity.UserCartItem;
import com.ganesh.ecommerce.cart.mapper.UserCartItemMapper;
import com.ganesh.ecommerce.cart.mapper.UserCartMapper;
import com.ganesh.ecommerce.cart.repository.UserCartItemRepository;
import com.ganesh.ecommerce.cart.repository.UserCartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserCartItemService {

    private UserCartItemRepository userCartItemRepository;
    private UserCartRepository userCartRepository;

    public UserCartItemDTO createUserCartItem (Long userCartId, UserCartItemDTO userCartItemDTO){
        UserCart userCart = userCartRepository.findById(userCartId).orElseThrow( ()-> new RuntimeException("cant find your cart"));
        UserCartItem userCartItemEntity = UserCartItemMapper.toUserCartItemEntity(userCartItemDTO, userCart);
        UserCartItem save = userCartItemRepository.save(userCartItemEntity);
        return UserCartItemMapper.toUserCartItemDTO(save);
    }
}
