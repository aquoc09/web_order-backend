package com.kenji.web_order.dto.response.product;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    String id;
    String name;
    String productCode;
    String productImage;
    Map<String, BigDecimal> prices;

    String categoryCode;

    boolean inStock;
    boolean popular;
}
