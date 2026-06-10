package com.example.translator.controller.person.admin;

import com.example.translator.dto.person.request.RegisterPersonWithRoleRequestDto;
import com.example.translator.dto.person.request.RetrievePersonRequestDto;
import com.example.translator.dto.person.response.RegisterPersonWithRoleResponseDto;
import com.example.translator.dto.person.response.RetrievePersonPageResponseDto;
import com.example.translator.dto.person.response.RetrieveStatusAccountResponseDto;
import com.example.translator.services.person.MyAccountService;
import com.example.translator.services.person.RegisterPersonService;
import com.example.translator.services.person.RetrievePersonByFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final RegisterPersonService registerPersonService;
    private final RetrievePersonByFilterService retrievePersonByFilterService;
    private final MyAccountService myAccountService;

    @PostMapping("/retrieve/filter")
    public ResponseEntity<RetrievePersonPageResponseDto> getPersonsByFilter(
            @RequestBody RetrievePersonRequestDto requestDto) {
        return ResponseEntity.ok(retrievePersonByFilterService.retrievePersonByFilter(requestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterPersonWithRoleResponseDto> postPersonsWithRole(
            @RequestBody RegisterPersonWithRoleRequestDto requestDto) {
        return ResponseEntity.ok(registerPersonService.registerWithRole(requestDto));
    }

    @PatchMapping("/block/{personId}")
    public ResponseEntity<RetrieveStatusAccountResponseDto> patchBlockStatus(@PathVariable String personId){
        return ResponseEntity.ok(myAccountService.blockAccount(personId));
    }


}
