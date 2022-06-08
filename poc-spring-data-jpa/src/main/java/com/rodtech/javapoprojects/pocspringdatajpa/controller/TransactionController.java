package com.rodtech.javapoprojects.pocspringdatajpa.controller;

import com.rodtech.javapoprojects.pocspringdatajpa.dto.TransactionDTO;
import com.rodtech.javapoprojects.pocspringdatajpa.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id){
        return ResponseEntity.ok(transactionService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<TransactionDTO>> list(Pageable page){
        return ResponseEntity.ok(transactionService.list(page));
    }

    @GetMapping("/year/{year}/month/{month}")
    public ResponseEntity<List<TransactionDTO>> getByMonth(
            @PathVariable Integer year, @PathVariable Integer month){
        return ResponseEntity.ok(transactionService.listByYearMonth(year, month));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<TransactionDTO>> getByDate(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
        return ResponseEntity.ok(transactionService.getByDate(date));
    }

    @GetMapping("/note/{note}")
    public ResponseEntity<Page<TransactionDTO>> getByNote(@PathVariable String note, Pageable page){
        return ResponseEntity.ok(transactionService.getByNote(note, page));
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> create(@RequestBody @Valid TransactionDTO transactionDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.create(transactionDTO));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<TransactionDTO>> createBatch(
            @RequestBody @Valid List<TransactionDTO> transactionDTOList) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createBatch(transactionDTOList));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid TransactionDTO transactionDTO){
        transactionService.update(transactionDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/batch")
    public ResponseEntity<?> updateBatch(@RequestBody @Valid List<TransactionDTO> transactionDTOList){
        transactionService.updateBatch(transactionDTOList);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
