package uk.tw.energy.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import uk.tw.energy.dao.MeterReadingRepo;
import uk.tw.energy.po.MeterReadingPo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Mockito.when(meterReadingRepo.findBySmartMeterId("unknown-id")).thenReturn(Optional.empty());
        assertThat(meterReadingService.getReadings("unknown-id")).isEqualTo(Optional.empty());
    }

    @Test
    public void givenMeterReadingThatExistsShouldReturnMeterReadings() {
        List<MeterReadingPo> expectResult = new ArrayList<>();

        Mockito.when(meterReadingRepo.findBySmartMeterId("random-id")).thenReturn(Optional.of(expectResult));
        assertThat(meterReadingService.getReadings("random-id")).isEqualTo(Optional.of(expectResult));
    }

}
