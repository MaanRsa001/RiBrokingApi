package com.maan.insurance.model.res.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetPptyOSLRClaimReportRes1 {

	@JsonProperty("TransactionNo")
	private String transactionNo;
	@JsonProperty("TransactionDate")
	private String transactionDate;
	@JsonProperty("DocType")
	private String docType;
	@JsonProperty("CedantReference")
	private String cedantReference;
	@JsonProperty("ContractNo")
	private String contractNo;
	@JsonProperty("UwYear")
	private String uwYear;
	@JsonProperty("ContractClass")
	private String contractClass;
	@JsonProperty("PremiumClass")
	private String premiumClass;
	@JsonProperty("RskSpfcid")
	private String rskSpfcid;
	@JsonProperty("InceptionDate")
	private String inceptionDate;
	@JsonProperty("ExpiryDate")
	private String expiryDate;
	@JsonProperty("CompanyCode")
	private String companyCode;
	@JsonProperty("BrokerCode")
	private String brokerCode;
	@JsonProperty("RskTreatyid")
	private String rskTreatyid;
	@JsonProperty("AccountPeriodQtr")
	private String accountPeriodQtr;
	@JsonProperty("StatementDate")
	private String statementDate;
	@JsonProperty("StatementRecdDate")
	private String statementRecdDate;
	@JsonProperty("Currency")
	private String currency;
	@JsonProperty("AmountOc")
	private String amountOc;
	@JsonProperty("AmountBkdDc")
	private String amountBkdDc;
	@JsonProperty("AmountRstDc")
	private String amountRstDc;
	@JsonProperty("SectionName")
	private String sectionName;
	@JsonProperty("AccountingPeriodDate")
	private String accountingPeriodDate;

}
