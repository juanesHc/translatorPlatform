package com.example.translator.dto.person.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditMyDataRequestDto {
    private String firstName;
    private String lastName;
}
