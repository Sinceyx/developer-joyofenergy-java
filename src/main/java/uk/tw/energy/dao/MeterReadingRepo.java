package uk.tw.energy.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import uk.tw.energy.po.MeterReadingPo;

import java.util.List;
import java.util.Optional;

public interface MeterReadingRepo extends JpaRepository<MeterReadingPo, Id> {
    Optional<List<MeterReadingPo>> findBySmartMeterId(String smartMeterId);
}
