package com.maan.insurance.model.res.proportionality;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.res.reports.GetClaimRegisterListRes1;

import lombok.Data;

@Data
public class GetRetroInwardMappingReportRes1 {

	@JsonProperty("ProposalNo")
	private String proposalNo;
	@JsonProperty("ContractNo")
	private String contractNo;
	@JsonProperty("LayerNo")
	private String layerNo;
	@JsonProperty("ProductName")
	private String productName;
	@JsonProperty("DepartmentName")
	private String departmentName;
	@JsonProperty("CompanyName")
	private String companyName;
	@JsonProperty("Broker")
	private String broker;
	@JsonProperty("UwYear")
	private String uwYear;
	@JsonProperty("InceptionDate")
	private String inceptionDate;
	@JsonProperty("ExpiryDate")
	private String expiryDate;
	@JsonProperty("AccountDate")
	private String accountDate;
	@JsonProperty("EndorsementDate")
	private String endorsementDate;
	@JsonProperty("RetroContractNo")
	private String retroContractNo;
	@JsonProperty("RetroContractPercentage")
	private String retroContractPercentage;
	@JsonProperty("Sno")
	private String sno;
	@JsonProperty("RetroCessionaire")
	private String retroCessionaire;
	@JsonProperty("RetroBroker")
	private String retroBroker;
	@JsonProperty("RetCes")
	private String retCes;
	@JsonProperty("RetroCessionairePercentage")
	private String retroCessionairePercentage;
	@JsonProperty("RetrocessionPercentage")
	private String retrocessionPercentage;
	@JsonProperty("RskRetroType")
	private String rskRetroType;
	@JsonProperty("InwardCurrency")
	private String inwardCurrency;
	@JsonProperty("OutwardCurrency")
	private String outwardCurrency;
	@JsonProperty("ShareSigned")
	private String shareSigned;

}
