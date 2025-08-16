package com.zodus.template.domain.repositories;

import com.zodus.template.domain.models.SalesRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SalesRecordRepository extends JpaRepository<SalesRecord, UUID>, JpaSpecificationExecutor<SalesRecord> {
}
