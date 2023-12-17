package com.service.bereport.controller;

import com.service.bereport.service.ProductReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping(value = "/productReport")
public class ProductReportController {

    @Autowired
    private ProductReportService productReportService;

    @GetMapping(value = "/generateProductReport")
    public void generate(@RequestParam(value = "startDate") String startDate,
                         @RequestParam(value = "endDate") String endDate,
                         @RequestParam(value = "status") String status) throws ParseException, IOException {
        productReportService.generateProductReport(startDate, endDate, status);
    }
}
