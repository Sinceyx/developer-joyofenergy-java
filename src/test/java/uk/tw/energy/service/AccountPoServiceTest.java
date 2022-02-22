package uk.tw.energy.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import uk.tw.energy.dao.AccountRepo;
import uk.tw.energy.po.AccountPo;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AccountPoServiceTest {

    private static final String PRICE_PLAN_ID = "price-plan-id";
    private static final String SMART_METER_ID = "smart-meter-id";
    @InjectMocks
    private AccountService accountService;
    @Mock
    private AccountRepo accountRepo;

    @BeforeEach
    public void setUp() {
        Map<String, String> smartMeterToPricePlanAccounts = new HashMap<>();
        smartMeterToPricePlanAccounts.put(SMART_METER_ID, PRICE_PLAN_ID);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenTheSmartMeterIdReturnsThePricePlanId() throws Exception {

        AccountPo fakeAccountPo = new AccountPo();
        fakeAccountPo.setSmartMeterId(SMART_METER_ID);
        fakeAccountPo.setPricePlanId(PRICE_PLAN_ID);
        Mockito.when(accountRepo.findBySmartMeterId(SMART_METER_ID)).thenReturn(fakeAccountPo);
        String expectedResult = PRICE_PLAN_ID;
        String actualResult = accountService.getPricePlanIdForSmartMeterId(SMART_METER_ID);
        assertThat(expectedResult).isEqualTo(actualResult);
    }
}
