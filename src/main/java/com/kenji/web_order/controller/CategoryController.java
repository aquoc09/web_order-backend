package com.kenji.web_order.controller;

import com.kenji.web_order.dto.request.CategoryRequest;
import com.kenji.web_order.dto.response.ApiResponse;
import com.kenji.web_order.dto.response.CategoryResponse;
import com.kenji.web_order.service.category.CategoryServiceImp;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    CategoryServiceImp categoryService;

    @PostMapping
    ApiResponse<CategoryResponse> create(@RequestBody CategoryRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.createCategory(request))
                .build();
    }

    @GetMapping
    List<ApiResponse<CategoryResponse>> getAll() {
        List<ApiResponse<CategoryResponse>> apiResponses = new ArrayList<>();
        categoryService.findAllCategories().forEach(categoryResponse -> apiResponses.add(
                ApiResponse.<CategoryResponse>builder()
                        .result(categoryResponse)
                        .build()));
        return apiResponses;
    }

    @GetMapping("/{categoryId}")
    ApiResponse<CategoryResponse> get(@PathVariable Long categoryId) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.getCategory(categoryId))
                .build();
    }

    @DeleteMapping("/{categoryId}")
    ApiResponse<String> delete(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ApiResponse.<String>builder()
                .message("Category has been deleted")
                .build();
    }

    @PutMapping("/{categoryId}")
    ApiResponse<CategoryResponse> update(@PathVariable Long categoryId,
                                         @RequestBody CategoryRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.updateCategory(categoryId, request))
                .build();
    }


}
