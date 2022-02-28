package uk.tw.energy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uk.tw.energy.po.PricePlanPo;

import javax.persistence.Id;

public interface PricePlanRepo extends JpaRepository<PricePlanPo, Id> {

    @Query("SELECT new uk.tw.energy.po.PricePlanPo(pp.pricePlanId,pp.energySupplier,pp.planName,pp.unitRate) FROM PricePlanPo pp where pricePlanId = (SELECT pricePlanId FROM AccountPo where smartMeterId = :smartMeterId )")
    PricePlanPo findOneBySmartMeterId(@Param("smartMeterId") String smartMeterId);
}
