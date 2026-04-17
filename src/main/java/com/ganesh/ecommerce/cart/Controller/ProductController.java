package com.ganesh.ecommerce.cart.Controller;

import com.ganesh.ecommerce.cart.dto.ProductDTO;
import com.ganesh.ecommerce.cart.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user/product")
@Tag(name = "Products", description = "Product catalog APIs")
public class ProductController {

    private ProductService productService;

    @Operation(summary = "Create product", description = "Creates a new product in the catalog.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created"),
            @ApiResponse(responseCode = "400", description = "Invalid product details")
    })
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct (@RequestBody @Valid ProductDTO productDTO){
        ProductDTO product = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @Operation(summary = "Get all products", description = "Returns every product from the catalog.")
    @ApiResponse(responseCode = "200", description = "Products returned")
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(summary = "Get product by id", description = "Returns one product by product id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product returned"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById(
            @Parameter(description = "Product id", example = "1")
            @PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @Operation(summary = "Update product", description = "Updates name, description, and price for an existing product.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated"),
            @ApiResponse(responseCode = "400", description = "Invalid product details"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(
            @Parameter(description = "Product id", example = "1")
            @PathVariable Long productId,
            @RequestBody @Valid ProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(productId, productDTO));
    }

    @Operation(summary = "Delete product", description = "Deletes an existing product by product id.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Product id", example = "1")
            @PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}

