package com.maan.insurance.model.req.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FinanceStagingListReq {
	@JsonProperty("StartDate")
	private String startDate;
	@JsonProperty("EndDate")
	private String endDate;
	@JsonProperty("Post")
	private String post;
	@JsonProperty("BranchCode")
	private String branchCode;
}
