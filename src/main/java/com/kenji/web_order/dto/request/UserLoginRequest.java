package com.kenji.web_order.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenji.web_order.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLoginRequest {
    @Size(min = 5, message = "INVALID_USERNAME")
    @NotBlank(message = "BLANK_DATA")
    private String username;

    @Size(min = 8, message = "INVALID_PASSWORD")
    private String password;
}
