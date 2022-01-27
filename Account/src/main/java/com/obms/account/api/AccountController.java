package com.obms.account.api;

import com.obms.account.model.Account;
import com.obms.account.service.AccountServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api
@RestController
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

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
    public ResponseEntity<Account> getById(final @PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(accountService.getById(id));
    }

    // --------------------------------------- create ---------------------------------------

    @Operation(summary = "Generic create entity controller",
            description = "Generic create entity controller",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PostMapping(value = {
            ""
    }, consumes = ALL_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> create(final @RequestBody Account e) {
        return ResponseEntity
                .ok()
                .body(accountService.create(e));
    }

    // --------------------------------------- update ---------------------------------------

    @Operation(summary = "Generic update entity controller",
            description = "Generic update entity controller",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PutMapping(value = {
            "/{id}"
    }, consumes = ALL_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> update(final @RequestBody Account e, final @PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(accountService.update(id, e));
    }

}
