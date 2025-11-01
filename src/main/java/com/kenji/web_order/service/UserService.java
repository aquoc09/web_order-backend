package com.kenji.web_order.service;

import com.kenji.web_order.dto.request.UserCreationRequest;
import com.kenji.web_order.dto.request.UserUpdateRequest;
import com.kenji.web_order.dto.response.UserResponse;
import com.kenji.web_order.entity.User;
import com.kenji.web_order.exception.AppException;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreationRequest request) throws AppException;

    UserResponse getUser(Long id) throws AppException;

    UserResponse getUserDetailsFromToken(String token) throws AppException;

    UserResponse updateUser(Long userId, UserUpdateRequest request) throws AppException;

    List<UserResponse> findAllUsers();

    void resetPassword(Long userId, String newPassword) throws AppException;

    void blockOrEnable(Long userId, Boolean active) throws AppException;
}
