package uk.tw.energy.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CalculateCostControllerTest {

    @InjectMocks
    private CalculateCostController controller;

    private static final String SMART_METER_ID = "SMART_METER_ID";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenSmartMeterIdShouldReturnPrevWeekCost(){
        assertThat(controller.calculatePrevWeekCost(SMART_METER_ID).getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
