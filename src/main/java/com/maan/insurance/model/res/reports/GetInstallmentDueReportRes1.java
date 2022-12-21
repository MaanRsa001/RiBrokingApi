package com.maan.insurance.model.res.reports;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.req.reports.GetJVReportsReq;

import lombok.Data;

@Data
public class GetInstallmentDueReportRes1 {

	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("RskProductid")
	private String rskProductid;
	@JsonProperty("ProposalNo")
	private String proposalNo;
	@JsonProperty("ContractNo")
	private String contractNo;
	@JsonProperty("Layer")
	private String layer;
	@JsonProperty("UwYear")
	private String uwYear;
	@JsonProperty("DepartmentName")
	private String departmentName;
	@JsonProperty("CedingCompany")
	private String cedingCompany;
	@JsonProperty("Broker")
	private String broker;
	@JsonProperty("Currency")
	private String currency;
	@JsonProperty("ExchangeRate")
	private String exchangeRate;
	@JsonProperty("InsuredName")
	private String insuredName;
	@JsonProperty("TreatyName")
	private String treatyName;
	@JsonProperty("InceptionDate")
	private String inceptionDate;
	@JsonProperty("ExpiryDate")
	private String expiryDate;
	@JsonProperty("ShareWrittenPercent")
	private String shareWrittenPercent;
	@JsonProperty("ShareSignedPercent")
	private String shareSignedPercent;
	@JsonProperty("InstallmentNumber")
	private String installmentNumber;
	@JsonProperty("InstallmentDate")
	private String installmentDate;
	@JsonProperty("MndPremiumOc")
	private String mndPremiumOc;
	@JsonProperty("MndPremiumDc")
	private String mndPremiumDc;
	@JsonProperty("PpwDate")
	private String ppwDate;
	@JsonProperty("OverdueDays")
	private String overdueDays;
}
