package com.kenji.web_order.controller;

import com.kenji.web_order.dto.request.ProductRequest;
import com.kenji.web_order.dto.response.ApiResponse;
import com.kenji.web_order.dto.response.product.ProductListResponse;
import com.kenji.web_order.dto.response.product.ProductResponse;
import com.kenji.web_order.service.product.ProductServiceImp;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

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
    ApiResponse<ProductListResponse> getProducts(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name = "category_id") Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ){
        PageRequest pageRequest = PageRequest.of(page,limit);
        Page<ProductResponse> productPage = productService.findAllProducts(
                keyword,
                categoryId,
                pageRequest);
        int totalPages = productPage.getTotalPages();
        List<ProductResponse> productResponses = productPage.getContent();

        ProductListResponse productListResponse = ProductListResponse.builder()
                .totalPages(totalPages)
                .products(productResponses)
                .build();

        return ApiResponse.<ProductListResponse>builder()
                .result(productListResponse)
                .build();
    }

    @GetMapping("/category-code/{categoryCode}")
    ApiResponse<List<ProductResponse>> getProductByCategoryCode(
            @RequestParam(defaultValue = "5") int limit,
            @PathVariable String categoryCode
    ){
        PageRequest pageRequest = PageRequest.of(0,limit);
        List<ProductResponse> productResponses = productService.findByCategoryCode(pageRequest, categoryCode);
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productResponses)
                .build();
    }

    @GetMapping("/latest-products")
    ApiResponse<List<ProductResponse>> getLatestProducts(@RequestParam(defaultValue = "5") int limit){
        List<ProductResponse> productResponses = productService.findLatestProducts(limit);
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productResponses)
                .build();
    }

    @GetMapping("/popular-products")
    ApiResponse<List<ProductResponse>> getPopularProducts(@RequestParam(defaultValue = "5") int limit){
        List<ProductResponse> productResponses = productService.getPopularProducts(limit);
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productResponses)
                .build();
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
