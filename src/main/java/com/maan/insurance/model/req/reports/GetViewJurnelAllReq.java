package com.maan.insurance.model.req.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetViewJurnelAllReq {
	@JsonProperty("Display")
	private String display;
	@JsonProperty("MovId")
	private String movId;
	@JsonProperty("SNo")
	private String sNo;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("AccountPeriod")
	private String accountPeriod;
	@JsonProperty("AccountDate")
	private String accountDate;
}
