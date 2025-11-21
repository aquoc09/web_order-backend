package com.kenji.web_order.service.cart;

import com.kenji.web_order.dto.response.CartResponse;
import com.kenji.web_order.entity.Cart;
import com.kenji.web_order.entity.User;
import com.kenji.web_order.exception.AppException;
import com.kenji.web_order.exception.ErrorCode;
import com.kenji.web_order.mapper.CartMapper;
import com.kenji.web_order.repository.CartItemRepository;
import com.kenji.web_order.repository.CartRepository;
import com.kenji.web_order.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartServiceImp implements CartService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    CartMapper cartMapper;

    private User getCurrentUser(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        return userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public CartResponse getCart() {
        User user = getCurrentUser();

        Cart cart = cartRepository.findByUser(user).orElseGet(() -> createCartIfNotExist(user));
        return cartMapper.toCartResponse(cart);
    }

    private Cart createCart(User user) {
        Cart cart = Cart.builder()
                .cartItems(new ArrayList<>())
                .active(true)
                .user(user)
                .build();
        return cartRepository.save(cart);
    }

    public Cart createCartIfNotExist(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> createCart(user));
    }


    public Cart findCart(User user) {
        return cartRepository.findByUser(user)
                .orElse(null);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    @Transactional
    public boolean clearCart() {
        User user = getCurrentUser();
        Cart cart = createCartIfNotExist(user);
        cartItemRepository.deleteAllByCart(cart);
        cart.getCartItems().clear();
        return cart.getCartItems().isEmpty();
    }
}
