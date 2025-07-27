package org.example.bankcards.controller;

import org.example.bankcards.dto.RoleDto;
import org.example.bankcards.entity.RoleEntity;
import org.example.bankcards.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для {@link RoleEntity}
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
        final RoleDto createdRole = roleService.createRole(roleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        final List<RoleDto> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long id) {
        final RoleDto role = roleService.getRole(id);
        return ResponseEntity.ok(role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> updateRole(@RequestBody RoleDto roleDto) {
        final RoleDto updatedRole = roleService.updateRole(roleDto);
        return ResponseEntity.ok(updatedRole);
    }
}
