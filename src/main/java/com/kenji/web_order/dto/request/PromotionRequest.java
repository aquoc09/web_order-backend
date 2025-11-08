package com.kenji.web_order.dto.request;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionRequest {
    String title;
    String img;
    boolean inStock;

    @Override
    public String toString() {
        return "PromotionRequest{" +
                "title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", inStock=" + inStock +
                '}';
    }
}
