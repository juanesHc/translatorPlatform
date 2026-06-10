package com.example.translator.controller.role;

import com.example.translator.dto.role.RoleDto;
import com.example.translator.services.role.RetrieveRolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role")
public class RoleController {

    private final RetrieveRolesService retrieveRolesService;

    @GetMapping("/retrieve")
    public ResponseEntity<List<RoleDto>> getRoles(){
        return ResponseEntity.ok(retrieveRolesService.retrieveRoles());
    }

}
