package com.obms.account.model;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * FIXME: Redundant maintenance
 */
@Getter
public class AccountTest {

    @NotNull
    private Long id;

    @NotBlank
    private String accountNumber;

    @NotBlank
    private String accountOwner;

    @NotNull
    private Account.Status status;

    public Account toAccount() {
        final Account account = new Account();
        account.setId(id);
        account.setAccountOwner(accountOwner);
        account.setAccountNumber(accountNumber);
        account.setStatus(status);
        return account;
    }
}
