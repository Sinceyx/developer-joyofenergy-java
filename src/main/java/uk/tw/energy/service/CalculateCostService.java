package uk.tw.energy.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class CalculateCostService {

    private final PricePlanService pricePlanService;
    private final MeterReadingService meterReadingService;

    public CalculateCostService(PricePlanService pricePlanService, MeterReadingService meterReadingService) {
        this.pricePlanService = pricePlanService;
        this.meterReadingService = meterReadingService;
    }

    public BigDecimal calculateCostOfPrevWeek(String smartMeterId) {

        return BigDecimal.TEN;
    }
}
