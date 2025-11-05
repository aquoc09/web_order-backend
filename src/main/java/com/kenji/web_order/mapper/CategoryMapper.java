package com.kenji.web_order.mapper;

import com.kenji.web_order.dto.request.CategoryRequest;
import com.kenji.web_order.dto.response.CategoryResponse;
import com.kenji.web_order.entity.Category;
import com.kenji.web_order.exception.AppException;
import com.kenji.web_order.exception.ErrorCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "parentCategory", ignore = true)
    Category toCategory(CategoryRequest request);

    @Mapping(target = "parentCategory",
            expression = "java(mapParentCategory(category))")
    @Mapping(target = "subCategories",
            expression = "java(mapSubCategories(category.getSubCategories()))")
    CategoryResponse toCategoryResponse(Category category);

    @Mapping(target = "parentCategory", ignore = true)
    void updateCategory(@MappingTarget Category category, CategoryRequest request);

    default List<String> mapSubCategories(List<Category> subCategories) {
        if (subCategories == null) return null;
        return subCategories.stream()
                .map(Category::getCategoryCode)
                .toList();
    }

    default String mapParentCategory(Category category){
        return category.getParentCategory() != null ?
                category.getParentCategory().getCategoryCode()
                : null;
    }
}
