package uk.tw.energy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.tw.energy.po.AccountPo;

import javax.persistence.Id;
public interface AccountRepo extends JpaRepository<AccountPo,Id> {
    AccountPo findBySmartMeterId(String smartMeterId);
}
