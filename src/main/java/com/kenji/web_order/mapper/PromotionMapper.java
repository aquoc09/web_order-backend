package com.kenji.web_order.mapper;

import com.kenji.web_order.dto.request.PromotionRequest;
import com.kenji.web_order.dto.request.user.UserCreationRequest;
import com.kenji.web_order.dto.request.user.UserUpdateRequest;
import com.kenji.web_order.dto.response.PromotionResponse;
import com.kenji.web_order.dto.response.UserResponse;
import com.kenji.web_order.entity.Promotion;
import com.kenji.web_order.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PromotionMapper {
    Promotion toPromotion(PromotionRequest request);

    PromotionResponse toPromotionResponse(Promotion promotion);

    void updatePromotion(@MappingTarget Promotion promotion, PromotionRequest request);
}
