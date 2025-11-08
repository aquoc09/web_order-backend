package com.kenji.web_order.dto.request.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kenji.web_order.entity.Role;
import com.kenji.web_order.enums.Gender;
import com.kenji.web_order.validator.DobConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    String fullName;

    @Size(min = 5, message = "INVALID_USERNAME")
    @NotBlank(message = "BLANK_DATA")
    String username;

    @Size(min = 8, message = "INVALID_PASSWORD")
    String password;

    @Email(message = "INVALID_EMAIL")
    String email;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate dob;

    String phone;

    Gender gender;

    Integer facebookAccountId;

    Integer googleAccountId;

    Role role;
}
