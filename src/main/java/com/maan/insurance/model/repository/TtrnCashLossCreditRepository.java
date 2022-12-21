package com.maan.insurance.model.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.maan.insurance.model.entity.TtrnCashLossCredit;
import com.maan.insurance.model.entity.TtrnClaimDetails;
import com.maan.insurance.model.entity.TtrnClaimDetailsId;

public interface TtrnCashLossCreditRepository extends JpaRepository<TtrnCashLossCredit,BigDecimal > , JpaSpecificationExecutor<TtrnCashLossCredit> {

	TtrnCashLossCredit findByContractNoAndTempRequestNo(String contractNo, BigDecimal bigDecimal);

}
