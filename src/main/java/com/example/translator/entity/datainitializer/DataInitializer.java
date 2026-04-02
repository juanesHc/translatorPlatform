package com.example.translator.entity.datainitializer;

import com.example.translator.entity.RoleEntity;
import com.example.translator.entity.enums.PersonRoleEnum;
import com.example.translator.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        Arrays.stream(PersonRoleEnum.values()).forEach(roleType -> {

            if (!roleRepository.existsByType(roleType)) {
                RoleEntity newRole = new RoleEntity();
                newRole.setType(roleType);
                roleRepository.save(newRole);

               log.info("roles inserted");
            }
        });
    }
}
