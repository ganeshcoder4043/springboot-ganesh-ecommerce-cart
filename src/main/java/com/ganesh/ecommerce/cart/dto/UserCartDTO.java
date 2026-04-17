package com.ganesh.ecommerce.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCartDTO {

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<UserCartItemDTO> userCartItems;
}
