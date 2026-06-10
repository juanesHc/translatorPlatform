package com.example.translator.services.person;

import com.example.translator.dto.messaging.request.SendBlockMessageRequestDto;
import com.example.translator.dto.person.request.EditMyDataRequestDto;
import com.example.translator.dto.person.response.EditMyDataResponseDto;
import com.example.translator.dto.person.response.RetrieveMyDataResponseDto;
import com.example.translator.dto.person.response.RetrieveStatusAccountResponseDto;
import com.example.translator.entity.PersonEntity;
import com.example.translator.entity.TokenEntity;
import com.example.translator.entity.enums.TokenTypeEnum;
import com.example.translator.exceptions.AccountRecoveryException;
import com.example.translator.exceptions.PersonNotFoundException;
import com.example.translator.mapper.person.PersonMapper;
import com.example.translator.repository.PersonRepository;
import com.example.translator.services.messaging.impl.MessagingService;
import com.example.translator.services.security.token.account.AccountRecoveryService;
import com.example.translator.services.security.token.impl.TokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyAccountService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final MessagingService messagingService;
    private final AccountRecoveryService accountRecoveryService;
    private final TokenService tokenService;

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

    @Transactional
    public void recoverAccount(String tokenValue) {
        TokenEntity token = tokenService.validateToken(tokenValue, TokenTypeEnum.ACCOUNT_RECOVERY);

        PersonEntity person = personRepository.findById(token.getPerson().getId())
                .orElseThrow(() -> new AccountRecoveryException("Usuario no encontrado"));

        if (person.isActivate()) {
            tokenService.removeToken(token);
            throw new AccountRecoveryException("La cuenta ya estaba activa");
        }

        person.setActivate(true);
        personRepository.save(person);
        tokenService.removeToken(token);

        log.info("Cuenta recuperada exitosamente para {}", person.getEmail());
    }

    public RetrieveStatusAccountResponseDto blockAccount(String personId){

        PersonEntity personEntity=retrievePersonEntity(personId);

        messagingService.sendBlockEmail(new SendBlockMessageRequestDto(personEntity.getGivenName(),
                personEntity.getEmail(),
                "Your account will be block soon"));

        personEntity.setBlock(!personEntity.isBlock());
        personRepository.save(personEntity);

        RetrieveStatusAccountResponseDto retrieveStatusAccountResponseDto=new RetrieveStatusAccountResponseDto();
        retrieveStatusAccountResponseDto.setStatus(personEntity.isActivate());

        if(retrieveStatusAccountResponseDto.isStatus()){
            retrieveStatusAccountResponseDto.setMessage("Account successfully unblocked");
        }
        else {
            retrieveStatusAccountResponseDto.setMessage("Account successfully blocked");
        }
        return retrieveStatusAccountResponseDto;
    }

    private PersonEntity retrievePersonEntity(String personId){
       return personRepository.findById(UUID.fromString(personId))
                .orElseThrow(()->new PersonNotFoundException("Couldnt found the person"));

    }

}
