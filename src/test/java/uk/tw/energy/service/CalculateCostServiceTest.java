package uk.tw.energy.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CalculateCostServiceTest {

    @InjectMocks
    private CalculateCostService service;

    private static final String SMART_METER_ID = "SMART_METER_ID";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenSmartMeterIdShouldReturnCostOfPrevWeek() {
        assertThat(service.calculateCostOfPrevWeek(SMART_METER_ID)).isEqualTo(BigDecimal.TEN);
    }
}
