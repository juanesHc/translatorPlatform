package com.example.translator.controller.document;

import com.example.translator.dto.document.response.LoadDocumentResponseDto;
import com.example.translator.services.document.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/document")
@RequiredArgsConstructor
public class DocumentController {

    private DocumentService documentService;



    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/load/{personId}")
    public ResponseEntity<LoadDocumentResponseDto> postDocument(@PathVariable String personId, @RequestParam("file") MultipartFile file){

        LoadDocumentResponseDto loadDocumentResponseDto=documentService.registerDocument(file,personId);

        return ResponseEntity.ok(loadDocumentResponseDto);
    }

}
