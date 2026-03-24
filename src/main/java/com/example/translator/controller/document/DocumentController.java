package com.example.translator.controller.document;

import com.example.translator.dto.document.request.RetrieveDocumentsRequestDto;
import com.example.translator.dto.document.response.DeleteDocumentResponseDto;
import com.example.translator.dto.document.response.LoadDocumentResponseDto;
import com.example.translator.dto.document.response.RetrieveDocumentsPageResponseDto;
import com.example.translator.services.document.DeleteDocumentService;
import com.example.translator.services.document.RegisterDocumentService;
import com.example.translator.services.document.RetrieveDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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
    private final DeleteDocumentService deleteDocumentService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/load/{personId}")
    public ResponseEntity<LoadDocumentResponseDto> postDocument(@PathVariable String personId, @RequestParam("file") MultipartFile file){

        LoadDocumentResponseDto loadDocumentResponseDto= registerDocumentService.registerDocument(file,personId);

        return ResponseEntity.ok(loadDocumentResponseDto);
    }

    @GetMapping("/person/{personId}/summary")
    public ResponseEntity<List<LoadDocumentResponseDto>> getDocumentSummaries(@PathVariable String personId) {
        return ResponseEntity.ok(retrieveDocumentService.findDocumentForPerson(personId));
    }

    @GetMapping("/retrieve/{personId}")
    public ResponseEntity<RetrieveDocumentsPageResponseDto> getDocuments(
            @PathVariable String personId,
            @ModelAttribute RetrieveDocumentsRequestDto requestDto) {

        return ResponseEntity.ok(retrieveDocumentService.getDocumentsByPerson(personId, requestDto));
    }

    @DeleteMapping("/delete/{documentId}")
    public ResponseEntity<DeleteDocumentResponseDto> deleteOriginalDocument(@PathVariable String documentId){
        return ResponseEntity.ok(deleteDocumentService.deleteDocument(documentId));
    }

    @GetMapping("/{documentId}/download")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable String documentId) {
        byte[] pdfBytes = retrieveDocumentService.downloadDocument(documentId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"original_document.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }




}
