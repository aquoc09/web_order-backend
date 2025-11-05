package com.kenji.web_order.service.Impl;

import com.kenji.web_order.dto.request.RoleRequest;
import com.kenji.web_order.dto.response.RoleResponse;
import com.kenji.web_order.entity.Role;
import com.kenji.web_order.exception.AppException;
import com.kenji.web_order.exception.ErrorCode;
import com.kenji.web_order.mapper.RoleMapper;
import com.kenji.web_order.repository.RoleRepository;
import com.kenji.web_order.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleServiceImp implements RoleService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RoleMapper roleMapper;

    @Override
    public RoleResponse createRole(RoleRequest request) {
        roleRepository.findById(request.getName()).orElseThrow(() -> new AppException(ErrorCode.ROLE_EXISTED));

        Role user = roleMapper.toRole(request);

        return roleMapper.toRoleResponse(roleRepository.save(user));
    }

    @Override
    public RoleResponse getRole(String roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_EXISTED));
        return roleMapper.toRoleResponse(role);
    }

    @Override
    public List<RoleResponse> findAllRoles() {
        List<RoleResponse> roleResponses = new ArrayList<>();
        roleRepository.findAll()
                .forEach(role -> roleResponses.add(roleMapper.toRoleResponse(role)));
        return roleResponses;
    }

    @Override
    public void deleteRole(String roleId) {
        roleRepository.deleteById(roleId);
    }

    @Override
    public RoleResponse updateRole(String roleId, RoleRequest request) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        roleMapper.updateRole(role, request);

        return roleMapper.toRoleResponse(roleRepository.save(role));
    }
}
