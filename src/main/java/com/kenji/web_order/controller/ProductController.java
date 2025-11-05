package com.kenji.web_order.controller;

import com.kenji.web_order.dto.request.ProductRequest;
import com.kenji.web_order.dto.response.ApiResponse;
import com.kenji.web_order.dto.response.ProductResponse;
import com.kenji.web_order.service.Impl.ProductServiceImp;
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
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductServiceImp productService;

    @PostMapping
    ApiResponse<ProductResponse> create(@RequestBody ProductRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(request))
                .build();
    }

    @GetMapping
    List<ApiResponse<ProductResponse>> findAll() {
        List<ApiResponse<ProductResponse>> apiResponses = new ArrayList<>();
        productService.findAllProducts().forEach(productResponse -> apiResponses.add(
                ApiResponse.<ProductResponse>builder()
                        .result(productResponse)
                        .build()));
        return apiResponses;
    }

    @GetMapping("/{productId}")
    ApiResponse<ProductResponse> get(@PathVariable Long productId) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getProduct(productId))
                .build();
    }

    @DeleteMapping("/{productId}")
    ApiResponse<String> delete(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ApiResponse.<String>builder()
                .message("Product has been deleted")
                .build();
    }

    @PutMapping("/{productId}")
    ApiResponse<ProductResponse> update(@PathVariable Long productId,
                                        @RequestBody ProductRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(productId, request))
                .build();
    }

}
