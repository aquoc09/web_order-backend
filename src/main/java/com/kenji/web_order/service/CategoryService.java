package com.kenji.web_order.service;

import com.kenji.web_order.dto.request.CategoryRequest;
import com.kenji.web_order.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
     CategoryResponse createCategory(CategoryRequest request);

     CategoryResponse getCategory(Long categoryId);

     List<CategoryResponse> findAllCategories();

     void deleteCategory(Long categoryId);

     CategoryResponse updateCategory(Long categoryId, CategoryRequest request);
}
