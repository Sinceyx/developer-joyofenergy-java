package uk.tw.energy.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import uk.tw.energy.dao.PricePlanRepo;
import uk.tw.energy.domain.PricePlan;
import uk.tw.energy.po.PeakTimeMultiplierPo;
import uk.tw.energy.po.PricePlanPo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class PricePlanServiceTest {

    @Mock
    private PricePlanRepo pricePlanRepo;

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
    void getConsumptionCostOfElectricityReadingsForEachPricePlan() {

    }
}