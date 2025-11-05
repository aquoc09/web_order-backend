package com.kenji.web_order.service.Impl;

import com.kenji.web_order.dto.request.CategoryRequest;
import com.kenji.web_order.dto.response.CategoryResponse;
import com.kenji.web_order.entity.Category;
import com.kenji.web_order.exception.AppException;
import com.kenji.web_order.exception.ErrorCode;
import com.kenji.web_order.mapper.CategoryMapper;
import com.kenji.web_order.repository.CategoryRepository;
import com.kenji.web_order.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper categoryMapper;

    public CategoryResponse createCategory(CategoryRequest request) {

        Category category = categoryMapper.toCategory(request);

        if(request.getParentCategory().getCategoryCode() !=null) {
            Category parentCategory = categoryRepository
                    .findByCategoryCode(request.getParentCategory().getCategoryCode())
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
            category.setParentCategory(parentCategory);
        }

        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    public CategoryResponse getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        return categoryMapper.toCategoryResponse(category);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<CategoryResponse> findAllCategories() {
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        categoryRepository.findAll().forEach(category ->
                categoryResponses.add(categoryMapper.toCategoryResponse(category))
        );
        return categoryResponses;
    }

    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public CategoryResponse updateCategory(Long categoryId, CategoryRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

        categoryMapper.updateCategory(category, request);

        if(request.getParentCategory().getCategoryCode() !=null) {
            Category parentCategory = categoryRepository
                    .findByCategoryCode(request.getParentCategory().getCategoryCode())
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
            category.setParentCategory(parentCategory);
        }

        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

}
