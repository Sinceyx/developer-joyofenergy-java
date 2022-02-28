package uk.tw.energy.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import uk.tw.energy.dao.MeterReadingRepo;
import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.po.MeterReadingPo;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MeterReadingServiceTest {

    @InjectMocks
    private MeterReadingService meterReadingService;
    @Mock
    private MeterReadingRepo meterReadingRepo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenMeterIdThatDoesNotExistShouldReturnNull() {
        Mockito.when(meterReadingRepo.findBySmartMeterId("unknown-id")).thenReturn(new ArrayList<>());
        assertThat(meterReadingService.getReadings("unknown-id")).isEqualTo(Optional.empty());
    }

    @Test
    public void givenMeterReadingThatExistsShouldReturnMeterReadings() {
        List<MeterReadingPo> expectResult = new ArrayList<>();

        Mockito.when(meterReadingRepo.findBySmartMeterId("random-id")).thenReturn(expectResult);
        assertThat(meterReadingService.getReadings("random-id")).isEqualTo(Optional.of(expectResult));
    }

    @Test
    public void givenSmartMeterIdShouldReturnPrevWeekMeterReadings() {
        String smartId = "SmartId";
        Instant startTime = Instant.now().minusSeconds(60*60*24*8);
        Instant endTime = Instant.now().minusSeconds(60*60*24*1);
        MeterReadingPo meterReadingPoOfTheDayPast8 = new MeterReadingPo(smartId,startTime.plusSeconds(1000),BigDecimal.valueOf(0.9));
        MeterReadingPo meterReadingPoOfTheDayPast1 = new MeterReadingPo(smartId,endTime.minusSeconds(1000),BigDecimal.valueOf(2.3));
        List<MeterReadingPo> readings = Arrays.asList(meterReadingPoOfTheDayPast8,meterReadingPoOfTheDayPast1);
        Mockito.when(meterReadingRepo.findBySmartMeterIdAndTimeBetween(smartId, startTime,endTime)).thenReturn(readings);
        Optional<List<ElectricityReading>> meterReadingPos = meterReadingService.getPrevWeekReadingsBySmartId(smartId);
        boolean actual = false;
        if(meterReadingPos.isPresent()){
            actual = meterReadingPos.get().stream().allMatch(ele->ele.getTime().isBefore(endTime)&&ele.getTime().isAfter(startTime));
        }
        assertThat(actual).isTrue();
    }
}
