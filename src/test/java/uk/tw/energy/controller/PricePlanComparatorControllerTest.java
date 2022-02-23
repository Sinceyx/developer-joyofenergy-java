package uk.tw.energy.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import uk.tw.energy.service.AccountService;
import uk.tw.energy.service.PricePlanService;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PricePlanComparatorControllerTest {

    private final static String PRICE_PLAN_ID_KEY = "pricePlanId";
    private static final String PRICE_PLAN_1_ID = "test-supplier";
    private static final String PRICE_PLAN_2_ID = "best-supplier";
    private static final String PRICE_PLAN_3_ID = "second-best-supplier";
    private static final String SMART_METER_ID = "smart-meter-id";
    private static final String PRICE_PLAN_COMPARISONS_KEY = "pricePlanComparisons";
    @InjectMocks
    private PricePlanComparatorController controller;
    @Mock
    private AccountService accountService;
    @Mock
    private PricePlanService pricePlanService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenExistSmartMeterIdShouldCalculateCostByMeterReadingsForEveryPricePlan() {
        Mockito.when(accountService.getPricePlanIdForSmartMeterId(SMART_METER_ID)).thenReturn(PRICE_PLAN_1_ID);
        Map<String,BigDecimal> map = new HashMap<>();
        map.put(PRICE_PLAN_1_ID,BigDecimal.ONE);
        map.put(PRICE_PLAN_2_ID,BigDecimal.TEN);
        map.put(PRICE_PLAN_3_ID,BigDecimal.ZERO);
        Mockito.when(pricePlanService.getConsumptionCostOfElectricityReadingsForEachPricePlan(SMART_METER_ID))
                .thenReturn(Optional.of(map));
        Map<String,Object> expected = new HashMap<>();
        expected.put(PRICE_PLAN_COMPARISONS_KEY,map);
        expected.put(PRICE_PLAN_ID_KEY,PRICE_PLAN_1_ID);

        assertThat(controller.calculatedCostForEachPricePlan(SMART_METER_ID).getBody()).isEqualTo(expected);
    }

    @Test
    public void shouldRecommendCheapestPricePlansNoLimitForMeterUsage() {
        Map<String,BigDecimal> map = new HashMap<>();
        map.put(PRICE_PLAN_1_ID,BigDecimal.ONE);
        map.put(PRICE_PLAN_2_ID,BigDecimal.TEN);
        map.put(PRICE_PLAN_3_ID,BigDecimal.valueOf(8L));
        Mockito.when(pricePlanService.getConsumptionCostOfElectricityReadingsForEachPricePlan(SMART_METER_ID)).thenReturn(Optional.of(map));

        List<Map.Entry<String,BigDecimal>> expected  = new ArrayList<>(map.entrySet());
        expected.sort(Map.Entry.comparingByValue());

        assertThat(controller.recommendCheapestPricePlans(SMART_METER_ID, null).getBody()).isEqualTo(expected);
    }


    @Test
    public void shouldRecommendLimitedCheapestPricePlansForMeterUsage() {
        int limit = 2;
        Map<String,BigDecimal> map = new HashMap<>();
        map.put(PRICE_PLAN_1_ID,BigDecimal.ONE);
        map.put(PRICE_PLAN_2_ID,BigDecimal.TEN);
        map.put(PRICE_PLAN_3_ID,BigDecimal.valueOf(8L));
        Mockito.when(pricePlanService.getConsumptionCostOfElectricityReadingsForEachPricePlan(SMART_METER_ID)).thenReturn(Optional.of(map));

        List<Map.Entry<String,BigDecimal>> expected  = new ArrayList<>(map.entrySet());
        expected.sort(Map.Entry.comparingByValue());
        expected = expected.subList(0,limit);
        assertThat(controller.recommendCheapestPricePlans(SMART_METER_ID, limit).getBody()).isEqualTo(expected);
    }

    @Test
    public void givenLimitOverAvailableTotalPricePlansShouldReturnAllPricePlansAscByPrice(){

        int limit = 10;
        Map<String,BigDecimal> map = new HashMap<>();
        map.put(PRICE_PLAN_1_ID,BigDecimal.ONE);
        map.put(PRICE_PLAN_2_ID,BigDecimal.TEN);
        map.put(PRICE_PLAN_3_ID,BigDecimal.valueOf(8L));
        Mockito.when(pricePlanService.getConsumptionCostOfElectricityReadingsForEachPricePlan(SMART_METER_ID)).thenReturn(Optional.of(map));

        List<Map.Entry<String,BigDecimal>> expected  = new ArrayList<>(map.entrySet());
        expected.sort(Map.Entry.comparingByValue());
        expected = expected.subList(0, Math.min(limit, expected.size()));
        assertThat(controller.recommendCheapestPricePlans(SMART_METER_ID, limit).getBody()).isEqualTo(expected);
    }

    @Test
    public void givenNotExistSmartMeterIdShouldReturnNotFound() {
        Mockito.when(accountService.getPricePlanIdForSmartMeterId(SMART_METER_ID)).thenReturn(null);
        assertThat(controller.calculatedCostForEachPricePlan("not-found").getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
