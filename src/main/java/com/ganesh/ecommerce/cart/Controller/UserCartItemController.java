package com.ganesh.ecommerce.cart.Controller;

import com.ganesh.ecommerce.cart.dto.UserCartItemDTO;
import com.ganesh.ecommerce.cart.service.UserCartItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/usercartitem")
@AllArgsConstructor
public class UserCartItemController {

    private UserCartItemService userCartItemService;

    @PostMapping("/user/{userCartId}")
    public ResponseEntity<UserCartItemDTO> createUserCartItem(@PathVariable Long userCartId,
                                                              @RequestBody @Valid UserCartItemDTO userCartItemDTO){
        try {
            UserCartItemDTO userCartItem = userCartItemService.createUserCartItem(userCartId, userCartItemDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(userCartItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
