package com.kenji.web_order.service.category;

import com.kenji.web_order.dto.request.CategoryRequest;
import com.kenji.web_order.dto.response.CategoryResponse;
import com.kenji.web_order.dto.response.product.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CategoryService {
     CategoryResponse createCategory(CategoryRequest request);

     CategoryResponse getCategory(Long categoryId);

     List<CategoryResponse> findAllCategories(String keyword,
                                           Long parentCategoryId);

     void deleteCategory(Long categoryId);

     boolean setActiveCategory(Long categoryId, CategoryRequest categoryRequest);

     CategoryResponse updateCategory(Long categoryId, CategoryRequest request);
}
