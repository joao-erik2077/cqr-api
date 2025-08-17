package com.zodus.template.api.controllers;

import com.zodus.template.api.dtos.requests.CreateSalesRecordRequest;
import com.zodus.template.api.dtos.requests.UpdateSalesRecordRequest;
import com.zodus.template.api.dtos.responses.SalesRecordResponse;
import com.zodus.template.api.mappers.SalesRecordMapper;
import com.zodus.template.application.services.SalesRecordService;
import com.zodus.template.domain.models.SalesRecord;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/sales-records")
@AllArgsConstructor
public class SalesRecordController {
  private final SalesRecordService salesRecordService;
  private final SalesRecordMapper salesRecordMapper;

  @PostMapping
  public ResponseEntity<SalesRecordResponse> create(@RequestBody @Valid CreateSalesRecordRequest request) {
    SalesRecord salesRecord = salesRecordMapper.createSalesRecordRequestToSalesRecord(request);
    SalesRecord savedSalesRecord = salesRecordService.save(salesRecord);
    SalesRecordResponse response = salesRecordMapper.salesRecordToSalesRecordResponse(savedSalesRecord);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<SalesRecordResponse> findById(@PathVariable UUID id) {
    SalesRecord salesRecord = salesRecordService.findById(id);
    SalesRecordResponse response = salesRecordMapper.salesRecordToSalesRecordResponse(salesRecord);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<Page<SalesRecordResponse>> findAll(
      @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    Page<SalesRecord> salesRecords = salesRecordService.findAll(pageable);
    Page<SalesRecordResponse> response = salesRecords.map(salesRecordMapper::salesRecordToSalesRecordResponse);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<SalesRecordResponse> update(@PathVariable UUID id, 
                                                   @RequestBody @Valid UpdateSalesRecordRequest request) {
    SalesRecord salesRecordDetails = salesRecordMapper.updateSalesRecordRequestToSalesRecord(request);
    SalesRecord updatedSalesRecord = salesRecordService.update(id, salesRecordDetails);
    SalesRecordResponse response = salesRecordMapper.salesRecordToSalesRecordResponse(updatedSalesRecord);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
    salesRecordService.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
