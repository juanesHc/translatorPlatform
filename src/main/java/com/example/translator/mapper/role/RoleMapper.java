package com.example.translator.mapper.role;

import com.example.translator.dto.role.RoleDto;
import com.example.translator.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(source = "type", target = "roleType")
    RoleDto toDto(RoleEntity roleEntity);

    List<RoleDto> toDtoList(List<RoleEntity> roleEntities);
}