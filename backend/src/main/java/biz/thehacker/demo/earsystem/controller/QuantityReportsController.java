package biz.thehacker.demo.earsystem.controller;

import biz.thehacker.demo.earsystem.model.GenericResponse;
import biz.thehacker.demo.earsystem.model.QuantityReport;
import biz.thehacker.demo.earsystem.repository.QuantityReportsRepository;
import biz.thehacker.demo.earsystem.soap.mitteilung.MitteilungsServicePort;
import biz.thehacker.demo.earsystem.soap.mitteilung.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/quantityReports")
public class QuantityReportsController {

    private static final Logger log = LoggerFactory.getLogger(QuantityReportsController.class);

    private final QuantityReportsRepository quantityReportsRepository;
    private final MitteilungsServicePort mitteilungsServicePort;

    public QuantityReportsController(
            QuantityReportsRepository quantityReportsRepository,
            MitteilungsServicePort mitteilungsServicePort
    ) {
        this.quantityReportsRepository = quantityReportsRepository;
        this.mitteilungsServicePort = mitteilungsServicePort;
    }

    @GetMapping
    public List<QuantityReport> listQuantityReports() {
        return quantityReportsRepository.queryAll();
    }

    @PostMapping
    public GenericResponse addQuantityReport(@RequestBody QuantityReport quantityReport) {
        quantityReportsRepository.add(quantityReport);

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

        return GenericResponse.success();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler
    public GenericResponse onHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return GenericResponse.failure("Invalid JSON: " + exception.getMessage());
    }
}
