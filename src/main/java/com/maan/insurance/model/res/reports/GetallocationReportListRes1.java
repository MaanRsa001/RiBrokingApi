package com.maan.insurance.model.res.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetallocationReportListRes1 {
	
	@JsonProperty("AllocationNo")
	private String allocationNo;
    @JsonProperty("AllocationDate")
	private String allocationDate;
    @JsonProperty("StlType")
	private String stlType;
    @JsonProperty("StlNo")
	private String stlNo;
    @JsonProperty("StlDate")
	private String stlDate;
    @JsonProperty("ProcessType")
	private String processType;
    @JsonProperty("Status")
	private String status;
    @JsonProperty("Currency")
	private String currency;
    @JsonProperty("Type")
	private String type;
    @JsonProperty("BookTransNo")
	private String bookTransNo;
    @JsonProperty("ProposalNo")
	private String proposalNo;
    @JsonProperty("ContractNo")
	private String contractNo;
    @JsonProperty("ProductName")
	private String productName;
    @JsonProperty("CedingCompany")
	private String cedingCompany;
    @JsonProperty("Broker")
	private String broker;
    @JsonProperty("AllocatedAmt")
	private String allocatedAmt;
    @JsonProperty("SettledRate")
	private String settledRate;
    @JsonProperty("AllocatedAmtDc")
	private String allocatedAmtDc;
    @JsonProperty("BkdExchangeRate")
	private String bkdExchangeRate;
    @JsonProperty("BookedAmtDc")
	private String bookedAmtDc;
    @JsonProperty("RealisedExDiff")
	private String realisedExDiff;

	
	
	
}
