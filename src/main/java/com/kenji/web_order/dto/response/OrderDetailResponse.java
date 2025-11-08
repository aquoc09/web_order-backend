package com.kenji.web_order.dto.response;

import com.kenji.web_order.dto.response.product.ProductResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    Long id;
    float price;
    int numProducts;
    float totalMoney;
    String size;
    String orderId;
    ProductResponse product;
}
