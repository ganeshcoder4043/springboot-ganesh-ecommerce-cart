package com.ganesh.ecommerce.cart.service;

import com.ganesh.ecommerce.cart.dto.UserDTO;
import com.ganesh.ecommerce.cart.dto.UserCartItemDTO;
import com.ganesh.ecommerce.cart.entity.Product;
import com.ganesh.ecommerce.cart.entity.User;
import com.ganesh.ecommerce.cart.entity.UserAddress;
import com.ganesh.ecommerce.cart.entity.UserCart;
import com.ganesh.ecommerce.cart.entity.UserCartItem;
import com.ganesh.ecommerce.cart.exception.ResourceNotFoundException;
import com.ganesh.ecommerce.cart.mapper.UserAddressMapper;
import com.ganesh.ecommerce.cart.mapper.UserCartItemMapper;
import com.ganesh.ecommerce.cart.mapper.UserMapper;
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
public class UserService {

    private UserRepository userRepository;
    private UserCartRepository userCartRepository;
    private ProductService productService;

    @Transactional
    public UserDTO createUser(UserDTO userDTO){
        if (userDTO == null) {
            throw new IllegalArgumentException("User details are required");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("User already exists with email: " + userDTO.getEmail());
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        if (userDTO.getUserAddress() != null && !userDTO.getUserAddress().isEmpty()) {
            List<UserAddress> userAddresses = userDTO.getUserAddress()
                    .stream()
                    .map(addressDTO -> {
                        UserAddress address = UserAddressMapper.toUserAddressEntity(addressDTO, user);
                        address.setIsDefault(Boolean.TRUE.equals(address.getIsDefault()));
                        return address;
                    })
                    .toList();
            user.setUserAddress(userAddresses);
        }

        User savedUser = userRepository.save(user);

        UserCart userCart = new UserCart();
        userCart.setUser(savedUser);
        userCart.setCreatedAt(LocalDateTime.now());
        userCart.setUpdatedAt(LocalDateTime.now());
        userCart.setUserCartItems(new ArrayList<>());

        if (userDTO.getUserCart() != null
                && userDTO.getUserCart().getUserCartItems() != null
                && !userDTO.getUserCart().getUserCartItems().isEmpty()) {
            userCart.setUserCartItems(createCartItems(userDTO.getUserCart().getUserCartItems(), userCart));
        }

        userCartRepository.save(userCart);
        savedUser.setUserCart(userCart);

        return UserMapper.toUserDTO(savedUser);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(Long userId) {
        return UserMapper.toUserDTO(getUserEntity(userId));
    }

    @Transactional
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User details are required");
        }

        User user = getUserEntity(userId);

        if (!user.getEmail().equals(userDTO.getEmail()) && userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("User already exists with email: " + userDTO.getEmail());
        }

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        return UserMapper.toUserDTO(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = getUserEntity(userId);
        userRepository.delete(user);
    }

    private User getUserEntity(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
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
