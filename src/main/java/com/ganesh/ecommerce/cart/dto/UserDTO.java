package com.ganesh.ecommerce.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private String email;
    private List<UserAddressDTO> userAddress;
    private UserCartDTO userCart;
}
