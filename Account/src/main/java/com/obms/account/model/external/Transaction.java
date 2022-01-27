package com.obms.account.model.external;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Now this is kinda dumb because of duplicate dependency
 * of an object
 * <p>
 * We got 2 choice
 * 1. Make client out of all microservices
 * 2. Import item as module dependency (not good as it breaks the isolation)
 */
@Getter
@Setter
public class Transaction {

    @NotNull
    private Long id;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String accountNumber;

    private Status status;

    public enum Status {
        PENDING,
        FAILED,
        SUCCESS
    }
}
