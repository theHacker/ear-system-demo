package biz.thehacker.demo.earsystem.controller;

import biz.thehacker.demo.earsystem.model.GenericResponse;
import biz.thehacker.demo.earsystem.model.QuantityReport;
import biz.thehacker.demo.earsystem.repository.QuantityReportsRepository;
import biz.thehacker.demo.earsystem.service.QuantityReportsService;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/quantityReports")
public class QuantityReportsController {

    private final QuantityReportsRepository quantityReportsRepository;
    private final QuantityReportsService quantityReportsService;

    public QuantityReportsController(
            QuantityReportsRepository quantityReportsRepository,
            QuantityReportsService quantityReportsService
    ) {
        this.quantityReportsRepository = quantityReportsRepository;
        this.quantityReportsService = quantityReportsService;
    }

    @GetMapping
    public List<QuantityReport> listQuantityReports() {
        return quantityReportsRepository.queryAll();
    }

    @PostMapping
    public GenericResponse addQuantityReport(@RequestBody QuantityReport quantityReport) {
        quantityReportsRepository.add(quantityReport);
        quantityReportsService.submitNewReport(quantityReport);

        return GenericResponse.success();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler
    public GenericResponse onHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return GenericResponse.failure("Invalid JSON: " + exception.getMessage());
    }
}
