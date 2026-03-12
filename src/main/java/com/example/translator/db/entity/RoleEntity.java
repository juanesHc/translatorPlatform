package com.example.translator.db.entity;

import com.example.translator.db.entity.enums.PersonRoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private PersonRoleEnum type;

}
