package com.example.translator.services.person;

import com.example.translator.dto.person.request.EditMyDataRequestDto;
import com.example.translator.dto.person.response.EditMyDataResponseDto;
import com.example.translator.dto.person.response.RetrieveMyDataResponseDto;
import com.example.translator.dto.person.response.RetrieveStatusAccountResponseDto;
import com.example.translator.entity.PersonEntity;
import com.example.translator.exceptions.PersonNotFoundException;
import com.example.translator.mapper.person.PersonMapper;
import com.example.translator.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MyAccountService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public RetrieveMyDataResponseDto RetrieveMyData(String personId){
        return personMapper.toRetrieveMyDataResponseDto(retrievePersonEntity(personId));
    }

    public EditMyDataResponseDto editMyData(EditMyDataRequestDto editMyDataRequestDto, String personId){
        PersonEntity personEntity=retrievePersonEntity(personId);
        if(!editMyDataRequestDto.getFirstName().isBlank()&&editMyDataRequestDto.getFirstName()!=(null)){
            personEntity.setGivenName(editMyDataRequestDto.getFirstName());
        }
        if(!editMyDataRequestDto.getLastName().isBlank()&&editMyDataRequestDto.getLastName()!=(null)){
            personEntity.setFamilyName(editMyDataRequestDto.getLastName());
        }
        personRepository.save(personEntity);
        return new EditMyDataResponseDto("Update successfully");
    }

    public RetrieveStatusAccountResponseDto changeAccountStatus(String personId){
        PersonEntity personEntity=retrievePersonEntity(personId);
        personEntity.setActivate(!personEntity.isActivate());
        personRepository.save(personEntity);

        RetrieveStatusAccountResponseDto retrieveStatusAccountResponseDto=new RetrieveStatusAccountResponseDto();
        retrieveStatusAccountResponseDto.setStatus(personEntity.isActivate());

        if(retrieveStatusAccountResponseDto.isStatus()){
            retrieveStatusAccountResponseDto.setMessage("Account successfully activated");
        }
        else {
            retrieveStatusAccountResponseDto.setMessage("Account successfully deactivated");
        }
        return retrieveStatusAccountResponseDto;
    }

    private PersonEntity retrievePersonEntity(String personId){
       return personRepository.findById(UUID.fromString(personId))
                .orElseThrow(()->new PersonNotFoundException("Couldnt found the person"));

    }

}
