package com.example.translator.repository;

import com.example.translator.bd.entity.TranslationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TranslationRepository extends JpaRepository<TranslationEntity,UUID> {

    List<TranslationEntity> findByPersonId(UUID personId);

    @Query("SELECT COUNT(t) FROM TranslationEntity t WHERE t.person.id = :personId")
    long countByPersonId(@Param("personId") UUID personId);

}
