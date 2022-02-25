package uk.tw.energy.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import uk.tw.energy.dao.PricePlanRepo;
import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.domain.PricePlan;
import uk.tw.energy.po.PricePlanPo;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PricePlanServiceTest {

    private static final String PRICE_PLAN_1_ID = "PRICE_PLAN_1_ID";
    private static final String PRICE_PLAN_2_ID = "PRICE_PLAN_2_ID";
    private static final String PRICE_PLAN_3_ID = "PRICE_PLAN_3_ID";
    @Mock
    private PricePlanRepo pricePlanRepo;

    @Mock
    private MeterReadingService meterReadingService;

    @InjectMocks
    private PricePlanService service;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void storePricePlans() {
        List<PricePlan> pricePlans = new ArrayList<>();
        service.storePricePlans(pricePlans);
        Mockito.verify(pricePlanRepo,Mockito.times(1)).saveAll(Mockito.anyList());
    }

    @Test
    void storePricePlan() {
        PricePlan pricePlan = new PricePlan("planName","supplier", BigDecimal.TEN,new ArrayList<>());
        service.storePricePlan(pricePlan);
        Mockito.verify(pricePlanRepo,Mockito.times(1)).save(Mockito.isA(PricePlanPo.class));
    }

    @Test
    void givenExistSmarterMeterIdShouldReturnConsumptionCostOfElectricityReadingsForEachPricePlan() {
        ElectricityReading electricityReading = new ElectricityReading(Instant.now().minusSeconds(3600), BigDecimal.valueOf(15.0));
        ElectricityReading otherReading = new ElectricityReading(Instant.now(), BigDecimal.valueOf(5.0));
        Mockito.when(meterReadingService.getReadings(Mockito.anyString())).thenReturn(Optional.of(Arrays.asList(electricityReading,otherReading)));

        List<PricePlanPo> fakePricePlan = makePricePlanList();
        Mockito.when(pricePlanRepo.findAll()).thenReturn(fakePricePlan);
        Map<String, BigDecimal> expectedPricePlanToCost = new HashMap<>();
        expectedPricePlanToCost.put(PRICE_PLAN_1_ID, BigDecimal.valueOf(50.0));
        expectedPricePlanToCost.put(PRICE_PLAN_2_ID, BigDecimal.valueOf(10.0));
        expectedPricePlanToCost.put(PRICE_PLAN_3_ID, BigDecimal.valueOf(20.0));
        Optional<Map<String, BigDecimal>> actual = service.getConsumptionCostOfElectricityReadingsForEachPricePlan("smartMeterId");
        assertThat(actual.orElse(null)).isEqualTo(expectedPricePlanToCost);
    }

    @Test
    void givenNotExistSmarterMeterIdShouldReturnOptionNull() {
        Mockito.when(meterReadingService.getReadings(Mockito.anyString())).thenReturn(Optional.empty());
        Optional<Map<String, BigDecimal>> actual = service.getConsumptionCostOfElectricityReadingsForEachPricePlan("smartMeterId");
        assertThat(actual.isPresent()).isEqualTo(false);
    }

    private List<PricePlanPo> makePricePlanList() {
        PricePlan pricePlan1 = new PricePlan(PRICE_PLAN_1_ID, null, BigDecimal.valueOf(5), null);
        PricePlan pricePlan2 = new PricePlan(PRICE_PLAN_2_ID, null, BigDecimal.ONE, null);
        PricePlan pricePlan3 = new PricePlan(PRICE_PLAN_3_ID, null, BigDecimal.valueOf(2), null);
        List<PricePlanPo> pricePlanPos = new ArrayList<>();
        pricePlanPos.add(PricePlanPo.build(pricePlan1));
        pricePlanPos.add(PricePlanPo.build(pricePlan2));
        pricePlanPos.add(PricePlanPo.build(pricePlan3));
        return pricePlanPos;
    }

    @Test
    void givenSmartIdShouldReturnPricePlan(){
        PricePlan pricePlan = new PricePlan("plan_name","evil",BigDecimal.ONE);
        String smartMeterId = "smart_meter_id";
        Mockito.when(pricePlanRepo.findOneBySmartMeterId(smartMeterId)).thenReturn(PricePlanPo.build(pricePlan));
        assertThat(service.findPricePlanBySmartMeterId(smartMeterId).getPlanName()).isEqualTo("plan_name");
    }

    @Test
    void givenElectricityReadingListAndPricePlanShouldReturnCost(){
        PricePlan pricePlan = new PricePlan("plan_name","evil",BigDecimal.ONE);
        ElectricityReading electricityReading = new ElectricityReading(Instant.now().minusSeconds(7200), BigDecimal.valueOf(15.0));
        ElectricityReading otherReading = new ElectricityReading(Instant.now(), BigDecimal.valueOf(5.0));
        BigDecimal expected = BigDecimal.valueOf(20.0);
        assertThat(service.calculateCost(Arrays.asList(electricityReading,otherReading),pricePlan).longValue()).isEqualTo(expected.longValue());
    }
}