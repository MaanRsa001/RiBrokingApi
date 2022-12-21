package com.maan.insurance.model.req.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class CallProBookedPremiumReq {
	@JsonProperty("ReportDate")
	private String reportDate;
	@JsonProperty("Type")
	private String type;
	@JsonProperty("MovementType")
	private String movementType;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("Product")
	private String product;
}
