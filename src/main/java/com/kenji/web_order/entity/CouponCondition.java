package com.kenji.web_order.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "coupon_conditions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attribute", length = 100)
    private String attribute; //thuộc tính để xét áp coupon: quantity, size, ...

    @Column(name = "operator", length = 100)
    private String operator; //toán tử so sánh với value: >, <, =, ...

    @Column(name = "value", length = 100)
    private String value; //giá trị để so có đủ điều kiện giảm giá: >50k, =19k, ...

    @Column(name = "discount_amount")
    private BigDecimal discountAmount; //tiền được giảm

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
}
