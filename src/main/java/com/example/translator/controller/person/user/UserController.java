package com.example.translator.controller.person.user;

import com.example.translator.dto.person.request.RegisterPersonRequestDto;
import com.example.translator.dto.person.response.RegisterPersonResponseDto;
import com.example.translator.services.person.RegisterPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final RegisterPersonService registerPersonService;

    @PostMapping("/register")
    public ResponseEntity<RegisterPersonResponseDto> postUser(@RequestBody RegisterPersonRequestDto registerPersonRequestDto){
        return ResponseEntity.ok(registerPersonService.registerPersonInitial(registerPersonRequestDto));
    }


}
