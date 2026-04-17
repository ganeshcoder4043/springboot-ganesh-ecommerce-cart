package com.ganesh.ecommerce.cart.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "userCartItems")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCartItem {

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private UserCart userCart;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime addedAt;
    private Integer quantity;

    @ManyToOne // Many cart items point to ONE product thats means  Ek Item = Sirf Ek Product Type (lekin quantity 1 se zyada ho sakti hai) and single object
    @JoinColumn(name = "product_id")
    private Product product;
}
