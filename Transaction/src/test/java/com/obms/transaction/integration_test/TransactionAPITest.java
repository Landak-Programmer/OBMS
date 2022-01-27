package com.obms.transaction.integration_test;

import com.obms.transaction.fixture.TransactionBuilder;
import com.obms.transaction.model.Transaction;
import com.obms.transaction.model.TransactionTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.HttpStatus.OK;

public class TransactionAPITest extends BaseAPIIntegrationTest {

    @Before
    public void setup() {
    }

    @Test
    public void depositTest() {
        final Transaction transaction = TransactionBuilder.sample().build();
        final ResponseEntity<TransactionTest> response = restTemplate.postForEntity("/transaction/deposit", transaction, TransactionTest.class);
        assertEquals("Response code must be 200 OK", OK, response.getStatusCode());
        assertNotNull("Response body must not be null", response.getBody());
        final Transaction responseBody = response.getBody().toTransaction();
        assertNotNull("Id must not be null", responseBody.getId());
        assertEquals("Account number must not be null", transaction.getAccountNumber(), responseBody.getAccountNumber());
        assertEquals("Status must PENDING", Transaction.Status.PENDING, responseBody.getStatus());
        assertEquals("Type must DEPOSIT", Transaction.Type.DEPOSIT, responseBody.getType());
    }

    @Test
    public void withdrawTest() {
        final Transaction transaction = TransactionBuilder.sample().build();
        final ResponseEntity<TransactionTest> response = restTemplate.postForEntity("/transaction/withdraw", transaction, TransactionTest.class);
        assertEquals("Response code must be 200 OK", OK, response.getStatusCode());
        assertNotNull("Response body must not be null", response.getBody());
        final Transaction responseBody = response.getBody().toTransaction();
        assertNotNull("Id must not be null", responseBody.getId());
        assertEquals("Account number must not be null", transaction.getAccountNumber(), responseBody.getAccountNumber());
        assertEquals("Status must PENDING", Transaction.Status.PENDING, responseBody.getStatus());
        assertEquals("Type must WITHDRAW", Transaction.Type.WITHDRAW, responseBody.getType());
    }
}
