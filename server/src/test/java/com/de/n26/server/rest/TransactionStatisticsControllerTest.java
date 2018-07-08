package com.de.n26.server.rest;

import com.de.n26.dto.StatisticsDto;
import com.de.n26.dto.TransactionDto;
import com.de.n26.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TransactionStatisticsControllerTest {

    @Mock
    TransactionService transactionService;

    @InjectMocks
    TransactionStatisticsController statisticsController;

    @Before
    public void setUp() throws Throwable {
        initMocks(this);
    }

    @Test
    public void processTransactionsValidTransaction() throws Throwable {
        doNothing().when(transactionService).processTransactions(any());
        ResponseEntity<Void> response = statisticsController.processTransactions(new TransactionDto(10.0, Instant.now().toEpochMilli()));

        assertEquals(new ResponseEntity<>(HttpStatus.CREATED), response);
    }

    @Test
    public void processTransactionsInvalidTransaction() throws Throwable {
        doNothing().when(transactionService).processTransactions(any());
        ResponseEntity<Void> response = statisticsController.processTransactions(new TransactionDto(10.0, Instant.now().toEpochMilli() - 62000));

        assertEquals(new ResponseEntity<>(HttpStatus.NO_CONTENT), response);
    }

    @Test
    public void getStatistics() throws Throwable {
        when(transactionService.getTransactionStatistics()).thenReturn(new StatisticsDto(150.0, 30.0, 50.0, 10.0, 5L));
        StatisticsDto statistics = statisticsController.getStatistics();

        assertEquals(150.0, statistics.getSum(), 0);
        assertEquals(30.0, statistics.getAvg(), 0);
        assertEquals(10.0, statistics.getMin(), 0);
        assertEquals(50.0, statistics.getMax(), 0);
        assertEquals(5, statistics.getCount());
    }
}