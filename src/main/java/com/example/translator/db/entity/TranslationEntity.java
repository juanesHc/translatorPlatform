package com.example.translator.db.entity;

import com.example.translator.db.entity.enums.LanguagesEnum;
import com.example.translator.db.entity.enums.TranslationStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "translations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TranslationEntity extends BaseEntity {
    @Column(columnDefinition = "TEXT")
    private String translatedText;

    @Enumerated(EnumType.STRING)
    private LanguagesEnum sourceLanguage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LanguagesEnum targetLanguage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TranslationStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private DocumentEntity document;


}
