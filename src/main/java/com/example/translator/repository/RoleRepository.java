package com.example.translator.repository;

import com.example.translator.db.entity.RoleEntity;
import com.example.translator.db.entity.enums.PersonRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {

    Optional<RoleEntity> findByType(PersonRoleEnum type);

}
