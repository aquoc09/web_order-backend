package com.kenji.web_order.service.cart;

import com.kenji.web_order.dto.request.CartItemRequest;
import com.kenji.web_order.dto.response.CartItemResponse;

public interface CartItemService {
    CartItemResponse addCartItem(CartItemRequest request);
    void updateQuantity(CartItemRequest request);
    void updateSize(CartItemRequest request);
    void deleteCartItem(Long id);
    void updateNote(CartItemRequest request);
}
