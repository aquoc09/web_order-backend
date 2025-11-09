package com.kenji.web_order.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForgotPasswordRequest {
    @NotBlank
    String fullName;
    @NotBlank
    String username;
    @Email(message = "INVALID_EMAIL")
    @NotBlank
    String email;
}
