package com.kenji.web_order.controller;

import com.kenji.web_order.dto.request.CartItemRequest;
import com.kenji.web_order.dto.response.ApiResponse;
import com.kenji.web_order.dto.response.CartItemResponse;
import com.kenji.web_order.dto.response.CartResponse;
import com.kenji.web_order.entity.Cart;
import com.kenji.web_order.service.cart.CartItemService;
import com.kenji.web_order.service.cart.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/carts")
public class CartController {
    CartService cartService;
    CartItemService itemService;

    @GetMapping
    ApiResponse<CartResponse> getCart(){
        CartResponse cartResponse = cartService.getCart();
        return ApiResponse.<CartResponse>builder()
                .result(cartResponse)
                .build();
    }

    @PutMapping
    ApiResponse<Boolean> clearCart(){
        boolean result = cartService.clearCart();
        return ApiResponse.<Boolean>builder()
                .result(result)
                .build();
    }

    @PostMapping("/items")
    ApiResponse<CartItemResponse> addItem(@RequestBody CartItemRequest request){
        CartItemResponse response = itemService.addCartItem(request);
        return ApiResponse.<CartItemResponse>builder()
                .result(response)
                .build();
    }

    @PutMapping("/items/sizes")
    ApiResponse<Void> updateSize(@RequestBody CartItemRequest request){
        itemService.updateSize(request);
        return ApiResponse.<Void>builder()
                .build();
    }

    @PutMapping("/items/quantities")
    ApiResponse<Void> updateQuantity(@RequestBody CartItemRequest request){
        itemService.updateQuantity(request);
        return ApiResponse.<Void>builder()
                .build();
    }

    @PutMapping("/items/notes")
    ApiResponse<Void> updateNote(@RequestBody CartItemRequest request){
        itemService.updateNote(request);
        return ApiResponse.<Void>builder()
                .build();
    }

    @DeleteMapping("/items/{itemId}")
    ApiResponse<Void> deleteItem(@PathVariable Long itemId){
        itemService.deleteCartItem(itemId);
        return ApiResponse.<Void>builder()
                .build();
    }
}
