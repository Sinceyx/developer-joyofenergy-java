package uk.tw.energy.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.domain.PricePlan;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CalculateCostServiceTest {

    @InjectMocks
    private CalculateCostService service;
    @Mock
    private MeterReadingService meterReadingService;
    @Mock
    private PricePlanService pricePlanService;

    private static final String SMART_METER_ID = "SMART_METER_ID";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenSmartMeterIdShouldReturnCostOfPrevWeek() {

        Instant startTime = Instant.now().minusSeconds(60*60*24*8);
        Instant endTime = Instant.now().minusSeconds(60*60*24*1);
        ElectricityReading reading = new ElectricityReading(startTime.plusSeconds(2000),BigDecimal.valueOf(2));
        ElectricityReading otherReadings = new ElectricityReading(endTime.minusSeconds(2000),BigDecimal.valueOf(8));
        List<ElectricityReading> readings = Arrays.asList(reading,otherReadings);
        Mockito.when(meterReadingService.getPrevWeekReadingsBySmartId(SMART_METER_ID)).thenReturn(Optional.of(readings));
        PricePlan plan = new PricePlan("price_plan_name","GreenEnergy",BigDecimal.TEN);
        Mockito.when(pricePlanService.findPricePlanBySmartMeterId(SMART_METER_ID)).thenReturn(plan);

        assertThat(service.calculateCostOfPrevWeek(SMART_METER_ID)).isEqualTo(BigDecimal.TEN);
    }
}
