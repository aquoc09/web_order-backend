package com.kenji.web_order.mapper;

import com.kenji.web_order.dto.request.ProductRequest;
import com.kenji.web_order.dto.response.ProductResponse;
import com.kenji.web_order.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductRequest request);

    ProductResponse toProductResponse(Product product);

    void updateProduct(@MappingTarget Product product, ProductRequest request);
}
