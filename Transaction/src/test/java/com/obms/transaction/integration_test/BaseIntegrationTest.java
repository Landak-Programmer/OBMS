package com.obms.transaction.integration_test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@Rollback
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
public abstract class BaseIntegrationTest {

    @Before
    public void setupBefore() {
    }
}
