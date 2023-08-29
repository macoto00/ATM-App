package com.phonecompany.billin.arbestechnologies;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class TelephoneBillCalculatorImpl implements TelephoneBillCalculator {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private String mostFrequentNumber = null;
    private int mostFrequentCount = 0;
    private final Map<String, Integer> phoneNumberToDuration = new HashMap<>();

    @Override
    public BigDecimal calculate(String phoneLog) throws IOException {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();
        String[] lines = phoneLog.split("\n");
        BigDecimal totalCost = BigDecimal.ZERO;

        // Iterate each line
        for (String line : lines) {
            String[] fields = csvParser.parseLine(line);

            // Extract the values and pass them into variables
            String phoneNumber = fields[0];
            LocalDateTime startTime = LocalDateTime.parse(fields[1], DATE_TIME_FORMATTER);
            LocalDateTime endTime = LocalDateTime.parse(fields[2], DATE_TIME_FORMATTER);

            // Calculate call duration in minutes
            Duration callDuration = Duration.between(startTime, endTime);
            long durationMinutes = callDuration.toMinutes();

            // Update most frequently called number
            updateMostFrequentNumber(phoneNumber);

            // Calculate call cost based on the rules and duration
            BigDecimal callCost = calculateCallCost(startTime.toLocalTime(), durationMinutes);

            // Accumulate the call cost
            totalCost = totalCost.add(callCost);

            // Track total duration per phone number
            phoneNumberToDuration.put(phoneNumber, phoneNumberToDuration.getOrDefault(phoneNumber, 0) + (int) durationMinutes);
        }

        // Apply promo rule: exclude most frequent number
        if (mostFrequentNumber != null) {
            phoneNumberToDuration.remove(mostFrequentNumber);
        }

        return totalCost;
    }

    private void updateMostFrequentNumber(String phoneNumber) {
        int count = phoneNumberToDuration.getOrDefault(phoneNumber, 0) + 1;
        if (count > mostFrequentCount) {
            mostFrequentCount = count;
            mostFrequentNumber = phoneNumber;
        }
    }

    private BigDecimal calculateCallCost(LocalTime callTime, long durationMinutes) {
        BigDecimal rate = (callTime.isAfter(LocalTime.of(8, 0)) && callTime.isBefore(LocalTime.of(16, 0)))
                ? BigDecimal.ONE : new BigDecimal("0.50");

        if (durationMinutes > 5) {
            BigDecimal additionalCost = BigDecimal.valueOf((durationMinutes - 5) * 0.20);
            return rate.multiply(BigDecimal.valueOf(5)).add(additionalCost);
        }

        return rate.multiply(BigDecimal.valueOf(durationMinutes));
    }
}

