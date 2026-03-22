package com.example.translator.services.person;

import com.example.translator.entity.PersonEntity;
import com.example.translator.entity.RoleEntity;
import com.example.translator.entity.enums.PersonRoleEnum;
import com.example.translator.dto.person.request.RegisterPersonRequestDto;
import com.example.translator.dto.person.response.RegisterPersonResponseDto;
import com.example.translator.exceptions.RoleNotFoundException;
import com.example.translator.mapper.person.PersonMapper;
import com.example.translator.repository.PersonRepository;
import com.example.translator.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterPersonService {

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PersonMapper personMapper;


    public RegisterPersonResponseDto registerPersonInitial(RegisterPersonRequestDto registerPersonRequestDto){
        RoleEntity roleEntity = roleRepository.findByType(PersonRoleEnum.FREE)
                .orElseThrow(() -> new RoleNotFoundException("Could not found the according role"));

        PersonEntity personEntity = personMapper.RegisterPersonRequestDtoToPersonEntity(registerPersonRequestDto);
        personEntity.setRole(roleEntity);

        personRepository.save(personEntity);

        return new RegisterPersonResponseDto("Successfully register");
    }





}
