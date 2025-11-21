package com.kenji.web_order.service.cart;

import com.kenji.web_order.dto.response.CartResponse;
import com.kenji.web_order.entity.Cart;
import com.kenji.web_order.entity.User;

public interface CartService {
    CartResponse getCart();
    boolean clearCart();
    Cart createCartIfNotExist(User user);
    Cart findCart(User user);
}
