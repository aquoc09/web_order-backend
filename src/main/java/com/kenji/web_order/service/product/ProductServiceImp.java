package com.kenji.web_order.service.product;

import com.kenji.web_order.dto.request.ProductRequest;
import com.kenji.web_order.dto.response.product.ProductResponse;
import com.kenji.web_order.entity.Category;
import com.kenji.web_order.entity.Product;
import com.kenji.web_order.entity.User;
import com.kenji.web_order.exception.AppException;
import com.kenji.web_order.exception.ErrorCode;
import com.kenji.web_order.mapper.ProductMapper;
import com.kenji.web_order.repository.CategoryRepository;
import com.kenji.web_order.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

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

    public Page<ProductResponse> findAllProducts(String keyword, Long categoryId, PageRequest pageRequest) {
        // Lấy danh sách sản phẩm theo trang (page), giới hạn (limit), và categoryId (nếu có)
        Page<Product> productsPage;
        productsPage = productRepository.searchProducts(categoryId, keyword, pageRequest);
        return productsPage
                .map(product -> productMapper.toProductResponse(product));
    }

    public List<ProductResponse> findByCategoryCode(PageRequest pageRequest, String categoryCode) {
        List<Product> products = productRepository.findByCategoryCode(pageRequest, categoryCode);
        return products
                .stream()
                .map(product -> productMapper.toProductResponse(product))
                .toList();
    }

    public List<ProductResponse> findLatestProducts(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return productRepository.findLatestProducts(pageable)
                .stream()
                .map(product -> productMapper.toProductResponse(product))
                .toList();
    }

    public List<ProductResponse> getPopularProducts(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return productRepository.findByInPopularTrue(pageable)
                .stream()
                .map(product -> productMapper.toProductResponse(product))
                .toList();
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
