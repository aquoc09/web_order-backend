package com.kenji.web_order.dto.response;

import com.kenji.web_order.entity.Category;
import com.kenji.web_order.enums.CategoryStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {
    Long id;
    String name;
    String categoryCode;
    CategoryStatus status;
    String parentCategory;
    List<String> subCategories;
}
