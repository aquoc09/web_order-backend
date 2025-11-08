package com.kenji.web_order.entity;

import com.kenji.web_order.enums.OrderStatus;
import com.kenji.web_order.enums.PaymentMethod;
import com.kenji.web_order.enums.ShippingMethod;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "note", length = 200)
    private String note;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "order_status")
    private OrderStatus status;

    @Column(name = "total_money")
    private float totalMoney;

    @Column(name = "shipping_method")
    private ShippingMethod shippingMethod;

    @Column(name = "shipping_date")
    private LocalDate shippingDate;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "is_active")
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails;
}
