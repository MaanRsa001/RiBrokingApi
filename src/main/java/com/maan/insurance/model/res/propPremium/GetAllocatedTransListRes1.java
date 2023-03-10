package com.maan.insurance.model.res.propPremium;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.req.propPremium.GetCashLossCreditReq;

import lombok.Data;

@Data
public class GetAllocatedTransListRes1 {
	@JsonProperty("CreditTrxnNO")
	private String creditTrxnNO;
	@JsonProperty("ContractNo")
	private String contractNo;
}
