package com.kenji.web_order.service.product;

import com.kenji.web_order.dto.request.ProductRequest;
import com.kenji.web_order.dto.response.product.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProductService {
     ProductResponse createProduct(ProductRequest request);

    ProductResponse getProduct(Long productId);

    Page<ProductResponse> findAllProducts(String keyword,
                                          Long categoryId,
                                          PageRequest pageRequest);
    List<ProductResponse> findLatestProducts(int limit);

    List<ProductResponse> getPopularProducts(int limit);

    void deleteProduct(Long productId);

     ProductResponse updateProduct(Long productId, ProductRequest request);
}
