package com.kenji.web_order.mapper;
import com.kenji.web_order.dto.request.UserCreationRequest;
import com.kenji.web_order.dto.request.UserUpdateRequest;
import com.kenji.web_order.dto.response.UserResponse;
import com.kenji.web_order.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
