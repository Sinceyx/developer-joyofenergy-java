package uk.tw.energy.service;

import org.springframework.stereotype.Service;
import uk.tw.energy.dao.AccountRepo;


@Service
public class AccountService {


    private final AccountRepo accountRepo;

    public AccountService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public String getPricePlanIdForSmartMeterId(String smartMeterId) {
        return accountRepo.findBySmartMeterId(smartMeterId).getPricePlanId();
    }
}
