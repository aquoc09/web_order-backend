package com.kenji.web_order.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_detail_price")
    private float price;

    @Column(name = "num_of_product")
    private int numProducts;

    @Column(name = "total_money")
    private float totalMoney;

    @Column(name = "order_detail_size")
    private String size;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
