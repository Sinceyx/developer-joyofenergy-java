package uk.tw.energy.service;

import org.springframework.stereotype.Service;
import uk.tw.energy.dao.MeterReadingRepo;
import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.po.MeterReadingPo;

import java.util.ArrayList;
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
        Optional<List<MeterReadingPo>> pos = meterReadingRepo.findBySmartMeterId(smartMeterId);
        List<ElectricityReading> readings = pos.isPresent()?pos.get().stream().map(ele -> new ElectricityReading(ele.getTime(),ele.getReading())).collect(Collectors.toList()):null;
        return Optional.ofNullable(readings);
    }

    public void storeReadings(String smartMeterId, List<ElectricityReading> electricityReadings) {
        List<MeterReadingPo> meterReadingPos = new ArrayList<>();
        electricityReadings.forEach(ele->{
            meterReadingPos.add(new MeterReadingPo(smartMeterId,ele.getTime(),ele.getReading()));
        });
        meterReadingRepo.saveAll(meterReadingPos);
    }
}
