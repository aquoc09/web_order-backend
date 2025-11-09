package com.kenji.web_order.service.user;

import com.kenji.web_order.dto.request.user.UserCreationRequest;
import com.kenji.web_order.dto.request.user.UserUpdateRequest;
import com.kenji.web_order.dto.response.UserResponse;
import com.kenji.web_order.entity.Role;
import com.kenji.web_order.entity.Token;
import com.kenji.web_order.entity.User;
import com.kenji.web_order.enums.RoleEnum;
import com.kenji.web_order.exception.AppException;
import com.kenji.web_order.exception.ErrorCode;
import com.kenji.web_order.mapper.UserMapper;
import com.kenji.web_order.repository.RoleRepository;
import com.kenji.web_order.repository.TokenRepository;
import com.kenji.web_order.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;

    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    TokenRepository tokenRepository;



    @Override
    public UserResponse createUser(UserCreationRequest request) throws AppException {
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null) {
            log.info("Role has been not set yet!");
            Role role = roleRepository.findById(RoleEnum.USER.toString())
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
            user.setRole(role);
        }
        user.setActive(true);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse getUser(Long id) throws AppException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getUserDetailsFromToken(String token) throws AppException {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Long userId, UserUpdateRequest request) throws AppException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> findAllUsers() {
        List<UserResponse> userResponses = new ArrayList<>();
        userRepository.findAll().forEach(user -> userResponses.add(userMapper.toUserResponse(user)));
        return userResponses;
    }

    @Override
    public void resetPassword(Long userId, String newPassword) throws AppException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        //reset all token
        List<Token> tokens = tokenRepository.findAllByUser(user)
                .orElseThrow((() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
        tokenRepository.deleteAll(tokens);
    }

    @Override
    public void blockOrEnable(Long userId, Boolean active) throws AppException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        user.setActive(active);
        userRepository.save(user);
    }
}
