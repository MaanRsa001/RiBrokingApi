package com.maan.insurance.model.res.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetRenewalDueListRes2 {

	@JsonProperty("RskProposalNumber")
	private String rskProposalNumber;
	@JsonProperty("RskUwyear")
	private String rskUwyear;
	@JsonProperty("RskContractNo")
	private String rskContractNo ;
	@JsonProperty("RskLayerNo")
	private String rskLayerNo;
	@JsonProperty("TmasSpfcName")
	private String tmasSpfcName;
	@JsonProperty("TerritoryDesc")
	private String territoryDesc;
	@JsonProperty("BrokerName")
	private String brokerName;
	@JsonProperty("CompanyName")
	private String companyName ;
	@JsonProperty("RskTreatyid")
	private String rskTreatyid;
	@JsonProperty("IncDate")
	private String incDate;
	@JsonProperty("ExpDate")
	private String expDate;
	@JsonProperty("ShareSigned")
	private String shareSigned ;
	@JsonProperty("RskEpiOsofOc")
	private String rskEpiOsofOc;
	@JsonProperty("BranchCode")
	private String branchCode;
}
