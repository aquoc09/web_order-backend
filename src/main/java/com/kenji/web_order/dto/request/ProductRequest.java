package com.kenji.web_order.dto.request;

import com.kenji.web_order.entity.Category;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    String name;
    String productCode;
    String img;
    Map<String, BigDecimal> prices;

    String categoryId;

    boolean inStock;
    boolean popular;
}
