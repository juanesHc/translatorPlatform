package com.example.translator.services.person;

import com.example.translator.dto.messaging.request.SendVerificationRequestDto;
import com.example.translator.dto.person.request.RegisterClassicPersonRequestDto;
import com.example.translator.dto.person.request.RegisterPersonWithRoleRequestDto;
import com.example.translator.dto.person.response.RegisterClassicPersonResponseDto;
import com.example.translator.dto.person.response.RegisterPersonWithRoleResponseDto;
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
import com.example.translator.services.messaging.impl.MessagingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterPersonService {

    private final PasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PersonMapper personMapper;
    private final MessagingService messagingService;


    public RegisterGooglePersonResponseDto registerGooglePerson(RegisterGooglePersonRequestDto registerGooglePersonRequestDto){

        PersonEntity personEntity=personMapper.RegisterPersonGoogleRequestDtoToPersonEntity(registerGooglePersonRequestDto);
        PersonEntity personToSave=summarySavePersonEntity(personEntity,
        null,null,
                PersonRoleEnum.COMMON,
                AuthEnum.CLASSIC);
        personEntity.setVerify(true);
        personRepository.save(personToSave);

        return new RegisterGooglePersonResponseDto("Successfully register");
    }

    public RegisterClassicPersonResponseDto registerClassicPerson(
            RegisterClassicPersonRequestDto registerClassicPersonRequestDto) {

        PersonEntity personEntity = personMapper.toEntityFromClassicRegister(registerClassicPersonRequestDto);
        PersonEntity personToSave = summarySavePersonEntity(
                personEntity,
                registerClassicPersonRequestDto.getPassword(),
                registerClassicPersonRequestDto.getConfirmPassword(),
                PersonRoleEnum.COMMON,
                AuthEnum.CLASSIC);

        personToSave.setVerify(false);
        PersonEntity savedPerson = personRepository.save(personToSave);

        SendVerificationRequestDto dto = new SendVerificationRequestDto();
        dto.setEmail(savedPerson.getEmail());
        messagingService.sendVerificationEmail(dto);

        return new RegisterClassicPersonResponseDto("User register in a successfully way");
    }

    public RegisterPersonWithRoleResponseDto registerWithRole(
            RegisterPersonWithRoleRequestDto registerPersonWithRoleRequestDto) {
        try {
            PersonEntity personEntity = personMapper.registerPersonWithRoleRequestDtoToEntity(
                    registerPersonWithRoleRequestDto);
            PersonEntity personToSave = summarySavePersonEntity(
                    personEntity,
                    registerPersonWithRoleRequestDto.getPassword(),
                    registerPersonWithRoleRequestDto.getConfirmPassword(),
                    PersonRoleEnum.valueOf(registerPersonWithRoleRequestDto.getRole()),
                    AuthEnum.CLASSIC);

            personToSave.setVerify(false);
            PersonEntity savedPerson = personRepository.save(personToSave);

            SendVerificationRequestDto dto = new SendVerificationRequestDto();
            dto.setEmail(savedPerson.getEmail());
            messagingService.sendVerificationEmail(dto);

        } catch (RegisterPersonClassicException e) {
            log.error("It run into a issue doing the register", e);
            throw new RegisterPersonClassicException("It run into a issue doing the register");
        }

        return new RegisterPersonWithRoleResponseDto("User successfully added");
    }

    private Boolean validatePassword(String password,String confirmPassword){
        return password.equals(confirmPassword);
    }

    private PersonEntity summarySavePersonEntity(PersonEntity personEntity,String password , String confirmPassword, PersonRoleEnum role, AuthEnum auth){
        if(!auth.equals(AuthEnum.GOOGLE)){
            if(!validatePassword(password,confirmPassword)){
                throw new RegisterPersonClassicException("Camps password and Confirm password dont match");
            }
            personEntity.setPassword(passwordEncoder.encode(password));
        }
        RoleEntity roleEntity = roleRepository.findByType(role)
                .orElseThrow(() -> new RoleNotFoundException("Could not found the according role"));
        personEntity.setAuthEnum(auth);
        personEntity.setActivate(true);
        personEntity.setRole(roleEntity);
        return personEntity;
    }







}
