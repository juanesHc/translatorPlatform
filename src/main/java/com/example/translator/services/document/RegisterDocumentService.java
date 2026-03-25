package com.example.translator.services.document;

import com.example.translator.dto.translation.request.TranslationRequestDto;
import com.example.translator.entity.DocumentEntity;
import com.example.translator.entity.PersonEntity;
import com.example.translator.dto.document.response.LoadDocumentResponseDto;
import com.example.translator.exceptions.*;
import com.example.translator.mapper.document.DocumentMapper;
import com.example.translator.repository.DocumentRepository;
import com.example.translator.repository.PersonRepository;
import com.example.translator.services.translation.RegisterTranslationService;
import jakarta.transaction.TransactionalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterDocumentService {

    private final PersonRepository personRepository;
    private final Tika tika;
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final RegisterTranslationService registerTranslationService;

    public LoadDocumentResponseDto updateFileName(String documentId,String newName){
        if(validateNewName(newName)==false){
            throw new UpdateDocumentException("The name cant be void");
        }
        DocumentEntity documentEntity=documentRepository.findById(UUID.fromString(documentId))
                .orElseThrow(()->new DocumentNotFoundException("Couldnt found the document"));

        documentEntity.setFileName(newName);
        DocumentEntity documentSaved=documentRepository.save(documentEntity);
        return new LoadDocumentResponseDto(String.valueOf(documentSaved.getId()),documentSaved.getFileName());
    }

    private Boolean validateNewName(String name){
        String newName=name.trim();
        return newName!=null;
    }

    public LoadDocumentResponseDto registerDocument(MultipartFile file,String personId,String targetLanguage){
        PersonEntity personEntity = personRepository.findById(UUID.fromString(personId))
                .orElseThrow(()->new PersonNotFoundException("Couldnt found respective person"));

        if (file.isEmpty()) {
      throw new DocumentProcessingException("the attached file is empty");
        }

        String mimeType;
        byte[] bytes;
        try {
            bytes = file.getBytes();
            mimeType = tika.detect(bytes);
        } catch (IOException e) {
            throw new DocumentProcessingException("No se pudo leer el archivo");
        }

        if (!isAllowedType(mimeType)) {
            throw new DocumentProcessingException("Tipo de archivo no permitido: " + mimeType);
        }

        String extractedText;
        try {
            extractedText = tika.parseToString(new ByteArrayInputStream(bytes));
        } catch (Exception e) {
            throw new DocumentProcessingException("No se pudo extraer el texto del archivo");
        }

        String base64 = Base64.getEncoder().encodeToString(bytes);

        DocumentEntity document = new DocumentEntity();
        document.setFileName(file.getOriginalFilename());
        document.setMimeType(mimeType);
        document.setOriginalText(extractedText);
        document.setContentBase64(base64);
        document.setPerson(personEntity);
        DocumentEntity documentSaved = documentRepository.save(document);

        TranslationRequestDto translationRequestDto=new TranslationRequestDto();
        translationRequestDto.setTargetLanguage(targetLanguage);

        try{
            registerTranslationService.translate(String.valueOf(documentSaved.getId()),translationRequestDto);
        } catch (TransactionalException e) {
            log.error("It run into a problem trying to translate the document",e);
            throw new TranslationException("It run into a problem trying to translate the document");
        }

        return documentMapper.toLoadDto(documentSaved);
    }


    private boolean isAllowedType(String mimeType) {
        return List.of(
                "application/pdf",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "text/plain"
        ).contains(mimeType);
    }


}
