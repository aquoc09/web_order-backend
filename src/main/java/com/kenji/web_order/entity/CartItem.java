package com.kenji.web_order.entity;

import com.kenji.web_order.enums.ProductSize;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "quantity")
    int quantity;

    @Column(name = "total_money")
    BigDecimal totalMoney;

    @Column(name = "product_size")
    ProductSize size;

    @Column(name = "note", length = 200)
    String note;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
}
