package com.example.translator.specification;

import com.example.translator.entity.DocumentEntity;
import com.example.translator.dto.document.request.RetrieveDocumentsRequestDto;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class DocumentSpecification {
    private static Specification<DocumentEntity> hasOriginalText(String originalText){
        return ((root, query, cb) -> cb.like(
                cb.lower(root.get("originalText")),"%"+originalText.toLowerCase()+"%"
        ));
    }

    private static Specification<DocumentEntity> hasCreatedAt(LocalDate createdAt) {
        return (root, query, cb) ->
                cb.between(
                        root.get("createdAt"),
                        createdAt.atStartOfDay(),
                        createdAt.atTime(23, 59, 59)
                );
    }


    private static Specification<DocumentEntity> hasFileName(String fileName) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("fileName")), "%" + fileName.toLowerCase() + "%");
    }


    private static Specification<DocumentEntity> createdBetween(
            LocalDate sourceDate,
            LocalDate targetDate
    ) {
        return (root, query, cb) ->
                cb.between(
                        root.get("createdAt"),
                        sourceDate.atStartOfDay(),
                        targetDate.atTime(23, 59, 59)
                );
    }

    public static Specification<DocumentEntity> buildUserSpecification(RetrieveDocumentsRequestDto retrieveDocumentsRequestDto){

        Specification<DocumentEntity> personEntitySpecification = Specification.allOf();

        if(retrieveDocumentsRequestDto.getFileName()!=null && !retrieveDocumentsRequestDto.getFileName().isBlank()){
            personEntitySpecification=personEntitySpecification.and(hasFileName(retrieveDocumentsRequestDto.getFileName()));
        }
        if(retrieveDocumentsRequestDto.getOriginalText()!=null && !retrieveDocumentsRequestDto.getOriginalText().isBlank()){
            personEntitySpecification=personEntitySpecification.and(hasOriginalText(retrieveDocumentsRequestDto.getOriginalText()));
        }

        if (retrieveDocumentsRequestDto.getCreatedAt() != null) {
            personEntitySpecification = personEntitySpecification.and(hasCreatedAt(retrieveDocumentsRequestDto.getCreatedAt()));
        }

        if (retrieveDocumentsRequestDto.getSourceDate() != null && retrieveDocumentsRequestDto.getTargetDate() != null) {
            personEntitySpecification = personEntitySpecification.and(
                    createdBetween(
                            retrieveDocumentsRequestDto.getSourceDate(),
                            retrieveDocumentsRequestDto.getTargetDate()
                    )
            );
        }


        return personEntitySpecification;
    }


}
