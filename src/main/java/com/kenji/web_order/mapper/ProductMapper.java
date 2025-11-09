package com.kenji.web_order.mapper;

import com.kenji.web_order.dto.request.ProductRequest;
import com.kenji.web_order.dto.response.product.PriceResponse;
import com.kenji.web_order.dto.response.product.ProductResponse;
import com.kenji.web_order.entity.Category;
import com.kenji.web_order.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "category", ignore = true)
    Product toProduct(ProductRequest request);

    @Mapping(target = "categoryCode",
            expression = "java(mapToCategory(product.getCategory()))")
    @Mapping(target = "prices",
            expression = "java(mapPrices(product.getPrices()))")
    ProductResponse toProductResponse(Product product);

    void updateProduct(@MappingTarget Product product, ProductRequest request);

    default String mapToCategory(Category category){
        if(category == null) return null;
        return category.getCategoryCode();
    }

    default List<PriceResponse> mapPrices(Map<String, BigDecimal> prices) {
        if (prices == null) return Collections.emptyList();

        return prices.entrySet().stream()
                .map(entry -> new PriceResponse(entry.getKey(), entry.getValue()))
                .toList();
    }
}
