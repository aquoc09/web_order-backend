package com.kenji.web_order.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.Month;

@Entity
@Table(name = "revenues")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Revenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "revenue_date")
    private LocalDate date;

    @Column(name = "revenue_month")
    private Month month;

    @Column(name = "total_money")
    private float totalMoney;

    @Column(name = "total_order")
    private int totalOrder;
}
