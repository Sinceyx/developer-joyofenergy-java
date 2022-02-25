package uk.tw.energy.service;

import org.springframework.stereotype.Service;
import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.domain.PricePlan;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CalculateCostService {

    private final PricePlanService pricePlanService;
    private final MeterReadingService meterReadingService;

    public CalculateCostService(PricePlanService pricePlanService, MeterReadingService meterReadingService) {
        this.pricePlanService = pricePlanService;
        this.meterReadingService = meterReadingService;
    }

    public BigDecimal calculateCostOfPrevWeek(String smartMeterId) {
        Optional<List<ElectricityReading>> prevWeekReadings = meterReadingService.getPrevWeekReadingsBySmartId(smartMeterId);
        PricePlan pricePlan = pricePlanService.findPricePlanBySmartMeterId(smartMeterId);
        if(prevWeekReadings.isPresent()){
            return pricePlanService.calculateCost(prevWeekReadings.get(),pricePlan);
        }else {
            return BigDecimal.ZERO;
        }
    }
}
