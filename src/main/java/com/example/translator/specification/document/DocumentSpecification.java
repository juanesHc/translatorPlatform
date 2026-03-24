package com.example.translator.specification.document;

import com.example.translator.entity.DocumentEntity;
import com.example.translator.dto.document.request.RetrieveDocumentsRequestDto;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class DocumentSpecification {

    private static Specification<DocumentEntity> hasFileName(String fileName) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("fileName")), "%" + fileName.toLowerCase().trim() + "%");
    }

    private static Specification<DocumentEntity> createdBetween(LocalDate start, LocalDate end) {
        return (root, query, cb) ->
                cb.between(
                        root.get("createdAt"),
                        start.atStartOfDay(),
                        end.atTime(23, 59, 59)
                );
    }


    public static Specification<DocumentEntity> buildUserSpecification(RetrieveDocumentsRequestDto requestDto) {

        Specification<DocumentEntity> spec = Specification.allOf();

        if (requestDto.getFileName() != null && !requestDto.getFileName().isBlank()) {
            spec = spec.and(hasFileName(requestDto.getFileName()));
        }

        if (requestDto.getSourceDate() != null && requestDto.getTargetDate() != null) {
            spec = spec.and(createdBetween(requestDto.getSourceDate(), requestDto.getTargetDate()));
        }

        else if (requestDto.getCreatedAt() != null) {
            spec = spec.and(createdBetween(requestDto.getCreatedAt(), requestDto.getCreatedAt()));
        }

        return spec;
    }

}
