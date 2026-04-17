package com.ganesh.ecommerce.cart.service;

import com.ganesh.ecommerce.cart.dto.ProductDTO;
import com.ganesh.ecommerce.cart.entity.Product;
import com.ganesh.ecommerce.cart.exception.ResourceNotFoundException;
import com.ganesh.ecommerce.cart.mapper.ProductMapper;
import com.ganesh.ecommerce.cart.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        if (productDTO == null) {
            throw new IllegalArgumentException("Product details are required");
        }
        validateProductDetails(productDTO);

        Product productEntity = ProductMapper.toProductEntity(productDTO);
        productEntity.setId(null);

        Product savedProduct = productRepository.save(productEntity);
        return ProductMapper.toProductDTO(savedProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toProductDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long productId) {
        return ProductMapper.toProductDTO(getProductEntity(productId));
    }

    @Transactional
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        if (productDTO == null) {
            throw new IllegalArgumentException("Product details are required");
        }
        validateProductDetails(productDTO);

        Product product = getProductEntity(productId);
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());

        return ProductMapper.toProductDTO(productRepository.save(product));
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = getProductEntity(productId);
        productRepository.delete(product);
    }

    public Product findOrCreateProduct(ProductDTO productDTO) {
        if (productDTO == null) {
            throw new IllegalArgumentException("Product details are required for cart item");
        }

        if (productDTO.getId() != null) {
            return getProductEntity(productDTO.getId());
        }
        validateProductDetails(productDTO);

        return productRepository
                .findByNameAndDescriptionAndPrice(
                        productDTO.getName(),
                        productDTO.getDescription(),
                        productDTO.getPrice())
                .orElseGet(() -> productRepository.save(ProductMapper.toProductEntity(productDTO)));
    }

    private Product getProductEntity(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
    }

    private void validateProductDetails(ProductDTO productDTO) {
        if (productDTO.getName() == null || productDTO.getName().isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }

        if (productDTO.getDescription() == null || productDTO.getDescription().isBlank()) {
            throw new IllegalArgumentException("Product description is required");
        }

        if (productDTO.getPrice() == null || productDTO.getPrice() <= 0) {
            throw new IllegalArgumentException("Product price must be positive");
        }
    }
}

