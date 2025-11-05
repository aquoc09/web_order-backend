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
    String productImage;
    Map<String, BigDecimal> prices;
    String description;

    Long categoryId;

    boolean inStock;
    boolean inPopular;

    @Override
    public String toString() {
        return "ProductRequest{" +
                "name='" + name + '\'' +
                ", productCode='" + productCode + '\'' +
                ", img='" + productImage + '\'' +
                ", prices=" + prices +
                ", categoryId='" + categoryId + '\'' +
                ", inStock=" + inStock +
                ", popular=" + inPopular +
                '}';
    }
}
