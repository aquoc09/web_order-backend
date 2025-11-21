package com.kenji.web_order.mapper;

import com.kenji.web_order.dto.request.CartItemRequest;
import com.kenji.web_order.dto.response.CartItemResponse;
import com.kenji.web_order.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "id", ignore = true)
    CartItem toCartItem(CartItemRequest request);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "cartId", source = "cart.id")
    CartItemResponse toCartItemResponse(CartItem cartItem);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "cart", ignore = true)
    void updateCartItem(@MappingTarget CartItem cartItem, CartItemRequest request);
}
