package com.maan.insurance.model.res.reports;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.req.reports.GetInstallmentDueReportReq;

import lombok.Data;

@Data
public class GetPortfolioOutPendingReportRes1 {
	
	@JsonProperty("ProposalNo")
	private String proposalNo;
	@JsonProperty("ContractNo")
	private String contractNo;
	@JsonProperty("DepartmentName")
	private String departmentName;
	@JsonProperty("CompanyCode")
	private String companyCode;
	@JsonProperty("BrokerCode")
	private String brokerCode;
	@JsonProperty("UwYear")
	private String uwYear;
	@JsonProperty("InceptionDate")
	private String inceptionDate;
	@JsonProperty("ExpiryDate")
	private String expiryDate;
	@JsonProperty("AccountDate")
	private String accountDate;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("RskRunOffYear")
	private String rskRunOffYear;
	@JsonProperty("TrfDate")
	private String trfDate;
	@JsonProperty("CleancutRunoff")
	private String cleancutRunoff;
	@JsonProperty("TreatyType")
	private String treatyType;
	@JsonProperty("RskTreatyid")
	private String rskTreatyid;
	@JsonProperty("RskRiskCovered")
	private String rskRiskCovered;
	@JsonProperty("StatementDueDays")
	private String statementDueDays;
}
