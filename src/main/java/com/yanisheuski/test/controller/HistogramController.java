package com.yanisheuski.test.controller;

import com.yanisheuski.test.service.HistogramService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/property-view/histogram")
public class HistogramController {

    private final HistogramService histogramService;

    public HistogramController(
            HistogramService histogramService
    ) {
        this.histogramService = histogramService;
    }

    @Operation(
            summary = "Get histogram"
    )
    @GetMapping("/{param}")
    public Map<String, Long> getHistogram(
            @PathVariable String param
    ) {

        return histogramService.getHistogram(param);
    }
}