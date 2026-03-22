package com.example.translator.services.document;

import com.example.translator.dto.document.response.LoadDocumentResponseDto;
import com.example.translator.entity.DocumentEntity;
import com.example.translator.entity.PersonEntity;
import com.example.translator.dto.document.request.RetrieveDocumentsRequestDto;
import com.example.translator.dto.document.response.RetrieveDocumentsPageResponseDto;
import com.example.translator.dto.document.response.RetrieveDocumentsResponseDto;
import com.example.translator.mapper.document.DocumentMapper;
import com.example.translator.repository.DocumentRepository;
import com.example.translator.repository.PersonRepository;
import com.example.translator.specification.DocumentSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RetrieveDocumentService {

    private final PersonRepository personRepository;
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    public List<RetrieveDocumentsResponseDto> retrieveMyDocuments(String personId){

        List<DocumentEntity> documentEntities=documentRepository.findByPersonId(UUID.fromString(personId));

        return documentMapper.DocumentEntitiesToRetrieveDocumentsResponseDtos(documentEntities);

    }

    public List<LoadDocumentResponseDto> findDocumentForPerson(String personId) {
        List<DocumentEntity> documents = documentRepository.findByPersonId(UUID.fromString(personId));

        return documentMapper.DocumentEntitiesToLoadDocumentResponseDtos(documents);
    }

    public RetrieveDocumentsPageResponseDto getDocumentsByPerson(
            String personId,
            RetrieveDocumentsRequestDto requestDto) {

        PersonEntity person = personRepository.findById(UUID.fromString(personId))
                .orElseThrow(() -> new RuntimeException("Person not found"));

        Specification<DocumentEntity> spec = DocumentSpecification
                .buildUserSpecification(requestDto)
                .and((root, query, cb) -> cb.equal(root.get("person"), person));

        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getSize(),
                Sort.by("createdAt").descending());

        Page<DocumentEntity> page = documentRepository.findAll(spec, pageable);

        return new RetrieveDocumentsPageResponseDto(
                documentMapper.DocumentEntitiesToRetrieveDocumentsResponseDtos(page.getContent()),
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }

    public byte[] downloadDocument(String documentId) {
        DocumentEntity document = documentRepository.findById(UUID.fromString(documentId))
                .orElseThrow(() -> new RuntimeException("Document not found"));

        return Base64.getDecoder().decode(document.getContentBase64());
    }

}
