package com.example.translator.repository;

import com.example.translator.dto.document.response.LoadDocumentResponseDto;
import com.example.translator.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity,UUID> , JpaSpecificationExecutor<DocumentEntity> {

List<DocumentEntity> findByPersonId(UUID personId);

}
