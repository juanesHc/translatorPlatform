package com.example.translator.db.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "document")
public class DocumentEntity extends BaseEntity {
    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String mimeType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String originalText;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contentBase64;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity person;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private List<TranslationEntity> translations;
}
