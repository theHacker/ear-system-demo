package biz.thehacker.demo.earsystem.controller;

import biz.thehacker.demo.earsystem.repository.QuantityReportsRepository;
import biz.thehacker.demo.earsystem.model.QuantityReport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/quantityReports")
public class QuantityReportsController {

    private final QuantityReportsRepository quantityReportsRepository;

    public QuantityReportsController(QuantityReportsRepository quantityReportsRepository) {
        this.quantityReportsRepository = quantityReportsRepository;
    }

    @GetMapping
    public List<QuantityReport> listQuantityReports() {
        return quantityReportsRepository.queryAll();
    }
}
