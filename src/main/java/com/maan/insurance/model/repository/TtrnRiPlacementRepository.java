package com.maan.insurance.model.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.maan.insurance.model.entity.TtrnRiPlacement;
import com.maan.insurance.model.entity.TtrnRiPlacementId;

public interface TtrnRiPlacementRepository  extends JpaRepository<TtrnRiPlacement,TtrnRiPlacementId > , JpaSpecificationExecutor<TtrnRiPlacement> {

	int countByProposalNo(BigDecimal bigDecimal);

}
