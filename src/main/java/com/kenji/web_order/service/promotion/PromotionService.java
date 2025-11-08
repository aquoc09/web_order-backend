package com.kenji.web_order.service.promotion;

import com.kenji.web_order.dto.request.ProductRequest;
import com.kenji.web_order.dto.request.PromotionRequest;
import com.kenji.web_order.dto.response.PromotionResponse;
import com.kenji.web_order.dto.response.product.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PromotionService {
    PromotionResponse createPromotion(PromotionRequest request);

    List<PromotionResponse> findInStockPromotions(int limit);

    Page<PromotionResponse> findAll(Pageable pageable);

    PromotionResponse getPromotion(Long promotionId);

    void deletePromotion(Long promotionId);

    PromotionResponse updatePromotion(Long promotionId, PromotionRequest request);

}
