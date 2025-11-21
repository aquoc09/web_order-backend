package com.kenji.web_order.dto.response;

import com.kenji.web_order.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
     Long id;
     boolean active;
     UserResponse user;
     List<CartItemResponse> cartItems;
}
