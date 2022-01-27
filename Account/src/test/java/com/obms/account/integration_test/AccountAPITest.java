package com.obms.account.integration_test;

import com.obms.account.fixture.AccountBuilder;
import com.obms.account.model.Account;
import com.obms.account.model.AccountTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.OK;

public class AccountAPITest extends BaseAPIIntegrationTest {

    @Before
    public void setup() {
    }

    @Test
    public void createTest() {
        create();
    }

    @Test
    public void getTest() {
        final Account acc = create();
        final ResponseEntity<AccountTest> response = restTemplate.getForEntity("/account/" + acc.getId(), AccountTest.class);
        assertEquals("Response code must be 200 OK", OK, response.getStatusCode());
        assertNotNull("Response body must not be null", response.getBody());
        final Account responseBody = response.getBody().toAccount();
        assertEquals("Account id must be the same", acc.getId(), responseBody.getId());
    }

    @Test
    public void updateTest() {
        final Account acc = create();
        final Account request = new AccountBuilder().build();
        final ResponseEntity<AccountTest> response = restTemplate.exchange("/account/" + acc.getId(), PUT, new HttpEntity<>(request, headers), AccountTest.class);
        assertEquals("Response code must be 200 OK", OK, response.getStatusCode());
        assertNotNull("Response body must not be null", response.getBody());
        final Account responseBody = response.getBody().toAccount();
        assertEquals("Account id must be the same", acc.getId(), responseBody.getId());
        assertEquals("Account name must same with request", request.getAccountOwner(), responseBody.getAccountOwner());
    }

    private Account create() {
        final Account request = new AccountBuilder().build();
        final ResponseEntity<AccountTest> response = restTemplate.postForEntity("/account", request, AccountTest.class);
        assertEquals("Response code must be 200 OK", OK, response.getStatusCode());
        assertNotNull("Response body must not be null", response.getBody());
        final Account responseBody = response.getBody().toAccount();
        assertNotNull("Id must not be null", responseBody.getId());
        assertNotNull("Account number must not be null", responseBody.getAccountNumber());
        assertEquals("Account name must same with request", request.getAccountOwner(), responseBody.getAccountOwner());
        assertEquals("Account must be in ACTIVE state", Account.Status.ACTIVE, responseBody.getStatus());
        return responseBody;
    }
}
