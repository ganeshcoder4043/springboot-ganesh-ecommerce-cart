package com.ganesh.ecommerce.cart.service;

import com.ganesh.ecommerce.cart.dto.UserCartDTO;
import com.ganesh.ecommerce.cart.dto.UserCartItemDTO;
import com.ganesh.ecommerce.cart.entity.Product;
import com.ganesh.ecommerce.cart.entity.User;
import com.ganesh.ecommerce.cart.entity.UserCart;
import com.ganesh.ecommerce.cart.entity.UserCartItem;
import com.ganesh.ecommerce.cart.exception.ResourceNotFoundException;
import com.ganesh.ecommerce.cart.mapper.UserCartItemMapper;
import com.ganesh.ecommerce.cart.mapper.UserCartMapper;
import com.ganesh.ecommerce.cart.repository.UserCartRepository;
import com.ganesh.ecommerce.cart.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserCartService {

    private UserCartRepository userCartRepository;
    private UserRepository userRepository;
    private ProductService productService;

    @Transactional
    public UserCartDTO createUserCart(Long userId, UserCartDTO userCartDTO) {
        User user = getUserEntity(userId);

        if (userCartRepository.existsByUserId(userId)) {
            throw new IllegalArgumentException("Cart already exists for user id: " + userId);
        }

        UserCart userCart = new UserCart();
        userCart.setUser(user);
        userCart.setCreatedAt(LocalDateTime.now());
        userCart.setUpdatedAt(LocalDateTime.now());
        userCart.setUserCartItems(new ArrayList<>());

        if (userCartDTO != null
                && userCartDTO.getUserCartItems() != null
                && !userCartDTO.getUserCartItems().isEmpty()) {
            userCart.getUserCartItems().addAll(createCartItems(userCartDTO.getUserCartItems(), userCart));
        }

        UserCart savedCart = userCartRepository.save(userCart);
        return UserCartMapper.toUserCartDTO(savedCart);
    }

    @Transactional(readOnly = true)
    public UserCartDTO getUserCartById(Long userCartId) {
        return UserCartMapper.toUserCartDTO(getUserCartEntity(userCartId));
    }

    @Transactional(readOnly = true)
    public UserCartDTO getUserCartByUserId(Long userId) {
        getUserEntity(userId);

        return userCartRepository.findByUserId(userId)
                .map(UserCartMapper::toUserCartDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user id: " + userId));
    }

    @Transactional
    public UserCartDTO updateUserCart(Long userCartId, UserCartDTO userCartDTO) {
        UserCart userCart = getUserCartEntity(userCartId);

        if (userCart.getUserCartItems() == null) {
            userCart.setUserCartItems(new ArrayList<>());
        } else {
            userCart.getUserCartItems().clear();
        }

        if (userCartDTO != null
                && userCartDTO.getUserCartItems() != null
                && !userCartDTO.getUserCartItems().isEmpty()) {
            userCart.getUserCartItems().addAll(createCartItems(userCartDTO.getUserCartItems(), userCart));
        }

        userCart.setUpdatedAt(LocalDateTime.now());
        return UserCartMapper.toUserCartDTO(userCartRepository.save(userCart));
    }

    @Transactional
    public UserCartDTO clearUserCart(Long userCartId) {
        UserCart userCart = getUserCartEntity(userCartId);

        if (userCart.getUserCartItems() != null) {
            userCart.getUserCartItems().clear();
        }

        userCart.setUpdatedAt(LocalDateTime.now());
        return UserCartMapper.toUserCartDTO(userCartRepository.save(userCart));
    }

    @Transactional
    public void deleteUserCart(Long userCartId) {
        UserCart userCart = getUserCartEntity(userCartId);
        userCartRepository.delete(userCart);
    }

    private User getUserEntity(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    private UserCart getUserCartEntity(Long userCartId) {
        return userCartRepository.findById(userCartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + userCartId));
    }

    private List<UserCartItem> createCartItems(List<UserCartItemDTO> itemDTOs, UserCart userCart) {
        List<UserCartItem> cartItems = new ArrayList<>();

        for (UserCartItemDTO itemDTO : itemDTOs) {
            Product product = productService.findOrCreateProduct(itemDTO.getProduct());
            UserCartItem item = UserCartItemMapper.toUserCartItemEntity(itemDTO, userCart, product);
            item.setAddedAt(LocalDateTime.now());
            cartItems.add(item);
        }

        return cartItems;
    }
}

