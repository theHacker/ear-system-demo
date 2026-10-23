package biz.thehacker.demo.earsystem.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.YearMonth;

import static biz.thehacker.demo.earsystem.model.Unit.*;
import static java.time.Month.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuantityReportTest {

    @Test
    void construction() {
        assertDoesNotThrow(() -> {
            new QuantityReport(new BigDecimal("123"), PIECE, YearMonth.of(2012, APRIL), 12345, 565676, true);
        });
    }

    @Test
    void validUnitVsDecimalPlaces_kilogram() {
        assertDoesNotThrow(() -> {
            new QuantityReport(new BigDecimal("123456"), KILOGRAM, YearMonth.of(2010, JANUARY), 6666, 77777, true);
            new QuantityReport(new BigDecimal("123456.0"), KILOGRAM, YearMonth.of(2010, JANUARY), 6666, 77777, true);
            new QuantityReport(new BigDecimal("123456.1"), KILOGRAM, YearMonth.of(2010, JANUARY), 6666, 77777, true);
            new QuantityReport(new BigDecimal("123456.12"), KILOGRAM, YearMonth.of(2010, JANUARY), 6666, 77777, true);
            new QuantityReport(new BigDecimal("123456.123"), KILOGRAM, YearMonth.of(2010, JANUARY), 6666, 77777, true);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new QuantityReport(new BigDecimal("123456.1234"), KILOGRAM, YearMonth.of(2010, JANUARY), 6666, 77777, true);
        });
    }

    @Test
    void validUnitVsDecimalPlaces_piece() {
        assertDoesNotThrow(() -> {
            new QuantityReport(new BigDecimal("123456"), PIECE, YearMonth.of(2015, JULY), 654654, 99, false);
            new QuantityReport(new BigDecimal("123456.0"), PIECE, YearMonth.of(2015, JULY), 654654, 99, false);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new QuantityReport(new BigDecimal("123456.1"), PIECE, YearMonth.of(2015, JULY), 654654, 99, false);
        });
    }

    @Test
    void validUnitVsDecimalPlaces_ton() {
        assertDoesNotThrow(() -> {
            new QuantityReport(new BigDecimal("123456"), TON, YearMonth.of(2012, JULY), 5555, 11, false);
            new QuantityReport(new BigDecimal("123456.0"), TON, YearMonth.of(2012, JULY), 5555, 11, false);
            new QuantityReport(new BigDecimal("123456.1"), TON, YearMonth.of(2012, JULY), 5555, 11, false);
            new QuantityReport(new BigDecimal("123456.12"), TON, YearMonth.of(2012, JULY), 5555, 11, false);
            new QuantityReport(new BigDecimal("123456.123"), TON, YearMonth.of(2012, JULY), 5555, 11, false);
            new QuantityReport(new BigDecimal("123456.1234"), TON, YearMonth.of(2012, JULY), 5555, 11, false);
            new QuantityReport(new BigDecimal("123456.12345"), TON, YearMonth.of(2012, JULY), 5555, 11, false);
            new QuantityReport(new BigDecimal("123456.123456"), TON, YearMonth.of(2012, JULY), 5555, 11, false);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new QuantityReport(new BigDecimal("123456.1234567"), TON, YearMonth.of(2012, JULY), 5555, 11, false);
        });
    }
}
