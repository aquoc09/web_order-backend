package com.kenji.web_order.dto.response;

import com.kenji.web_order.entity.Address;
import com.kenji.web_order.entity.OrderDetail;
import com.kenji.web_order.entity.Role;
import com.kenji.web_order.entity.User;
import com.kenji.web_order.enums.Gender;
import com.kenji.web_order.enums.OrderStatus;
import com.kenji.web_order.enums.PaymentMethod;
import com.kenji.web_order.enums.ShippingMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Long id;
    String note;
    LocalDate orderDate;
    OrderStatus status;
    float totalMoney;
    ShippingMethod shippingMethod;
    LocalDate shippingDate;
    String trackingNumber;
    PaymentMethod paymentMethod;
    boolean active;
    String userId;
    AddressResponse address;
    List<OrderDetailResponse> orderDetails;
}
