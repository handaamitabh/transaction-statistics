package com.de.n26.service;

import com.de.n26.dto.StatisticsDto;
import com.de.n26.dto.TransactionDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class InMemoryTransactionServiceTest {

    @Mock
    private ConcurrentHashMap<Long, Double> transactionData;

    @InjectMocks
    private InMemoryTransactionService memoryTransactionService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void processTransactions() {
        long timestamps = Instant.now().toEpochMilli();
        memoryTransactionService.processTransactions(new TransactionDto(10.0, timestamps));

        verify(transactionData, Mockito.times(1)).put(timestamps, 10.0);
    }

    @Test
    public void getTransactionStatisticsAllValidTimestamps() {

        ConcurrentHashMap<Long, Double> transactions = new ConcurrentHashMap<>();
        Long epochTimeMillis = Instant.now().toEpochMilli();
        transactions.put(epochTimeMillis, 10.0);
        transactions.put(epochTimeMillis - 5000, 20.0);
        transactions.put(epochTimeMillis - 6000, 30.0);
        transactions.put(epochTimeMillis - 7000, 40.0);
        transactions.put(epochTimeMillis - 20000, 50.0);

        when(transactionData.entrySet()).thenReturn(transactions.entrySet());
        StatisticsDto transactionStatistics = memoryTransactionService.getTransactionStatistics();

        assertEquals(150.0, transactionStatistics.getSum(), 0);
        assertEquals(30.0, transactionStatistics.getAvg(), 0);
        assertEquals(10.0, transactionStatistics.getMin(), 0);
        assertEquals(50.0, transactionStatistics.getMax(), 0);
        assertEquals(5, transactionStatistics.getCount());
    }

    @Test
    public void getTransactionStatisticsValidAndInvalidTimestamps() {

        ConcurrentHashMap<Long, Double> transactions = new ConcurrentHashMap<>();
        Long epochTimeMillis = Instant.now().toEpochMilli();
        transactions.put(epochTimeMillis, 10.0);
        transactions.put(epochTimeMillis - 20000, 20.0);
        transactions.put(epochTimeMillis - 30000, 30.0);
        transactions.put(epochTimeMillis - 40000, 40.0);
        transactions.put(epochTimeMillis - 62000, 50.0);

        when(transactionData.entrySet()).thenReturn(transactions.entrySet());
        StatisticsDto transactionStatistics = memoryTransactionService.getTransactionStatistics();

        assertEquals(100.0, transactionStatistics.getSum(), 0);
        assertEquals(25.0, transactionStatistics.getAvg(), 0);
        assertEquals(10.0, transactionStatistics.getMin(), 0);
        assertEquals(40.0, transactionStatistics.getMax(), 0);
        assertEquals(4, transactionStatistics.getCount());

    }

    @Test
    public void clearTransactions() {
        memoryTransactionService.clearTransactions();
        verify(transactionData, times(1)).clear();
    }
}