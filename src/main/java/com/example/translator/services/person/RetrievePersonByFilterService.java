package com.example.translator.services.person;

import com.example.translator.dto.person.request.RetrievePersonRequestDto;
import com.example.translator.dto.person.response.RetrievePersonPageResponseDto;
import com.example.translator.entity.PersonEntity;
import com.example.translator.mapper.person.PersonMapper;
import com.example.translator.repository.PersonRepository;
import com.example.translator.specification.person.PersonSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetrievePersonByFilterService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public RetrievePersonPageResponseDto retrievePersonByFilter(RetrievePersonRequestDto requestDto) {

        Specification<PersonEntity> spec = PersonSpecification.buildPersonSpecification(requestDto);

        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getSize(),
                Sort.by("createdAt").descending());

        Page<PersonEntity> page = personRepository.findAll(spec, pageable);

        long total = page.getTotalElements();
        String message = (total == 0) ? "No hay resultados para esta búsqueda" : "Búsqueda exitosa";

        return new RetrievePersonPageResponseDto(
                personMapper.toRetrievePersonResponseDtos(page.getContent()),
                page.getNumber(),
                page.getTotalPages(),
                total,
                message
        );
    }
}
