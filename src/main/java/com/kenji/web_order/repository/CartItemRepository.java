package com.kenji.web_order.repository;

import com.kenji.web_order.entity.Cart;
import com.kenji.web_order.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCart(Cart cart);
}
