package com.kenji.web_order.service;

import com.kenji.web_order.dto.request.CategoryRequest;
import com.kenji.web_order.dto.request.RoleRequest;
import com.kenji.web_order.dto.response.CategoryResponse;
import com.kenji.web_order.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
     RoleResponse createRole(RoleRequest request);

     RoleResponse getRole(String roleId);

     List<RoleResponse> findAllRoles();

     void deleteRole(String roleId);

     RoleResponse updateRole(String roleId, RoleRequest request);
}
