package com.ganesh.ecommerce.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCartItemDTO {

    private Long id;
    private LocalDateTime addedAt;
    private Integer quantity;
    private ProductDTO product;
}
