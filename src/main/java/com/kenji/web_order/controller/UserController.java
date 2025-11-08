package com.kenji.web_order.controller;

import com.kenji.web_order.dto.request.user.UserCreationRequest;
import com.kenji.web_order.dto.request.user.UserUpdateRequest;
import com.kenji.web_order.dto.response.ApiResponse;
import com.kenji.web_order.dto.response.UserResponse;
import com.kenji.web_order.service.user.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/users")
public class UserController {
    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> create(@Valid @RequestBody UserCreationRequest request){
        System.out.println("Dob from request: "+ request.getDob());
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping
    List<ApiResponse<UserResponse>> getAll(){
        List<ApiResponse<UserResponse>> apiResponses = new ArrayList<>();
        userService.findAllUsers().forEach(userResponse -> apiResponses.add(
                ApiResponse.<UserResponse>builder()
                        .result(userResponse)
                        .build()));
        return apiResponses;
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> get(@PathVariable Long userId){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo(){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> delete(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ApiResponse.<String>builder()
                .message("User has been deleted")
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> update(@PathVariable Long userId, @RequestBody @Valid UserUpdateRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }
}
