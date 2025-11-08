package com.kenji.web_order.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    @Size(min = 5, message = "INVALID_USERNAME")
    @NotBlank(message = "BLANK_DATA")
    private String username;

//    @Size(min = 8, message = "INVALID_PASSWORD")
    private String password;
}
