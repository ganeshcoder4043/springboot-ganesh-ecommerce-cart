package com.ganesh.ecommerce.cart.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<UserAddress> userAddress;

    @OneToOne(mappedBy = "user" , cascade = CascadeType.ALL)
    private UserCart userCart;
}
