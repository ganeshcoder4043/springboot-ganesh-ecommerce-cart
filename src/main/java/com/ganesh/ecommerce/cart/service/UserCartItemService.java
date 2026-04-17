package com.ganesh.ecommerce.cart.service;

import com.ganesh.ecommerce.cart.dto.UserCartItemDTO;
import com.ganesh.ecommerce.cart.entity.Product;
import com.ganesh.ecommerce.cart.entity.UserCart;
import com.ganesh.ecommerce.cart.entity.UserCartItem;
import com.ganesh.ecommerce.cart.exception.ResourceNotFoundException;
import com.ganesh.ecommerce.cart.mapper.UserCartItemMapper;
import com.ganesh.ecommerce.cart.repository.UserCartItemRepository;
import com.ganesh.ecommerce.cart.repository.UserCartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UserCartItemService {

    private UserCartItemRepository userCartItemRepository;
    private UserCartRepository userCartRepository;
    private ProductService productService;

    @Transactional
    public UserCartItemDTO createUserCartItem(Long userCartId, UserCartItemDTO userCartItemDTO) {
        UserCart userCart = getUserCartEntity(userCartId);
        Product product = productService.findOrCreateProduct(userCartItemDTO.getProduct());

        UserCartItem userCartItemEntity = UserCartItemMapper.toUserCartItemEntity(
                userCartItemDTO,
                userCart,
                product);
        userCartItemEntity.setAddedAt(LocalDateTime.now());

        UserCartItem savedItem = userCartItemRepository.save(userCartItemEntity);
        touchCart(userCart);

        return UserCartItemMapper.toUserCartItemDTO(savedItem);
    }

    @Transactional(readOnly = true)
    public UserCartItemDTO getUserCartItemById(Long userCartItemId) {
        return UserCartItemMapper.toUserCartItemDTO(getUserCartItemEntity(userCartItemId));
    }

    @Transactional(readOnly = true)
    public List<UserCartItemDTO> getUserCartItemsByCartId(Long userCartId) {
        getUserCartEntity(userCartId);

        return userCartItemRepository.findByUserCartId(userCartId)
                .stream()
                .map(UserCartItemMapper::toUserCartItemDTO)
                .toList();
    }

    @Transactional
    public UserCartItemDTO updateUserCartItem(Long userCartItemId, UserCartItemDTO userCartItemDTO) {
        UserCartItem userCartItem = getUserCartItemEntity(userCartItemId);
        userCartItem.setQuantity(userCartItemDTO.getQuantity());

        if (userCartItemDTO.getProduct() != null) {
            userCartItem.setProduct(productService.findOrCreateProduct(userCartItemDTO.getProduct()));
        }

        UserCartItem savedItem = userCartItemRepository.save(userCartItem);
        touchCart(savedItem.getUserCart());

        return UserCartItemMapper.toUserCartItemDTO(savedItem);
    }

    @Transactional
    public void deleteUserCartItem(Long userCartItemId) {
        UserCartItem userCartItem = getUserCartItemEntity(userCartItemId);
        UserCart userCart = userCartItem.getUserCart();

        userCartItemRepository.delete(userCartItem);
        touchCart(userCart);
    }

    private UserCart getUserCartEntity(Long userCartId) {
        return userCartRepository.findById(userCartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + userCartId));
    }

    private UserCartItem getUserCartItemEntity(Long userCartItemId) {
        return userCartItemRepository.findById(userCartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + userCartItemId));
    }

    private void touchCart(UserCart userCart) {
        userCart.setUpdatedAt(LocalDateTime.now());
        userCartRepository.save(userCart);
    }
}
