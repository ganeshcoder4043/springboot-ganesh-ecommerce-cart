package com.ganesh.ecommerce.cart.mapper;

import com.ganesh.ecommerce.cart.dto.ProductDTO;
import com.ganesh.ecommerce.cart.entity.Product;

public class ProductMapper {

    /* Entity to DTO */
    public static ProductDTO toProductDTO(Product product){
        if (product == null) return null;

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());

        return productDTO;
    }

    /* DTO to Entity */
    public static Product toProductEntity(ProductDTO productDTO){
        if (productDTO == null) return null;

        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());

        return product;
    }
}
