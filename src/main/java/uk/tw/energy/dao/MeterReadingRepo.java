package uk.tw.energy.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uk.tw.energy.po.MeterReadingPo;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface MeterReadingRepo extends JpaRepository<MeterReadingPo, Id> {

    Optional<List<MeterReadingPo>> findBySmartMeterId(String smartMeterId) ;

    @Query(value = "select smart_meter_id,reading,time from meter_readings where smart_meter_id = ?1 and time > ?2 and time < ?3",nativeQuery = true)
    Optional<List<MeterReadingPo>> findBySmartMeterIdWithStartTimeAndEndTime(String smartMeterId, Instant startTime, Instant endTime);
}
