package com.kenji.web_order.dto.response;

import com.kenji.web_order.dto.request.CategoryRequest;
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
    String productId;
    String name;
    String productCode;
    String img;
    Map<String, BigDecimal> prices;

    CategoryResponse category;

    boolean inStock;
    boolean popular;
}
