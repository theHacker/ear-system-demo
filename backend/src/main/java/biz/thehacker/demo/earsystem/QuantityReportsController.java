package biz.thehacker.demo.earsystem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

import static biz.thehacker.demo.earsystem.Unit.*;
import static java.time.Month.*;

@RestController
@RequestMapping("/quantityReports")
public class QuantityReportsController {

    @GetMapping
    public List<QuantityReport> listQuantityReports() {
        // TODO fill we actual data
        return List.of(
                new QuantityReport(new BigDecimal("47.11"), KILOGRAM, YearMonth.of(2026, JANUARY), 12345, 99999, true),
                new QuantityReport(new BigDecimal("0"), STUECK, YearMonth.of(2026, FEBRUARY), -999, 0, false), // <- likely not realistic
                new QuantityReport(new BigDecimal("2000000000"), STUECK, YearMonth.of(2024, DECEMBER), 1236666987, 543210, true),
                new QuantityReport(new BigDecimal("12345678901234567890.123456"), TONNEN, YearMonth.of(2025, APRIL), Integer.MAX_VALUE, Integer.MIN_VALUE, false) // <- extreme values, also likely not realistic
        );
    }
}
