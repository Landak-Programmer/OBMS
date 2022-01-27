package com.obms.transaction.api;

import com.obms.transaction.model.Transaction;
import com.obms.transaction.service.TransactionServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api
@RestController
@RequestMapping(value = "/transaction")
public class TransactionController {

    @Autowired
    private TransactionServiceImpl transactionService;

    // --------------------------------------- get ---------------------------------------
    @Operation(summary = "Generic getById entity controller",
            description = "Generic getById entity controller",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @GetMapping(value = {
            "/{id}"
    }, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> getById(final @PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(transactionService.getById(id));
    }

    // --------------------------------------- withdraw ---------------------------------------
    @Operation(summary = "Withdraw money from account",
            description = "Withdraw money from account",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PostMapping(value = {
            "/withdraw"
    }, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> withdraw(final @RequestBody @Valid Transaction transaction) {
        return ResponseEntity
                .ok()
                .body(transactionService.withdraw(transaction));
    }

    // --------------------------------------- deposit ---------------------------------------
    @Operation(summary = "Deposit money from account",
            description = "Deposit money from account",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PostMapping(value = {
            "/deposit"
    }, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> deposit(final @RequestBody @Valid Transaction transaction) {
        return ResponseEntity
                .ok()
                .body(transactionService.deposit(transaction));
    }
}
