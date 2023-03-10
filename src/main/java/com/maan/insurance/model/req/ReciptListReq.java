package com.maan.insurance.model.req;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ReciptListReq {
	
	@JsonProperty("SearchType")
	private String searchType;
	
	@JsonProperty("PaymentNoSearch")
	private String paymentNoSearch;
	
	@JsonProperty("BrokerNameSearch")
	private String brokerNameSearch;
	
	@JsonProperty("CompanyNameSearch")
	private String companyNameSearch;
	
	@JsonProperty("RemarksSearch")
	private String remarksSearch;
	
	@JsonProperty("FullSearch")
	private String fullSearch;
	
	@JsonProperty("OpStartDate")
	private String opStartDate;
	
	@JsonProperty("OpEndDate")
	private String opEndDate;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("TransType")
	private String transType;

}
