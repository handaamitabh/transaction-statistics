package com.de.n26.server.it.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class GherkinHelper {

    private static final String NOW = "NOW";
    private static final String EXPIRED = "EXPIRED";

    public static long millsFromString(String input) {
        long transactionTime = 0L;
        String[] inputString = input.split(",");
        if (inputString[0].equals(EXPIRED)) {
            transactionTime = Instant.now().toEpochMilli() - 62000;
        } else if (inputString[0].equals(NOW)) {
            transactionTime = inputString.length > 1 ? Instant.now().plus(Long.parseLong(inputString[1]), ChronoUnit.SECONDS).toEpochMilli() :
                    Instant.now().toEpochMilli();
        }
        return transactionTime;
    }
}
