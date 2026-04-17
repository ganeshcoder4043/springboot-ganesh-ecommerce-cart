package com.ganesh.ecommerce.cart.Controller;

import com.ganesh.ecommerce.cart.dto.UserCartDTO;
import com.ganesh.ecommerce.cart.service.UserCartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user/usercart")
public class UserCartController {

    private UserCartService userCartService;

    @PostMapping("/{userId}")
    public ResponseEntity<UserCartDTO> createUserCart(@PathVariable Long userId, @RequestBody @Valid UserCartDTO userCartDTO){
        try {
            UserCartDTO userCart = userCartService.createUserCart(userId, userCartDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(userCart);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
