package com.maan.insurance.model.res.propPremium;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;
import com.maan.insurance.model.res.proportionality.CommonSaveRes;

import lombok.Data;

@Data
public class ViewPremiumDetailsRIReq {
//	@JsonProperty("AmendId")
//	private String amendId;
	@JsonProperty("TransactionNo")
	private String transactionNo;
//	@JsonProperty("BranchCode")
//	private String branchCode;
//	
//	@JsonProperty("CedingId")
//	private String cedingId;
//
//	@JsonProperty("BrokerId")
//	private String brokerId;


}
