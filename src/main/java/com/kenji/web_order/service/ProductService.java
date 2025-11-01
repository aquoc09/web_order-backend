package com.kenji.web_order.service;

import com.kenji.web_order.dto.request.ProductRequest;
import com.kenji.web_order.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    public ProductResponse createProduct(ProductRequest request);

    public ProductResponse getProduct(Long productId);

    //@PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<ProductResponse> findAllProducts();

    public void deleteProduct(Long productId);

    public ProductResponse updateProduct(Long productId, ProductRequest request);
}
