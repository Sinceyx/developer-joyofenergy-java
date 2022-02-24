package uk.tw.energy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/calculate-cost")
public class CalculateCostController {

    public ResponseEntity<BigDecimal> calculatePrevWeekCost(String smartMeterId) {

        return ResponseEntity.ok(BigDecimal.ONE);
    }
}

