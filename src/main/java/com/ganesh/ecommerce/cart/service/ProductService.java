package com.ganesh.ecommerce.cart.service;

import com.ganesh.ecommerce.cart.dto.ProductDTO;
import com.ganesh.ecommerce.cart.entity.Product;
import com.ganesh.ecommerce.cart.entity.UserCartItem;
import com.ganesh.ecommerce.cart.mapper.ProductMapper;
import com.ganesh.ecommerce.cart.repository.ProductRepository;
import com.ganesh.ecommerce.cart.repository.UserCartItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;
//    private UserCartItemRepository userCartItemRepository;

    public ProductDTO createProduct (/*Long userCartItemId*/ ProductDTO productDTO){
//        UserCartItem userCartItem = userCartItemRepository
//                .findById(userCartItemId).orElseThrow(() -> new RuntimeException("user cart it dose not find"));
        Product productEntity = ProductMapper.toProductEntity(productDTO);
        Product save = productRepository.save(productEntity);
       return  ProductMapper.toProductDTO(save);

    }
}

