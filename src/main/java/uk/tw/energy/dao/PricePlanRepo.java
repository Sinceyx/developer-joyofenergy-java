package uk.tw.energy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.tw.energy.po.PricePlanPo;

import javax.persistence.Id;

public interface PricePlanRepo extends JpaRepository<PricePlanPo, Id> {
}
