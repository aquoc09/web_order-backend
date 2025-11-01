package com.kenji.web_order.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenji.web_order.entity.Role;
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
public class UserUpdateRequest {
    @JsonProperty("full_name")
    private String fullName;

    @Size(min = 8, message = "INVALID_PASSWORD")
    private String password;

    @Email(message = "INVALID_EMAIL")
    private String email;

    private String phone;

    private Gender gender;

    @JsonProperty("facebook_account_id")
    private Integer facebookAccountId;

    @JsonProperty("google_account_id")
    private Integer googleAccountId;
}
