package com.example.translator.controller.person.admin;

import com.example.translator.dto.person.request.RetrievePersonRequestDto;
import com.example.translator.dto.person.response.RetrievePersonPageResponseDto;
import com.example.translator.services.person.RetrievePersonByFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final RetrievePersonByFilterService retrievePersonByFilterService;

    @PostMapping("/retrieve/filter")
    public ResponseEntity<RetrievePersonPageResponseDto> getPersonsByFilter(
            @RequestBody RetrievePersonRequestDto requestDto) {
        return ResponseEntity.ok(retrievePersonByFilterService.retrievePersonByFilter(requestDto));
    }
}
