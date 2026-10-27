package biz.thehacker.demo.earsystem.service;

import biz.thehacker.demo.earsystem.model.QuantityReport;
import biz.thehacker.demo.earsystem.soap.mitteilung.MitteilungsServicePort;
import biz.thehacker.demo.earsystem.soap.mitteilung.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service to encapsulate the SOAP Web Service.
 */
@Service
public class QuantityReportsService {

    private static final Logger log = LoggerFactory.getLogger(QuantityReportsService.class);

    private final MitteilungsServicePort mitteilungsServicePort;

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
        } catch (ServiceException e) {
            log.warn("Could not transmit report via SOAP web service.");
            // TODO remember this and try again if web service is down
        }
    }
}
