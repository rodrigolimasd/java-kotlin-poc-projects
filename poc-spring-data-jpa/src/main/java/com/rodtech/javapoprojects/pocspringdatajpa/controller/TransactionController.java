package com.rodtech.javapoprojects.pocspringdatajpa.controller;

import com.rodtech.javapoprojects.pocspringdatajpa.dto.TransactionDTO;
import com.rodtech.javapoprojects.pocspringdatajpa.dto.TransactionFilterDTO;
import com.rodtech.javapoprojects.pocspringdatajpa.service.TransactionService;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id){
        log.info("get transaction by id {} ", id);
        TransactionDTO transactionDTO = transactionService.getById(id);
        log.info("transaction {} ", transactionDTO);
        return ResponseEntity.ok(transactionDTO);
    }

    @GetMapping
    public ResponseEntity<Page<TransactionDTO>> list(Pageable page){
        log.info("list transactions {} ", page);
        Page<TransactionDTO> list = transactionService.list(page);
        log.info("transactions {} ", list);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/year/{year}/month/{month}")
    public ResponseEntity<List<TransactionDTO>> getByMonth(
            @PathVariable Integer year, @PathVariable Integer month){
        log.info("list transactions by month {} ", month);
        List<TransactionDTO> list = transactionService.listByYearMonth(year, month);
        log.info("transactions {} ", list);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<TransactionDTO>> getByDate(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
        log.info("list transactions by date {} ", date);
        List<TransactionDTO> list = transactionService.getByDate(date);
        log.info("transactions {} ", list);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/note/{note}")
    public ResponseEntity<Page<TransactionDTO>> getByNote(@PathVariable String note, Pageable page){
        log.info("list transactions by note {} ", note);
        Page<TransactionDTO> pageList = transactionService.getByNote(note, page);
        log.info("transactions {} ", pageList);
        return ResponseEntity.ok(pageList);
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<TransactionDTO>> filter(TransactionFilterDTO filterDTO, Pageable page){
        log.info("filter transactions {} ", filterDTO);
        Page<TransactionDTO> pageList = transactionService.filter(filterDTO, page);
        log.info("transactions filted {} ", pageList);
        return ResponseEntity.ok(pageList);
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> create(@RequestBody @Valid TransactionDTO transactionDTO){
        log.info("create transaction {} ", transactionDTO);
        TransactionDTO dto = transactionService.create(transactionDTO);
        log.info("transaction created {} ", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<TransactionDTO>> createBatch(
            @RequestBody @Valid List<TransactionDTO> transactionDTOList) {
        log.info("create batch transactions {} ", transactionDTOList);
        List<TransactionDTO> list = transactionService.createBatch(transactionDTOList);
        log.info("batch transactions created {} ", list);
        return ResponseEntity.status(HttpStatus.CREATED).body(list);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid TransactionDTO transactionDTO){
        log.info("update transaction {} ", transactionDTO);
        transactionService.update(transactionDTO);
        log.info("transaction updated");
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/batch")
    public ResponseEntity<?> updateBatch(@RequestBody @Valid List<TransactionDTO> transactionDTOList){
        log.info("update batch transactions {} ", transactionDTOList);
        transactionService.updateBatch(transactionDTOList);
        log.info("batch transactions updated");
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        log.info("delete transaction by id {} ", id);
        transactionService.delete(id);
        log.info("transaction deleted");
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/batch")
    public ResponseEntity<?> deleteBatch(@RequestBody List<TransactionDTO> transactionDTOList){
        log.info("delete batch transactions {} ", transactionDTOList);
        transactionService.deleteBatch(transactionDTOList);
        log.info("batch transactions deleted");
        return ResponseEntity.noContent().build();
    }

}
