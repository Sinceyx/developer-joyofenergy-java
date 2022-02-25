package uk.tw.energy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import uk.tw.energy.service.CalculateCostService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/calculate-cost")
public class CalculateCostController {

    private final CalculateCostService service;

    public CalculateCostController(CalculateCostService service) {
        this.service = service;
    }

    @ResponseBody
    @RequestMapping("/prev-week-cost/{smartMeterId}")
    public ResponseEntity<BigDecimal> calculatePrevWeekCost(String smartMeterId) {
        BigDecimal cost = service.calculateCostOfPrevWeek(smartMeterId);
        return ResponseEntity.ok(cost);
    }
}

