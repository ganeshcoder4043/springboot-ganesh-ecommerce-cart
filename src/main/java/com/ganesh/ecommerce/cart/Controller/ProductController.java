package com.ganesh.ecommerce.cart.Controller;

import com.ganesh.ecommerce.cart.dto.ProductDTO;
import com.ganesh.ecommerce.cart.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
//@RequestMapping("user/product")
public class ProductController {

    private ProductService productService;

    @PostMapping("user/product")
    public ResponseEntity<ProductDTO> createProduct (@RequestBody @Valid ProductDTO productDTO){
        try{
            ProductDTO product = productService.createProduct(productDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

