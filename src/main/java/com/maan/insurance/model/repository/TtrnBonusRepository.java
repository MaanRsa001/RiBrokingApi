package com.maan.insurance.model.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.maan.insurance.model.entity.TtrnBonus;
import com.maan.insurance.model.entity.TtrnCrestazoneDetails;

public interface TtrnBonusRepository extends JpaRepository<TtrnBonus,BigDecimal> , JpaSpecificationExecutor<TtrnBonus> {


	void deleteByProposalNoAndBranchAndTypeAndLayerNo(BigDecimal bigDecimal, String branchCode, String type,
			String layerNo);

	void deleteByProposalNoAndEndorsementNoAndBranchAndTypeAndLayerNo(BigDecimal bigDecimal, BigDecimal bigDecimal2,
			String branchCode, String type, String layerNo);

	List<TtrnBonus> findByReferenceNo(String referenceNo);

}
