package uk.tw.energy.domain;

import uk.tw.energy.po.PeakTimeMultiplierPo;
import uk.tw.energy.po.PricePlanPo;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

public class PricePlan {

    private String energySupplier;
    private String planName;
    /**
     * unit price per kWh
     */
    private BigDecimal unitRate;
    private List<PeakTimeMultiplier> peakTimeMultipliers;

    public PricePlan(String planName, String energySupplier, BigDecimal unitRate, List<PeakTimeMultiplier> peakTimeMultipliers) {
        this.planName = planName;
        this.energySupplier = energySupplier;
        this.unitRate = unitRate;
        this.peakTimeMultipliers = peakTimeMultipliers;
    }

    public PricePlan(String planName,String energySupplier,BigDecimal unitRate){
        this.planName = planName;
        this.energySupplier = energySupplier;
        this.unitRate = unitRate;
    }

    public static PricePlan build(PricePlanPo po){
        return new PricePlan(po.getPlanName(),po.getEnergySupplier(),po.getUnitRate());
    }
    public String getEnergySupplier() {
        return energySupplier;
    }

    public String getPlanName() {
        return planName;
    }

    public BigDecimal getUnitRate() {
        return unitRate;
    }

    public BigDecimal getPrice(LocalDateTime dateTime) {
        return peakTimeMultipliers.stream()
                .filter(multiplier -> multiplier.dayOfWeek.equals(dateTime.getDayOfWeek()))
                .findFirst()
                .map(multiplier -> unitRate.multiply(multiplier.multiplier))
                .orElse(unitRate);
    }

    public List<PeakTimeMultiplier> getPeakTimeMultipliers() {
        return peakTimeMultipliers;
    }

    public void setEnergySupplier(String energySupplier) {
        this.energySupplier = energySupplier;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public void setUnitRate(BigDecimal unitRate) {
        this.unitRate = unitRate;
    }

    public void setPeakTimeMultipliers(List<PeakTimeMultiplier> peakTimeMultipliers) {
        this.peakTimeMultipliers = peakTimeMultipliers;
    }
    public static class PeakTimeMultiplier {

        DayOfWeek dayOfWeek;
        BigDecimal multiplier;

        public PeakTimeMultiplier(DayOfWeek dayOfWeek, BigDecimal multiplier) {
            this.dayOfWeek = dayOfWeek;
            this.multiplier = multiplier;
        }

        public DayOfWeek getDayOfWeek() {
            return dayOfWeek;
        }

        public void setDayOfWeek(DayOfWeek dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
        }

        public BigDecimal getMultiplier() {
            return multiplier;
        }

        public void setMultiplier(BigDecimal multiplier) {
            this.multiplier = multiplier;
        }

        public static PeakTimeMultiplier build(PeakTimeMultiplierPo po){
            return new PeakTimeMultiplier(DayOfWeek.of(po.getDayOfWeek()),po.getMultiplier());
        }
    }
}
