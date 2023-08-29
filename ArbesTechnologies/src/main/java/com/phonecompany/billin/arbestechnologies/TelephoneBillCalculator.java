package com.phonecompany.billin.arbestechnologies;

import java.io.IOException;
import java.math.BigDecimal;

public interface TelephoneBillCalculator {
    BigDecimal calculate(String phoneLog) throws IOException;
}
