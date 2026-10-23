package biz.thehacker.demo.earsystem;

import java.math.BigDecimal;
import java.time.YearMonth;

public record QuantityReport(
        BigDecimal amount, // TODO check later, likely this must be a BigDecimal to not loose precision
        Unit unit,
        YearMonth yearMonth,
        int deviceNumber, // TODO clarify: likely a number-like string with fixed/limited number of digits
        int registrationNumber, // TODO clarify: likely a number-like string with fixed/limited number of digits
        boolean declarationOfTruth
) {
    public QuantityReport {
        if (amount.signum() == -1) {
            throw new IllegalArgumentException("Cannot have negative amount. " + amount + " is not valid.");
        }

        int maxDecimalPlaces = unit.getMaxDecimalPlaces();
        int digits = amount.stripTrailingZeros().scale(); // TODO check if correct

        if (digits > maxDecimalPlaces) {
            throw new IllegalArgumentException(
                    "Invalid number of decimal places. Value " + amount + " with unit " + unit +
                            " may only have at most " + maxDecimalPlaces + " decimal places."
            );
        }
    }
}
