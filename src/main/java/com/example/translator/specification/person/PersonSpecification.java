package com.example.translator.specification.person;

import com.example.translator.dto.person.request.RetrievePersonRequestDto;
import com.example.translator.entity.PersonEntity;
import com.example.translator.entity.enums.AuthEnum;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class PersonSpecification {
    private static Specification<PersonEntity> hasGivenName(String givenName) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("givenName")), "%" + givenName.toLowerCase().trim() + "%");
    }

    private static Specification<PersonEntity> hasFamilyName(String familyName) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("familyName")), "%" + familyName.toLowerCase().trim() + "%");
    }

    private static Specification<PersonEntity> hasEmail(String email) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase().trim() + "%");
    }

    private static Specification<PersonEntity> hasAuthEnum(AuthEnum authEnum) {
        return (root, query, cb) ->
                cb.equal(root.get("authEnum"), authEnum);
    }

    private static Specification<PersonEntity> isActivate(Boolean activate) {
        return (root, query, cb) ->
                cb.equal(root.get("activate"), activate);
    }

    private static Specification<PersonEntity> createdBetween(LocalDate start, LocalDate end) {
        return (root, query, cb) ->
                cb.between(
                        root.get("createdAt"),
                        start.atStartOfDay(),
                        end.atTime(23, 59, 59)
                );
    }

    public static Specification<PersonEntity> buildPersonSpecification(RetrievePersonRequestDto requestDto) {

        Specification<PersonEntity> spec = Specification.allOf();

        if (requestDto.getGivenName() != null && !requestDto.getGivenName().isBlank()) {
            spec = spec.and(hasGivenName(requestDto.getGivenName()));
        }

        if (requestDto.getFamilyName() != null && !requestDto.getFamilyName().isBlank()) {
            spec = spec.and(hasFamilyName(requestDto.getFamilyName()));
        }

        if (requestDto.getEmail() != null && !requestDto.getEmail().isBlank()) {
            spec = spec.and(hasEmail(requestDto.getEmail()));
        }

        if (requestDto.getAuthEnum() != null) {
            spec = spec.and(hasAuthEnum(requestDto.getAuthEnum()));
        }

        if (requestDto.getActivate() != null) {
            spec = spec.and(isActivate(requestDto.getActivate()));
        }

        if (requestDto.getStartDate() != null && requestDto.getEndDate() != null) {
            spec = spec.and(createdBetween(requestDto.getStartDate(), requestDto.getEndDate()));
        } else if (requestDto.getStartDate() != null) {
            spec = spec.and(createdBetween(requestDto.getStartDate(), requestDto.getStartDate()));
        }

        return spec;
    }
}
