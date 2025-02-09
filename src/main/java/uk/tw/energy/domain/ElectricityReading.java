package uk.tw.energy.domain;

import java.math.BigDecimal;
import java.time.Instant;

public class ElectricityReading {

    private Instant time;
    /**
     * kW
     */
    private BigDecimal reading;

    public ElectricityReading() { }

    public ElectricityReading(Instant time, BigDecimal reading) {
        this.time = time;
        this.reading = reading;
    }

    public BigDecimal getReading() {
        return reading;
    }

    public Instant getTime() {
        return time;
    }
}
