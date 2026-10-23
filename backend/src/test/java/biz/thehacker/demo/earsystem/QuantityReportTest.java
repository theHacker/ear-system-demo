package biz.thehacker.demo.earsystem;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.YearMonth;

import static biz.thehacker.demo.earsystem.Unit.*;
import static java.time.Month.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuantityReportTest {

    @Test
    void construction() {
        assertDoesNotThrow(() -> {
            new QuantityReport(new BigDecimal("123"), STUECK, YearMonth.of(2012, APRIL), 12345, 565676, true);
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
    void validUnitVsDecimalPlaces_stueck() {
        assertDoesNotThrow(() -> {
            new QuantityReport(new BigDecimal("123456"), STUECK, YearMonth.of(2015, JULY), 654654, 99, false);
            new QuantityReport(new BigDecimal("123456.0"), STUECK, YearMonth.of(2015, JULY), 654654, 99, false);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new QuantityReport(new BigDecimal("123456.1"), STUECK, YearMonth.of(2015, JULY), 654654, 99, false);
        });
    }

    @Test
    void validUnitVsDecimalPlaces_tonnen() {
        assertDoesNotThrow(() -> {
            new QuantityReport(new BigDecimal("123456"), TONNEN, YearMonth.of(2012, JULY), 5555, 11, false);
            new QuantityReport(new BigDecimal("123456.0"), TONNEN, YearMonth.of(2012, JULY), 5555, 11, false);
            new QuantityReport(new BigDecimal("123456.1"), TONNEN, YearMonth.of(2012, JULY), 5555, 11, false);
            new QuantityReport(new BigDecimal("123456.12"), TONNEN, YearMonth.of(2012, JULY), 5555, 11, false);
            new QuantityReport(new BigDecimal("123456.123"), TONNEN, YearMonth.of(2012, JULY), 5555, 11, false);
            new QuantityReport(new BigDecimal("123456.1234"), TONNEN, YearMonth.of(2012, JULY), 5555, 11, false);
            new QuantityReport(new BigDecimal("123456.12345"), TONNEN, YearMonth.of(2012, JULY), 5555, 11, false);
            new QuantityReport(new BigDecimal("123456.123456"), TONNEN, YearMonth.of(2012, JULY), 5555, 11, false);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new QuantityReport(new BigDecimal("123456.1234567"), TONNEN, YearMonth.of(2012, JULY), 5555, 11, false);
        });
    }
}
