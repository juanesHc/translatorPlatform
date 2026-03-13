package com.example.translator.services.document;

import com.example.translator.db.entity.DocumentEntity;
import com.example.translator.dto.document.response.RetrieveDocumentsResponseDto;
import com.example.translator.exceptions.PersonNotFoundException;
import com.example.translator.mapper.document.DocumentMapper;
import com.example.translator.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RetrieveAllDocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    public List<RetrieveDocumentsResponseDto> retrieveMyDocuments(String personId){

        List<DocumentEntity> documentEntities=documentRepository.findByPersonId(UUID.fromString(personId)).
                orElseThrow(()->new PersonNotFoundException("Could not found the according person"));

        return documentMapper.DocumentEntitiesToRetrieveDocumentsResponseDtos(documentEntities);

    }

}
