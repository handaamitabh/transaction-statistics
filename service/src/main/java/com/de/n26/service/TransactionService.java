package com.de.n26.service;

import com.de.n26.dto.StatisticsDto;
import com.de.n26.dto.TransactionDto;

public interface TransactionService {

    void processTransactions(final TransactionDto transactionDto);
    void clearTransactions();
    StatisticsDto getTransactionStatistics();
}
