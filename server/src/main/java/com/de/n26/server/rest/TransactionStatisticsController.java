package com.de.n26.server.rest;

import com.de.n26.dto.StatisticsDto;
import com.de.n26.dto.TransactionDto;
import com.de.n26.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@Slf4j
@Api(tags = {"TransactionStatistics"}, description = "process transactions and provide statistics")
public class TransactionStatisticsController {

    @Autowired
    @Qualifier("inMemoryTransactionService")
    TransactionService transactionService;

    @ApiOperation(value = "process valid transactions")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully processed the transaction"),
            @ApiResponse(code = 204, message = "transaction is older than 60 seconds")
    })
    @PostMapping(value = "/transactions", produces = "application/json")
    public ResponseEntity<Void> processTransactions(@RequestBody TransactionDto transactionDto) throws Exception {

        long validTransactionsTime = Instant.now().toEpochMilli() - 60000;

        if (transactionDto.getTimestamp() > validTransactionsTime) {
            transactionService.processTransactions(transactionDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        log.info("transaction is older than 1 second !");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "returns the statistics based on the transactions of the last 60 seconds", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returns the statistics"),
    })
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public StatisticsDto getStatistics() throws Exception {
        return transactionService.getTransactionStatistics();
    }
}
