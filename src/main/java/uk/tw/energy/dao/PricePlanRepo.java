package uk.tw.energy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uk.tw.energy.po.PricePlanPo;

import javax.persistence.Id;

public interface PricePlanRepo extends JpaRepository<PricePlanPo, Id> {
    @Query(value = "select price_plan_id,plan_name,energy_supplier,unit_rate from price_plan where price_plan_id = (select price_plan_id from account act where act.smart_meter_id = ?1 )",nativeQuery = true)
    PricePlanPo findOneBySmartMeterId(String smartMeterId);
}
