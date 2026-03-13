package com.example.translator.controller.document;

import com.example.translator.dto.document.response.LoadDocumentResponseDto;
import com.example.translator.dto.document.response.RetrieveDocumentsResponseDto;
import com.example.translator.services.document.RegisterDocumentService;
import com.example.translator.services.document.RetrieveDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/document")
@RequiredArgsConstructor
public class DocumentController {

    private final RegisterDocumentService registerDocumentService;
    private final RetrieveDocumentService retrieveDocumentService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/load/{personId}")
    public ResponseEntity<LoadDocumentResponseDto> postDocument(@PathVariable String personId, @RequestParam("file") MultipartFile file){

        LoadDocumentResponseDto loadDocumentResponseDto= registerDocumentService.registerDocument(file,personId);

        return ResponseEntity.ok(loadDocumentResponseDto);
    }

    @GetMapping("/retrieve/{personId}")
    public ResponseEntity<List<RetrieveDocumentsResponseDto>> getOrignalDocuments(@PathVariable String personId){

        List<RetrieveDocumentsResponseDto> retrieveDocumentsResponseDto=retrieveDocumentService.retrieveMyDocuments(personId);

        return ResponseEntity.ok(retrieveDocumentsResponseDto);
    }


}
