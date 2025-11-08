package com.kenji.web_order.dto.request;

import com.kenji.web_order.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressRequest {
     String phoneNumber;
     String email;
     String addressInfo;
     String userId;
}
