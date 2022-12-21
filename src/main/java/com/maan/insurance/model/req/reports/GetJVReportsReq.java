package com.maan.insurance.model.req.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetJVReportsReq {
	@JsonProperty("JournalType")
	private String journalType;
	@JsonProperty("StartDate")
	private String startDate;
	@JsonProperty("EndDate")
	private String endDate;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("BranchCode")
	private String branchCode;
	
}
