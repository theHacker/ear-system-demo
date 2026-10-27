package biz.thehacker.demo.earsystem.service;

import biz.thehacker.demo.earsystem.model.QuantityReport;
import biz.thehacker.demo.earsystem.soap.mitteilung.MitteilungsServicePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static biz.thehacker.demo.earsystem.service.QuantityReportsService.SECONDS_BETWEEN_RETRIES;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Service to encapsulate the SOAP Web Service.
 * <p>
 * Failed calls will be cached and retried up to 3 times. Only after 3 failed submission,
 * the report is lost and must be fixed manually.
 */
@Service
public class QuantityReportsService {

    public static final int SECONDS_BETWEEN_RETRIES = 30;
    public static final int MAX_RETRIES = 3;

    private static final Logger log = LoggerFactory.getLogger(QuantityReportsService.class);

    private final MitteilungsServicePort mitteilungsServicePort;

    private final List<FailedReportSubmission> failedReportSubmissions =
            Collections.synchronizedList(new LinkedList<>());

    public QuantityReportsService(MitteilungsServicePort mitteilungsServicePort) {
        this.mitteilungsServicePort = mitteilungsServicePort;
    }

    public void submitNewReport(QuantityReport quantityReport) {
        try {
            mitteilungsServicePort.submitIstInput(
                    quantityReport.amount(),
                    quantityReport.unit().getValue(),
                    quantityReport.yearMonth().getYear(),
                    quantityReport.yearMonth().getMonthValue(),
                    quantityReport.deviceTypeNumber(),
                    quantityReport.registrationNumber(),
                    quantityReport.declarationOfTruth()
            );
        } catch (Exception e) {
            log.warn("Could not transmit report via SOAP web service. Queuing for re-submission...", e);

            failedReportSubmissions.add(FailedReportSubmission.of(quantityReport));
        }
    }

    @Scheduled(fixedRate = 10, timeUnit = SECONDS)
    public void retryFailedSubmissions() {
        if (failedReportSubmissions.isEmpty()) {
            return;
        }

        List<FailedReportSubmission> jobsToRetry = failedReportSubmissions
                .stream()
                .filter(it -> it.nextRetry().isBefore(Instant.now()))
                .toList();
        if (jobsToRetry.isEmpty()) {
            return;
        }

        List<FailedReportSubmission> itemsToAddToList = new LinkedList<>();
        for (FailedReportSubmission failedReportSubmission : jobsToRetry) {
            log.info("Retrying failed submission {}...", failedReportSubmission);

            Exception exception = null;
            try {
                QuantityReport quantityReport = failedReportSubmission.quantityReport();
                mitteilungsServicePort.submitIstInput(
                        quantityReport.amount(),
                        quantityReport.unit().getValue(),
                        quantityReport.yearMonth().getYear(),
                        quantityReport.yearMonth().getMonthValue(),
                        quantityReport.deviceTypeNumber(),
                        quantityReport.registrationNumber(),
                        quantityReport.declarationOfTruth()
                );
            } catch (Exception e) {
                exception = e;
            }

            if (exception == null) {
                log.info("Retry succeeded for {}", failedReportSubmission);
            } else {
                if (failedReportSubmission.retriesCount() == MAX_RETRIES) {
                    log.error(
                            "Retry failed for submission {}. We tried {} times and give up now. " +
                                    "You must manually insert the data {} to the target system!",
                            failedReportSubmission,
                            failedReportSubmission.retriesCount(),
                            failedReportSubmission.quantityReport(),
                            exception
                    );
                } else {
                    log.warn(
                            "Retry failed for submission {}. Queueing for another try...",
                            failedReportSubmission,
                            exception
                    );
                    itemsToAddToList.add(FailedReportSubmission.ofRetry(failedReportSubmission));
                }
            }
        }

        failedReportSubmissions.removeAll(jobsToRetry);
        failedReportSubmissions.addAll(itemsToAddToList);
    }
}

record FailedReportSubmission(
        QuantityReport quantityReport,
        int retriesCount,
        Instant nextRetry
) {
    public static FailedReportSubmission of(QuantityReport quantityReport) {
        return new FailedReportSubmission(quantityReport, 0, Instant.now().plusSeconds(SECONDS_BETWEEN_RETRIES));
    }

    public static FailedReportSubmission ofRetry(FailedReportSubmission failedReportSubmission) {
        return new FailedReportSubmission(
                failedReportSubmission.quantityReport(),
                failedReportSubmission.retriesCount() + 1,
                Instant.now().plusSeconds(SECONDS_BETWEEN_RETRIES)
        );
    }

    @Override
    public String toString() {
        return "FailedReportSubmission %d@%s (retriesCount %d)".formatted(
                this.quantityReport.registrationNumber(),
                quantityReport.yearMonth(),
                retriesCount
        );
    }
}
