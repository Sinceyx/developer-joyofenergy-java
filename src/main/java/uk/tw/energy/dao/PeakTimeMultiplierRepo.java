package uk.tw.energy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.tw.energy.po.PeakTimeMultiplierPo;

import javax.persistence.Id;

public interface PeakTimeMultiplierRepo extends JpaRepository<PeakTimeMultiplierPo, Id> {
}
