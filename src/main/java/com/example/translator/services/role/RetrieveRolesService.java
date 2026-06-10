package com.example.translator.services.role;

import com.example.translator.dto.role.RoleDto;
import com.example.translator.entity.RoleEntity;
import com.example.translator.mapper.role.RoleMapper;
import com.example.translator.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetrieveRolesService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public List<RoleDto> retrieveRoles(){
        List<RoleEntity> roleEntities=roleRepository.findAll();
        return roleMapper.toDtoList(roleEntities);
    }

}
