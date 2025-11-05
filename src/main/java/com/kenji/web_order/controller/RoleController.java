package com.kenji.web_order.controller;


import com.kenji.web_order.dto.request.RoleRequest;
import com.kenji.web_order.dto.response.ApiResponse;
import com.kenji.web_order.dto.response.RoleResponse;
import com.kenji.web_order.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequestMapping("/roles")
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.createRole(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> findAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.findAllRoles())
                .build();
    }

    @GetMapping("/{role}")
    ApiResponse<RoleResponse> get(@PathVariable String role) {
        return ApiResponse.<RoleResponse>builder().result(roleService.getRole(role)).build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<String> delete(@PathVariable String role) {
        roleService.deleteRole(role);
        return ApiResponse.<String>builder().result("Role has been deleted").build();
    }
}
