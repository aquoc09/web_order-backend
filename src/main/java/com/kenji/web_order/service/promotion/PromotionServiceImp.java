package com.kenji.web_order.service.promotion;

import com.kenji.web_order.dto.request.PromotionRequest;
import com.kenji.web_order.dto.response.PromotionResponse;
import com.kenji.web_order.entity.Promotion;
import com.kenji.web_order.exception.AppException;
import com.kenji.web_order.exception.ErrorCode;
import com.kenji.web_order.mapper.PromotionMapper;
import com.kenji.web_order.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class PromotionServiceImp implements PromotionService {
    @Autowired
    PromotionMapper promotionMapper;
    @Autowired
    PromotionRepository promotionRepository;

    @Override
    public PromotionResponse createPromotion(PromotionRequest request) {
        Promotion promotion = promotionMapper.toPromotion(request);
        System.out.println("Promotion request: "+request.toString());
        System.out.println("Promotion: "+promotion.toString());
        return promotionMapper
                .toPromotionResponse(promotionRepository.save(promotion));
    }

    @Override
    public List<PromotionResponse> findInStockPromotions(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<Promotion> promotions = promotionRepository.findByInStockTrue(pageable);
        return promotions
                .stream()
                .map(promotion -> promotionMapper.toPromotionResponse(promotion))
                .toList();
    }

    @Override
    public Page<PromotionResponse> findAll(Pageable pageable) {
        return promotionRepository
                .findAll(pageable)
                .map(promotion -> promotionMapper.toPromotionResponse(promotion));
    }

    @Override
    public PromotionResponse getPromotion(Long promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_EXISTED));
        return promotionMapper.toPromotionResponse(promotion);
    }

    @Override
    public void deletePromotion(Long promotionId) {
        promotionRepository.deleteById(promotionId);
    }

    @Override
    public PromotionResponse updatePromotion(Long promotionId, PromotionRequest request) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_EXISTED));

        promotionMapper.updatePromotion(promotion, request);

        return promotionMapper.toPromotionResponse(promotionRepository.save(promotion));
    }
}
