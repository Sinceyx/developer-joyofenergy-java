package uk.tw.energy.service;

import org.springframework.stereotype.Service;
import uk.tw.energy.dao.PricePlanRepo;
import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.domain.PricePlan;
import uk.tw.energy.po.PeakTimeMultiplierPo;
import uk.tw.energy.po.PricePlanPo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PricePlanService {

    private final MeterReadingService meterReadingService;
    private final PricePlanRepo pricePlanRepo;
    private static final double SECONDS_PER_HOUR = 3600.0;
    public PricePlanService(MeterReadingService meterReadingService, PricePlanRepo pricePlanRepo) {
        this.meterReadingService = meterReadingService;
        this.pricePlanRepo = pricePlanRepo;
    }

    public void storePricePlans(List<PricePlan> pricePlans) {
        pricePlanRepo.saveAll(pricePlans.stream().map(
                ele -> new PricePlanPo(ele.getEnergySupplier(), ele.getPlanName(), ele.getUnitRate(), ele.getPeakTimeMultipliers().stream().map(
                        peakTimeMultiplier -> new PeakTimeMultiplierPo(peakTimeMultiplier.getDayOfWeek().getValue(), peakTimeMultiplier.getMultiplier()))
                        .collect(Collectors.toList())))
                .collect(Collectors.toList()));
    }

    public void storePricePlan(PricePlan pricePlan) {
        pricePlanRepo.save(new PricePlanPo(pricePlan.getEnergySupplier(),
                pricePlan.getPlanName(),
                pricePlan.getUnitRate(),
                pricePlan.getPeakTimeMultipliers().stream().map(
                        ele -> new PeakTimeMultiplierPo(ele.getDayOfWeek().getValue(),ele.getMultiplier())
                ).collect(Collectors.toList())));
    }

    public Optional<Map<String, BigDecimal>> getConsumptionCostOfElectricityReadingsForEachPricePlan(String smartMeterId) {
        Optional<List<ElectricityReading>> electricityReadings = meterReadingService.getReadings(smartMeterId);

        if (!electricityReadings.isPresent()) {
            return Optional.empty();
        }
        List<PricePlanPo> pricePlans = pricePlanRepo.findAll();
        return Optional.of(pricePlans.stream().collect(Collectors.toMap(PricePlanPo::getPlanName, t -> calculateCost(electricityReadings.get(),PricePlan.build(t)))));
    }

    private BigDecimal calculateCost(List<ElectricityReading> electricityReadings, PricePlan pricePlan) {
        BigDecimal average = calculateAverageReading(electricityReadings);
        BigDecimal timeElapsed = calculateTimeElapsed(electricityReadings);
        BigDecimal averagedCost = average.divide(timeElapsed, RoundingMode.HALF_UP);
        return averagedCost.multiply(pricePlan.getUnitRate());
    }

    private BigDecimal calculateAverageReading(List<ElectricityReading> electricityReadings) {
        BigDecimal summedReadings = electricityReadings.stream().map(ElectricityReading::getReading).reduce(BigDecimal.ZERO, BigDecimal::add);

        return summedReadings.divide(BigDecimal.valueOf(electricityReadings.size()), RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTimeElapsed(List<ElectricityReading> electricityReadings) {
        ElectricityReading first = electricityReadings.stream().min(Comparator.comparing(ElectricityReading::getTime)).get();
        ElectricityReading last = electricityReadings.stream().max(Comparator.comparing(ElectricityReading::getTime)).get();

        return BigDecimal.valueOf(Duration.between(first.getTime(), last.getTime()).getSeconds() / SECONDS_PER_HOUR);
    }

    public PricePlan findPricePlanBySmartMeterId(String smartMeterId) {
        return PricePlan.build(pricePlanRepo.findOneBySmartMeterId(smartMeterId));
    }
}
