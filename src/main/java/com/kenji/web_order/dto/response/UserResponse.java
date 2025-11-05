package com.kenji.web_order.dto.response;

import com.kenji.web_order.entity.Role;
import com.kenji.web_order.enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String fullName;
    String username;
    LocalDate dob;
    String email;
    String phone;
    Gender gender;
    Role role;
}
