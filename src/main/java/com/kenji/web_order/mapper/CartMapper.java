package com.kenji.web_order.mapper;

import com.kenji.web_order.dto.response.CartResponse;
import com.kenji.web_order.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { CartItemMapper.class, UserMapper.class })
public interface CartMapper {
    @Mapping(target = "cartItems", source = "cartItems")
    CartResponse toCartResponse(Cart cart);
}
