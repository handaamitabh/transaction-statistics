package com.de.n26.service;

import com.de.n26.dto.StatisticsDto;
import com.de.n26.dto.TransactionDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class InMemoryTransactionService implements TransactionService {

    private ConcurrentHashMap<Long, Double> transactionData;

    public InMemoryTransactionService() {
        transactionData = new ConcurrentHashMap<>();
    }

    /**
     * Saves transaction amount and timestamp in a concurrent hash map.
     *
     * @param transactionDto
     */
    @Override
    public synchronized void processTransactions(final TransactionDto transactionDto) {
        this.transactionData.put(transactionDto.getTimestamp(), transactionDto.getAmount());
    }

    /**
     * Calculates and returns the statistics based on the transactions of the last 60 seconds.
     *
     * @return
     */
    @Override
    public synchronized StatisticsDto getTransactionStatistics() {

        long validTransactionsTime = Instant.now().toEpochMilli() - 60000;

        List<Double> filteredTransactionAmounts = transactionData.entrySet()
                .stream()
                .filter(map -> map.getKey() > validTransactionsTime)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        DoubleSummaryStatistics stats = filteredTransactionAmounts.stream().collect(Collectors.summarizingDouble(Double::doubleValue));

        return new StatisticsDto(stats.getSum(), stats.getAverage(), stats.getMax(), stats.getMin(), stats.getCount());
    }

    /**
     * Clears the entries inside the Concurrent hash map.
     */
    @Override
    public void clearTransactions() {
        transactionData.clear();
    }
}
