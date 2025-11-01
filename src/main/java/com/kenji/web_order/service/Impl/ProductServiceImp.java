package com.kenji.web_order.service.Impl;

import com.kenji.web_order.dto.request.ProductRequest;
import com.kenji.web_order.dto.response.ProductResponse;
import com.kenji.web_order.entity.Product;
import com.kenji.web_order.exception.AppException;
import com.kenji.web_order.exception.ErrorCode;
import com.kenji.web_order.mapper.ProductMapper;
import com.kenji.web_order.repository.ProductRepository;
import com.kenji.web_order.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    public ProductResponse createProduct(ProductRequest request){

        Product product = productMapper.toProduct(request);

        Product saved = productRepository.save(product);
        return productMapper.toProductResponse(saved);
    }

    public ProductResponse getProduct(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        return productMapper.toProductResponse(product);
    }

    //@PostAuthorize("returnObject.productname == authentication.name")
//    public ProductResponse getMyInfo() {
//        var context = SecurityContextHolder.getContext();
//        String name = context.getAuthentication().getName();
//
//        product product = productRepository.findByproductname(name).orElseThrow(() -> new AppException(ErrorCode.product_NOT_EXISTED));
//
//        return productMapper.toProductResponse(product);
//    }

    //@PreAuthorize("hasAnyRole('ADMIN','HR')")
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
