package uk.tw.energy.po;


import uk.tw.energy.domain.PricePlan;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "price_plan")
public class PricePlanPo {
    @Id
    @Column(name = "price_plan_id", nullable = false)
    private Long pricePlanId;

    @Column(name = "energy_supplier")
    private String energySupplier;
    @Column(name = "plan_name")
    private String planName;
    @Column(name = "unit_rate")
    private BigDecimal unitRate; // unit price per kWh
    @OneToMany(targetEntity = PeakTimeMultiplierPo.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "price_plan_id")
    private List<PeakTimeMultiplierPo> peakTimeMultipliers;

    public Long getPricePlanId() {
        return pricePlanId;
    }

    public void setPricePlanId(Long pricePlanId) {
        this.pricePlanId = pricePlanId;
    }

    public String getEnergySupplier() {
        return energySupplier;
    }

    public void setEnergySupplier(String energySupplier) {
        this.energySupplier = energySupplier;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public BigDecimal getUnitRate() {
        return unitRate;
    }

    public void setUnitRate(BigDecimal unitRate) {
        this.unitRate = unitRate;
    }

    public List<PeakTimeMultiplierPo> getPeakTimeMultipliers() {
        return peakTimeMultipliers;
    }

    public void setPeakTimeMultipliers(List<PeakTimeMultiplierPo> peakTimeMultipliers) {
        this.peakTimeMultipliers = peakTimeMultipliers;
    }

    public PricePlan toPricePlan(){
        PricePlan pricePlan = new PricePlan(this.planName,this.getEnergySupplier(),this.unitRate,this.peakTimeMultipliers.stream().map(ele->new PricePlan.PeakTimeMultiplier(DayOfWeek.of(ele.getDayOfWeek()),ele.getMultiplier())).collect(Collectors.toList()));
        return pricePlan;
    }
}
