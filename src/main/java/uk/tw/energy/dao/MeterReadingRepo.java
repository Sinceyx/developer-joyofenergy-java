package uk.tw.energy.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uk.tw.energy.po.MeterReadingPo;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MeterReadingRepo extends JpaRepository<MeterReadingPo, Id> {

    Optional<List<MeterReadingPo>> findBySmartMeterId(String smartMeterId) ;

    @Query(value = "SELECT id,smart_meter_id,reading,read_time FROM meter_readings as mr WHERE mr.smart_meter_id=:smartMeterId and mr.read_time>:startTime and mr.read_time<:endTime",nativeQuery = true)
    Optional<List<MeterReadingPo>> findBySmartMeterIdWithStartTimeAndEndTime(@Param("smartMeterId") String smartMeterId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

}
