package com.maan.insurance.model.req.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.res.reports.GetClaimRegisterListRes;
import com.maan.insurance.model.res.reports.GetClaimRegisterListRes1;

import lombok.Data;

@Data
public class GetMoveMentPageReq {
	@JsonProperty("Accper")
	private String accper;
	
	@JsonProperty("AccountDate")
	private String accountDate;
	
	@JsonProperty("BranchCode")
	private String branchCode;
	
	@JsonProperty("MovementType")
	private String movementType;
	
	@JsonProperty("MovId")
	private String movId;
	
	@JsonProperty("Display")
	private String display;
	
	
}
