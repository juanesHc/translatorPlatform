package com.example.translator.controller.person.user;

import com.example.translator.dto.person.request.EditMyDataRequestDto;
import com.example.translator.dto.person.request.RegisterClassicPersonRequestDto;
import com.example.translator.dto.person.request.RegisterGooglePersonRequestDto;
import com.example.translator.dto.person.response.*;
import com.example.translator.services.person.MyAccountService;
import com.example.translator.services.person.RegisterPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final RegisterPersonService registerPersonService;
    private final MyAccountService myAccountService;

    @PostMapping("/register/google")
    public ResponseEntity<RegisterGooglePersonResponseDto> postGoogleUser(@RequestBody RegisterGooglePersonRequestDto registerGooglePersonRequestDto){
        return ResponseEntity.ok(registerPersonService.registerGooglePerson(registerGooglePersonRequestDto));
    }

    @PostMapping("/register/classic")
    public ResponseEntity<RegisterClassicPersonResponseDto> postClassicUser(@RequestBody RegisterClassicPersonRequestDto registerClassicPersonRequestDto){
        return ResponseEntity.ok(registerPersonService.registerClassicPerson(registerClassicPersonRequestDto));
    }

    @GetMapping("/retrieve/data/{personId}")
    public ResponseEntity<RetrieveMyDataResponseDto> getMyData(@PathVariable String personId){
        return ResponseEntity.ok(myAccountService.RetrieveMyData(personId));
    }

    @PutMapping("/update/data/{personId}")
    public ResponseEntity<EditMyDataResponseDto> putMyData(@PathVariable String personId, @RequestBody EditMyDataRequestDto editMyDataRequestDto){
        return ResponseEntity.ok(myAccountService.editMyData(editMyDataRequestDto,personId));
    }

    @PatchMapping("/status/{personId}")
    public ResponseEntity<RetrieveStatusAccountResponseDto> patchAccountStatus(@PathVariable String personId){
        return ResponseEntity.ok(myAccountService.changeAccountStatus(personId));
    }


}
