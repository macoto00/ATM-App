package com.phonecompany.billin.arbestechnologies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TelephoneBillCalculatorImplTest {

    private TelephoneBillCalculatorImpl calculator;

    @BeforeEach
    public void setUp() {
        calculator = new TelephoneBillCalculatorImpl();
    }

    @Test
    public void testCalculate() throws IOException {
        String phoneLog = "420774577453,13-01-2020 08:00:00,13-01-2020 08:05:00\n" +
                "420774577453,13-01-2020 09:00:00,13-01-2020 09:10:00\n" +
                "420123456789,13-01-2020 10:30:00,13-01-2020 10:40:00\n" +
                "420123456999,13-01-2020 17:30:00,13-01-2020 17:40:00\n";

        // Expected cost calculation:
        // Call 1: 08:00:00 - 08:05:00 (5 minutes) -> Rate: 1 Kč/min -> Cost: 5 * 1 = 5 Kč
        // Call 2: 09:00:00 - 09:10:00 (10 minutes) -> Rate: 1 Kč/min -> Cost: 5 * 1 + 5 * 0,2 = 6 Kč
        // Call 3: 10:30:00 - 10:40:00 (10 minutes) -> Rate: 1 Kč/min -> Cost: 5 * 1 + 5 * 0,2 = 6 Kč
        // Call 4: 17:30:00 - 17:40:00 (10 minutes) -> Rate: 1 Kč/min -> Cost: 5 * 0,5 + 5 * 0,2 = 3,5 Kč
        // Total Cost: 5 + 6 + 6 + 3,50 = 20,50 - 11 = 9,50 Kč (promo rules: calls to most frequent number are not charged, 5 minutes discounted, excluding rush hour discount)

        BigDecimal expectedCost = new BigDecimal("9.50");

        BigDecimal calculatedCost = calculator.calculate(phoneLog);
        assertEquals(expectedCost, calculatedCost);
    }
}
