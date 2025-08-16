package com.zodus.template.application.services;

import com.zodus.template.domain.models.SalesRecord;
import com.zodus.template.domain.repositories.SalesRecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SalesRecordService {
  private final SalesRecordRepository repository;

  public SalesRecord save(SalesRecord salesRecord) {
    if (salesRecord.getId() != null) {
      salesRecord.setUpdatedAt(LocalDateTime.now());
    }
    return repository.save(salesRecord);
  }

  public SalesRecord findById(UUID id) throws ResponseStatusException {
    return repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sales record not found for id: " + id));
  }

  public Page<SalesRecord> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  public Page<SalesRecord> findAll(Pageable pageable, Specification<SalesRecord> specification) {
    return repository.findAll(specification, pageable);
  }

  public void deleteById(UUID id) throws ResponseStatusException {
    if (!repository.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sales record not found for id: " + id);
    }
    repository.deleteById(id);
  }

  public SalesRecord update(UUID id, SalesRecord salesRecordDetails) throws ResponseStatusException {
    SalesRecord existingSalesRecord = findById(id);
    
    if (salesRecordDetails.getOrder() != null) {
      existingSalesRecord.setOrder(salesRecordDetails.getOrder());
    }
    
    existingSalesRecord.setUpdatedAt(LocalDateTime.now());
    return repository.save(existingSalesRecord);
  }

  public boolean existsById(UUID id) {
    return repository.existsById(id);
  }

  public long count() {
    return repository.count();
  }
}
