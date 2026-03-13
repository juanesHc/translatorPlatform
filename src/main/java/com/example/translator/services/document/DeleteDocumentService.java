package com.example.translator.services.document;

import com.example.translator.db.entity.DocumentEntity;
import com.example.translator.dto.document.response.DeleteDocumentResponseDto;
import com.example.translator.exceptions.DocumentNotFoundException;
import com.example.translator.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteDocumentService {

    private final DocumentRepository documentRepository;

    public DeleteDocumentResponseDto deleteDocument(String documentId){

        DocumentEntity documentEntity=documentRepository.findById(UUID.fromString(documentId)).
                orElseThrow(()->new DocumentNotFoundException("It run into a problem trying to find the according id"));

        documentRepository.delete(documentEntity);
        return new DeleteDocumentResponseDto("Document successfully dropped");
    }

}
