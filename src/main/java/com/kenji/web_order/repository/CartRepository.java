package com.kenji.web_order.repository;

import com.kenji.web_order.entity.Cart;
import com.kenji.web_order.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
