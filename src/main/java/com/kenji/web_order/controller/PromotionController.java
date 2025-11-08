package com.kenji.web_order.controller;

import com.kenji.web_order.dto.request.PromotionRequest;
import com.kenji.web_order.dto.response.ApiResponse;
import com.kenji.web_order.dto.response.PromotionResponse;
import com.kenji.web_order.service.promotion.PromotionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequestMapping("/promotions")
public class PromotionController {

    @Autowired
    PromotionService promotionService;

    @PostMapping
    ApiResponse<PromotionResponse> create(@RequestBody PromotionRequest request){
        return ApiResponse.<PromotionResponse>builder()
                .result(promotionService.createPromotion(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PromotionResponse>> findAll(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "5") int limit){
        Pageable pageable = PageRequest.of(page, limit);
        return ApiResponse.<List<PromotionResponse>>builder()
                .result(promotionService.findAll(pageable).stream().toList())
                .build();
    }

    @GetMapping("/in-stock")
    ApiResponse<List<PromotionResponse>> findInSockPromotions(@RequestParam(defaultValue = "5") int limit){
        return ApiResponse.<List<PromotionResponse>>builder()
                .result(promotionService.findInStockPromotions(limit))
                .build();
    }

    @GetMapping("/{promotionId}")
    ApiResponse<PromotionResponse> get(@PathVariable Long promotionId){
        return ApiResponse.<PromotionResponse>builder()
                .result(promotionService.getPromotion(promotionId))
                .build();
    }

    @DeleteMapping("/{promotionId}")
    ApiResponse<String> delete(@PathVariable Long promotionId){
        promotionService.deletePromotion(promotionId);
        return ApiResponse.<String>builder()
                .message("Promotion has been deleted")
                .build();
    }

    @PutMapping("/{promotionId}")
    ApiResponse<PromotionResponse> update(@PathVariable Long promotionId,@RequestBody PromotionRequest request){
        return ApiResponse.<PromotionResponse>builder()
                .result(promotionService.updatePromotion(promotionId,request))
                .build();
    }
}
