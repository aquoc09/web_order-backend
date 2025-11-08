package com.kenji.web_order.dto.request;

import com.kenji.web_order.entity.Order;
import com.kenji.web_order.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailRequest {
    float price;
    int numProducts;
    float totalMoney;
    String size;
    Long orderId;
    Long productId;
}
