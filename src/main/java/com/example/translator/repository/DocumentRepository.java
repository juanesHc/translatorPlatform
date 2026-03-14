package com.example.translator.repository;

import com.example.translator.db.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity,UUID> , JpaSpecificationExecutor<DocumentEntity> {

    Optional<List<DocumentEntity>> findByPersonId(UUID personId);

}
