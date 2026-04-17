package com.ganesh.ecommerce.cart.dto;

import com.ganesh.ecommerce.cart.entity.AddressType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressDTO {

    private Long id;
    private String street;
    private String city;
    private String state;
    private Long zip_code;
    private Boolean isDefault;
    private AddressType addressType;
}
