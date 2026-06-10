package com.example.translator.repository;

import com.example.translator.entity.TranslationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TranslationRepository extends JpaRepository<TranslationEntity,UUID> {

    List<TranslationEntity> findByDocumentId(UUID documentId);

}
