package com.kenji.web_order.dto.response;

import com.kenji.web_order.dto.response.product.ProductResponse;
import com.kenji.web_order.entity.Cart;
import com.kenji.web_order.entity.Product;
import com.kenji.web_order.enums.ProductSize;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {
    Long id;
    int quantity;
    BigDecimal totalMoney;
    ProductSize size;
    String note;
    Long cartId;
    Long productId;
}
