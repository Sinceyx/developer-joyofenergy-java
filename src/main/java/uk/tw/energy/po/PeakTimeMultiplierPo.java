package uk.tw.energy.po;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "price_plan_peak_time_multiplier")
public class PeakTimeMultiplierPo {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "day_of_week")
    private int dayOfWeek;

    @Column(name = "multiplier")
    private BigDecimal multiplier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public BigDecimal getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(BigDecimal multiplier) {
        this.multiplier = multiplier;
    }

    public PeakTimeMultiplierPo(int dayOfWeek, BigDecimal multiplier) {
        this.dayOfWeek = dayOfWeek;
        this.multiplier = multiplier;
    }
}
