package com.maan.insurance.model.req.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class GetTrailBalanceReportReq {
	@JsonProperty("StartDate")
	private String startDate;
	@JsonProperty("BranchCode")
	private String branchCode;
	
	
	
}
