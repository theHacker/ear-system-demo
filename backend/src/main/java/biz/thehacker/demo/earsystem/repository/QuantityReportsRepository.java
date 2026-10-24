package biz.thehacker.demo.earsystem.repository;

import biz.thehacker.demo.earsystem.enums.QuantityReportUnit;
import biz.thehacker.demo.earsystem.model.QuantityReport;
import biz.thehacker.demo.earsystem.model.Unit;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

import static biz.thehacker.demo.earsystem.Tables.QUANTITY_REPORT;

@Repository
public class QuantityReportsRepository {

    private final DSLContext create;

    public QuantityReportsRepository(DSLContext create) {
        this.create = create;
    }

    /** Returns all quantity reports ordered by year and month */
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

    /** Persists a new quantity report */
    public void add(QuantityReport quantityReport) {
        create
                .insertInto(
                        QUANTITY_REPORT,
                        QUANTITY_REPORT.ID,
                        QUANTITY_REPORT.AMOUNT,
                        QUANTITY_REPORT.UNIT,
                        QUANTITY_REPORT.YEAR,
                        QUANTITY_REPORT.MONTH,
                        QUANTITY_REPORT.DEVICE_TYPE_NUMBER,
                        QUANTITY_REPORT.REGISTRATION_NUMBER,
                        QUANTITY_REPORT.DECLARATION_OF_TRUTH
                )
                .values(
                        UUID.randomUUID(),
                        quantityReport.amount(),
                        QuantityReportUnit.valueOf(quantityReport.unit().name()),
                        (short) quantityReport.yearMonth().getYear(),
                        (byte) quantityReport.yearMonth().getMonthValue(),
                        quantityReport.deviceTypeNumber(),
                        quantityReport.registrationNumber(),
                        quantityReport.declarationOfTruth()
                )
                .execute();
    }
}
