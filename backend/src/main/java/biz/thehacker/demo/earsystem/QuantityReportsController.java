package biz.thehacker.demo.earsystem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;
import java.util.List;

import static java.time.Month.*;

@RestController
@RequestMapping("/quantityReports")
public class QuantityReportsController {

    @GetMapping
    public List<QuantityReport> listQuantityReports() {
        // TODO fill we actual data
        return List.of(
                new QuantityReport(47.11, "m", YearMonth.of(2026, JANUARY), 12345, 99999, true),
                new QuantityReport(0, "kg", YearMonth.of(2026, FEBRUARY), -999, 0, false), // <- likely not realistic
                new QuantityReport(2_000_000_000, "ly", YearMonth.of(2024, DECEMBER), 1236666987, 543210, true),
                new QuantityReport(12345678901234567890d, "precision loose", YearMonth.of(2025, APRIL), Integer.MAX_VALUE, Integer.MIN_VALUE, false) // <- extreme values, also likely not realistic
        );
    }
}
