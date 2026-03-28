package com.example.translator.services.person;

import com.example.translator.dto.person.request.RegisterClassicPersonRequestDto;
import com.example.translator.dto.person.response.RegisterClassicPersonResponseDto;
import com.example.translator.entity.PersonEntity;
import com.example.translator.entity.RoleEntity;
import com.example.translator.entity.enums.AuthEnum;
import com.example.translator.entity.enums.PersonRoleEnum;
import com.example.translator.dto.person.request.RegisterGooglePersonRequestDto;
import com.example.translator.dto.person.response.RegisterGooglePersonResponseDto;
import com.example.translator.exceptions.RegisterPersonClassicException;
import com.example.translator.exceptions.RoleNotFoundException;
import com.example.translator.mapper.person.PersonMapper;
import com.example.translator.repository.PersonRepository;
import com.example.translator.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterPersonService {

    private final PasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PersonMapper personMapper;


    public RegisterGooglePersonResponseDto registerGooglePerson(RegisterGooglePersonRequestDto registerGooglePersonRequestDto){
        RoleEntity roleEntity = roleRepository.findByType(PersonRoleEnum.COMMON)
                .orElseThrow(() -> new RoleNotFoundException("Could not found the according role"));

        PersonEntity personEntity = personMapper.RegisterPersonGoogleRequestDtoToPersonEntity(registerGooglePersonRequestDto);
        personEntity.setRole(roleEntity);
        personEntity.setActivate(true);
        personEntity.setAuthEnum(AuthEnum.GOOGLE);

        personRepository.save(personEntity);

        return new RegisterGooglePersonResponseDto("Successfully register");
    }

    public RegisterClassicPersonResponseDto registerClassicPerson(RegisterClassicPersonRequestDto registerClassicPersonRequestDto){
        if(!validatePassword(registerClassicPersonRequestDto.getPassword(),registerClassicPersonRequestDto.getConfirmPassword())){
            throw new RegisterPersonClassicException("Camps password and Confirm password dont match");
        }

        PersonEntity personEntity=personMapper.toEntityFromClassicRegister(registerClassicPersonRequestDto);
        RoleEntity roleEntity=roleRepository.findByType(PersonRoleEnum.COMMON).
                orElseThrow(()->new RoleNotFoundException("It was impossible to found the role"));

        personEntity.setRole(roleEntity);
        personEntity.setAuthEnum(AuthEnum.CLASSIC);
        personEntity.setActivate(true);
        personEntity.setPassword(passwordEncoder.encode(registerClassicPersonRequestDto.getPassword()));

        personRepository.save(personEntity);
        return new RegisterClassicPersonResponseDto("User register in a successfully way");
    }

    private Boolean validatePassword(String password,String confirmPassword){
        return password.equals(confirmPassword);
    }





}
