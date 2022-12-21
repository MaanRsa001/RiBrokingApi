package com.maan.insurance.model.req.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetInstallmentDueReportReq {
	
	@JsonProperty("ProductId")
	private String productId;
	@JsonProperty("CedingId")
	private String cedingId;
	@JsonProperty("EndDate")
	private String endDate;
	@JsonProperty("BrokerId")
	private String brokerId;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("AllocationYN")
	private String allocationYN;
}
