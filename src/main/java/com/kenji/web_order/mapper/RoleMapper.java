package com.kenji.web_order.mapper;

import com.kenji.web_order.dto.request.ProductRequest;
import com.kenji.web_order.dto.request.RoleRequest;
import com.kenji.web_order.dto.response.ProductResponse;
import com.kenji.web_order.dto.response.RoleResponse;
import com.kenji.web_order.entity.Product;
import com.kenji.web_order.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

    void updateRole(@MappingTarget Role role, RoleRequest request);
}
