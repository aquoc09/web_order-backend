package com.kenji.web_order.service.Impl;

import com.kenji.web_order.dto.request.ProductRequest;
import com.kenji.web_order.dto.response.ProductResponse;
import com.kenji.web_order.entity.Category;
import com.kenji.web_order.entity.Product;
import com.kenji.web_order.exception.AppException;
import com.kenji.web_order.exception.ErrorCode;
import com.kenji.web_order.mapper.ProductMapper;
import com.kenji.web_order.repository.CategoryRepository;
import com.kenji.web_order.repository.ProductRepository;
import com.kenji.web_order.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductMapper productMapper;

    public ProductResponse createProduct(ProductRequest request){

        Product product = productMapper.toProduct(request);
        System.out.println(request.toString());
        System.out.println(product.toString());
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        product.setCategory(category);

        Product saved = productRepository.save(product);
        return productMapper.toProductResponse(saved);
    }

    public ProductResponse getProduct(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        return productMapper.toProductResponse(product);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public List<ProductResponse> findAllProducts(){
        List<ProductResponse> productResponses = new ArrayList<>();
        productRepository.findAll().forEach(product -> productResponses.add(productMapper.toProductResponse(product)));
        return productResponses;
    }

    public void deleteProduct(Long productId){
        productRepository.deleteById(productId);
    }

    public ProductResponse updateProduct(Long productId, ProductRequest request){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        productMapper.updateProduct(product, request);

        return productMapper.toProductResponse(productRepository.save(product));
    }
}
