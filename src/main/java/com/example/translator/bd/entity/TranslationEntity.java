package com.example.translator.bd.entity;

import com.example.translator.bd.entity.enums.LanguagesEnum;
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

    @Column(nullable = false, columnDefinition = "TEXT")
    private String originalText;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String translatedText;

    @Column(nullable = false)
    private String fileName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LanguagesEnum sourceLanguage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LanguagesEnum targetLanguage;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity person;


}
