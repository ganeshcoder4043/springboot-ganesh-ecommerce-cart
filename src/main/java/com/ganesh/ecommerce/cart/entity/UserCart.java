package com.ganesh.ecommerce.cart.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "userCart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCart {

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "userCart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<UserCartItem> userCartItems;

}
