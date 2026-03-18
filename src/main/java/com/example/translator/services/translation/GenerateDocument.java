package com.example.translator.services.translation;

import com.example.translator.db.entity.DocumentEntity;
import com.example.translator.db.entity.PersonEntity;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Component
public class GenerateDocument {
    public DocumentEntity generateAndSaveTranslatedDocument(
            String translatedText,
            String originalFileName,
            PersonEntity person) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);
        document.add(new Paragraph(translatedText));
        document.close();


        byte[] pdfBytes = outputStream.toByteArray();
        String base64 = Base64.getEncoder().encodeToString(pdfBytes);

        DocumentEntity translatedDoc = new DocumentEntity();
        translatedDoc.setFileName("translated_" + originalFileName);
        translatedDoc.setMimeType("application/pdf");
        translatedDoc.setOriginalText(translatedText);
        translatedDoc.setContentBase64(base64);
        translatedDoc.setPerson(person);

        return translatedDoc;
    }
}
