package uk.tw.energy.service;

import org.springframework.stereotype.Service;
import uk.tw.energy.dao.MeterReadingRepo;
import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.po.MeterReadingPo;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MeterReadingService {


    private final MeterReadingRepo meterReadingRepo;

    public MeterReadingService(MeterReadingRepo meterReadingRepo) {
        this.meterReadingRepo = meterReadingRepo;
    }


    public Optional<List<ElectricityReading>> getReadings(String smartMeterId) {
        List<MeterReadingPo> pos = meterReadingRepo.findBySmartMeterId(smartMeterId);
        List<ElectricityReading> readings = pos.stream().map(ele -> new ElectricityReading(ele.getTime(), ele.getReading())).collect(Collectors.toList());
        return Optional.of(readings);
    }

    public void storeReadings(String smartMeterId, List<ElectricityReading> electricityReadings) {
        List<MeterReadingPo> meterReadingPos = new ArrayList<>();
        electricityReadings.forEach(ele -> meterReadingPos.add(new MeterReadingPo(smartMeterId, ele.getTime(), ele.getReading())));
        meterReadingRepo.saveAll(meterReadingPos);
    }

    public Optional<List<ElectricityReading>> getPrevWeekReadingsBySmartId(String smartId) {
        Instant eightDaysAgo = Instant.now().minusSeconds(60 * 60 * 24 * 8);
        Instant oneDayAgo = Instant.now().minusSeconds(60 * 60 * 24 * 1);
        List<MeterReadingPo> meterReadingPos = meterReadingRepo.findBySmartMeterIdAndTimeBetween(smartId, eightDaysAgo, oneDayAgo);
        List<ElectricityReading> electricityReadings = new ArrayList<>(meterReadingPos.stream().map(ele -> new ElectricityReading(ele.getTime(), ele.getReading())).collect(Collectors.toList()));
        return Optional.of(electricityReadings);
    }
}
