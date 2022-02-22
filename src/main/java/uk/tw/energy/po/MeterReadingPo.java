package uk.tw.energy.po;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "meter_readings")
public class MeterReadingPo {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;

    @Column(name = "smart_meter_id")
    private String smartMeterId;

    @Column(name = "time")
    private Instant time;

    @Column(name = "reading")
    private BigDecimal reading;

    public String getSmartMeterId() {
        return smartMeterId;
    }

    public void setSmartMeterId(String smartMeterId) {
        this.smartMeterId = smartMeterId;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public BigDecimal getReading() {
        return reading;
    }

    public void setReading(BigDecimal reading) {
        this.reading = reading;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MeterReadingPo(String smartMeterId, Instant time, BigDecimal reading) {
        this.smartMeterId = smartMeterId;
        this.time = time;
        this.reading = reading;
    }

    public MeterReadingPo() {
    }
}
