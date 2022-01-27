package com.obms.account.fixture;

import com.obms.account.model.Account;
import org.apache.commons.lang3.RandomStringUtils;

public class AccountBuilder {

    private final String accountOwner = RandomStringUtils.randomAlphabetic(10);

    public static AccountBuilder sample() {
        return new AccountBuilder();
    }

    public Account build() {
        final Account account = new Account();
        account.setAccountOwner(accountOwner);

        return account;
    }
}
