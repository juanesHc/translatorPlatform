package com.example.translator.controller.person.user;

import com.example.translator.dto.person.request.EditMyDataRequestDto;
import com.example.translator.dto.person.request.RegisterPersonRequestDto;
import com.example.translator.dto.person.response.EditMyDataResponseDto;
import com.example.translator.dto.person.response.RegisterPersonResponseDto;
import com.example.translator.dto.person.response.RetrieveMyDataResponseDto;
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

    @PostMapping("/register")
    public ResponseEntity<RegisterPersonResponseDto> postUser(@RequestBody RegisterPersonRequestDto registerPersonRequestDto){
        return ResponseEntity.ok(registerPersonService.registerPersonInitial(registerPersonRequestDto));
    }

    @GetMapping("/retrieve/data/{personId}")
    public ResponseEntity<RetrieveMyDataResponseDto> getMyData(@PathVariable String personId){
        return ResponseEntity.ok(myAccountService.RetrieveMyData(personId));
    }

    @PutMapping("/update/data/{personId}")
    public ResponseEntity<EditMyDataResponseDto> putMyData(@PathVariable String personId, @RequestBody EditMyDataRequestDto editMyDataRequestDto){
        return ResponseEntity.ok(myAccountService.editMyData(editMyDataRequestDto,personId));
    }


}
