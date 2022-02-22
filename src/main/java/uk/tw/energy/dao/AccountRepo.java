package uk.tw.energy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.tw.energy.domain.Account;

import javax.persistence.Id;
public interface AccountRepo extends JpaRepository<Account,Id> {
    Account findBySmartMeterId(String smartMeterId);
}
