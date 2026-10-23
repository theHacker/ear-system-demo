package biz.thehacker.demo.earsystem;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;

import static biz.thehacker.demo.earsystem.Tables.QUANTITY_REPORT;

@Repository
public class QuantityReportsRepository {

    private final DSLContext create;

    public QuantityReportsRepository(DSLContext create) {
        this.create = create;
    }

    /** Returns all quantity reports order by year and month */
    public List<QuantityReport> queryAll() {
        return create
                .select(
                        QUANTITY_REPORT.AMOUNT,
                        QUANTITY_REPORT.UNIT,
                        QUANTITY_REPORT.YEAR,
                        QUANTITY_REPORT.MONTH,
                        QUANTITY_REPORT.DEVICE_TYPE_NUMBER,
                        QUANTITY_REPORT.REGISTRATION_NUMBER,
                        QUANTITY_REPORT.DECLARATION_OF_TRUTH
                )
                .from(QUANTITY_REPORT)
                .orderBy(QUANTITY_REPORT.YEAR, QUANTITY_REPORT.MONTH)
                .fetch(record -> new QuantityReport(
                        record.get(QUANTITY_REPORT.AMOUNT).stripTrailingZeros(),
                        Unit.valueOf(record.get(QUANTITY_REPORT.UNIT).name()),
                        YearMonth.of(record.get(QUANTITY_REPORT.YEAR), record.get(QUANTITY_REPORT.MONTH)),
                        record.get(QUANTITY_REPORT.DEVICE_TYPE_NUMBER),
                        record.get(QUANTITY_REPORT.REGISTRATION_NUMBER),
                        record.get(QUANTITY_REPORT.DECLARATION_OF_TRUTH)
                ));
    }
}
