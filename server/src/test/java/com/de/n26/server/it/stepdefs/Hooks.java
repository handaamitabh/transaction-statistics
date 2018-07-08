package com.de.n26.server.it.stepdefs;

import com.de.n26.service.InMemoryTransactionService;
import cucumber.api.java.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@Scope("cucumber-glue")
public class Hooks extends SpringIntegrationTest {

    InMemoryTransactionService memoryTransactionService;

    @Autowired
    public Hooks(InMemoryTransactionService memoryTransactionService) {
        this.memoryTransactionService = memoryTransactionService;
    }

    @Before
    public void before() throws Exception {
        memoryTransactionService.clearTransactions();
    }
}
