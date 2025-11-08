package com.kenji.web_order.service.user;

import com.kenji.web_order.dto.request.user.UserCreationRequest;
import com.kenji.web_order.dto.request.user.UserUpdateRequest;
import com.kenji.web_order.dto.response.UserResponse;
import com.kenji.web_order.exception.AppException;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreationRequest request) throws AppException;

    UserResponse getUser(Long id) throws AppException;

    UserResponse getUserDetailsFromToken(String token) throws AppException;

    UserResponse updateUser(Long userId, UserUpdateRequest request) throws AppException;

    void deleteUser(Long userId);

    UserResponse getMyInfo();

    List<UserResponse> findAllUsers();

    void resetPassword(Long userId, String newPassword) throws AppException;

    void blockOrEnable(Long userId, Boolean active) throws AppException;
}
