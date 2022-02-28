package uk.tw.energy.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import uk.tw.energy.service.CalculateCostService;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CalculateCostControllerTest {

    @InjectMocks
    private CalculateCostController controller;
    @Mock
    private CalculateCostService service;

    private static final String SMART_METER_ID = "bob";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenSmartMeterIdShouldReturnCostOfPrevWeek(){
        BigDecimal expected = BigDecimal.ONE;
        Mockito.when(service.calculateCostOfPrevWeek(SMART_METER_ID)).thenReturn(expected);
        assertThat(controller.calculatePrevWeekCost(SMART_METER_ID).getBody()).isEqualTo(expected);
    }
}
