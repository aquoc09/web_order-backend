package com.kenji.web_order.mapper;

import com.kenji.web_order.dto.request.ProductRequest;
import com.kenji.web_order.dto.response.ProductResponse;
import com.kenji.web_order.entity.Category;
import com.kenji.web_order.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "category", ignore = true)
    Product toProduct(ProductRequest request);

    @Mapping(target = "categoryCode",
            expression = "java(mapToCategory(product.getCategory()))")
    ProductResponse toProductResponse(Product product);

    void updateProduct(@MappingTarget Product product, ProductRequest request);

    default String mapToCategory(Category category){
        if(category == null) return null;
        return category.getCategoryCode();
    }
}
