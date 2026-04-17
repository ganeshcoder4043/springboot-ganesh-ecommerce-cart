package com.ganesh.ecommerce.cart.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "userAddresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddress {

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference /*  @JsonIgnore yhi bhi use kr skten hai*/
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String city;
    private String state;
    private Long zip_code;
    private Boolean isDefault;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;


}
