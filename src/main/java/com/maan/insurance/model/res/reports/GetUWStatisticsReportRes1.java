package com.maan.insurance.model.res.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetUWStatisticsReportRes1 {

	@JsonProperty("Item")
	private String item;
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
	@JsonProperty("LayerNo")
	private String layerNo;
	@JsonProperty("UwYear")
	private String uwYear;
	@JsonProperty("DepartmentName")
	private String departmentName;
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
	@JsonProperty("RskInsuredName")
	private String rskInsuredName;
	@JsonProperty("ClaimNo")
	private String claimNo;
	@JsonProperty("AccountPeriodQtr")
	private String accountPeriodQtr;
	@JsonProperty("StatementDate")
	private String statementDate;
	@JsonProperty("Currency")
	private String currency;
	@JsonProperty("AmountOc")
	private String amountOc;
	@JsonProperty("AmountDc")
	private String amountDc;
	@JsonProperty("M1")
	private String m1;
	@JsonProperty("M2")
	private String m2;
	@JsonProperty("M2")
	private String m3;
	@JsonProperty("M4")
	private String m4;
	@JsonProperty("M5")
	private String m5;
	@JsonProperty("M6")
	private String m6;
	@JsonProperty("M7")
	private String m7;
	@JsonProperty("M8")
	private String m8;
	@JsonProperty("M9")
	private String m9;
	@JsonProperty("M10")
	private String m10;
	@JsonProperty("M11")
	private String m11;
	@JsonProperty("M12")
	private String m12;
	@JsonProperty("M13")
	private String m13;
	@JsonProperty("M14")
	private String m14;
	@JsonProperty("M15")
	private String m15;
	@JsonProperty("M16")
	private String m16;
	@JsonProperty("M17")
	private String m17;
	@JsonProperty("M18")
	private String m18;
	@JsonProperty("M19")
	private String m19;
	@JsonProperty("M20")
	private String m20;
	@JsonProperty("Cnt")
	private String cnt;
	@JsonProperty("AccountingPeriodDate")
	private String accountingPeriodDate;

}
