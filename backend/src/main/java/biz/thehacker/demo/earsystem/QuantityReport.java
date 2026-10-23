package biz.thehacker.demo.earsystem;

import java.time.YearMonth;

public record QuantityReport(
        double amount, // TODO check later, likely this must be a BigDecimal to not loose precision
        String unit, // TODO clarify: did not find what SOAP type "ear:Masseinheit" really is
        YearMonth yearMonth,
        int deviceNumber, // TODO clarify: likely a number-like string with fixed/limited number of digits
        int registrationNumber, // TODO clarify: likely a number-like string with fixed/limited number of digits
        boolean declarationOfTruth
) {}
