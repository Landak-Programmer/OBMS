package com.obms.account.repository;

import com.obms.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByAccountNumber(final String accountNumber);

    Account findByAccountNumber(final String accountNumber);

}
