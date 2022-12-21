package com.maan.insurance.model.res.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetRenewalDueListRes1 {
	@JsonProperty("ProposalNo")
	private String proposalNo;
	@JsonProperty("RskUwyear")
	private String rskUwyear;
	@JsonProperty("ContractNo")
	private String contractNo;
	@JsonProperty("TmasSpfcName")
	private String tmasSpfcName;
	@JsonProperty("TerritoryDesc")
	private String territoryDesc;
	@JsonProperty("BrokerName")
	private String brokerName;
	@JsonProperty("CompanyName")
	private String companyName;
	@JsonProperty("RskInsuredName")
	private String rskInsuredName;
	@JsonProperty("IncDate")
	private String incDate;
	@JsonProperty("ExpDate")
	private String expDate;
	@JsonProperty("ShareSigned")
	private String shareSigned;
}
