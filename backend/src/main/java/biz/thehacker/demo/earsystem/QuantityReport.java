package biz.thehacker.demo.earsystem;

import java.math.BigDecimal;
import java.time.YearMonth;

public record QuantityReport(
        BigDecimal amount,
        Unit unit,
        YearMonth yearMonth,
        int deviceTypeNumber, // see https://test.ear-system.de/ear-soap/v2/doc/xml_ear_Geraeteart.html
        int registrationNumber, // "WEEE-Reg.-Nr. DE", see https://test.ear-system.de/ear-soap/v2/doc/service_ear_MitteilungsService.html
        boolean declarationOfTruth
) {
    public QuantityReport {
        if (amount.signum() == -1) {
            throw new IllegalArgumentException("Cannot have negative amount. " + amount + " is not valid.");
        }

        int maxDecimalPlaces = unit.getMaxDecimalPlaces();
        int digits = amount.stripTrailingZeros().scale();

        if (digits > maxDecimalPlaces) {
            throw new IllegalArgumentException(
                    "Invalid number of decimal places. Value " + amount + " with unit " + unit +
                            " may only have at most " + maxDecimalPlaces + " decimal places."
            );
        }
    }
}
