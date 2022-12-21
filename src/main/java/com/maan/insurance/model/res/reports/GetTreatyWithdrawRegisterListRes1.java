package com.maan.insurance.model.res.reports;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.req.reports.GetRetroRegisterListReq;

import lombok.Data;

@Data
public class GetTreatyWithdrawRegisterListRes1 {

	@JsonProperty("ProposalNo")
	private String proposalNo;

	@JsonProperty("ContractNo")
	private String contractNo;

	@JsonProperty("MainClass")
	private String mainClass;

	@JsonProperty("Class")
	private String clas;
	
	@JsonProperty("SubClass")
	private String subClass;

	@JsonProperty("UwYear")
	private String uwYear;

	@JsonProperty("CedingCompany")
	private String cedingCompany;

	@JsonProperty("BrokerName")
	private String brokerName;

	@JsonProperty("TreatyName")
	private String treatyName;

	@JsonProperty("RunoffClean")
	private String runoffClean;

	@JsonProperty("PofolioWithdrawYrs")
	private String pofolioWithdrawYrs;

	@JsonProperty("InceptionDate")
	private String inceptionDate;

	@JsonProperty("ExpiryDate")
	private String expiryDate;

	@JsonProperty("AcceptanceDate")
	private String acceptanceDate;

	@JsonProperty("OriginalCurrency")
	private String originalCurrency;

	@JsonProperty("ExchangeRate")
	private String exchangeRate;

	@JsonProperty("AccountingPeriod")
	private String accountingPeriod;

	@JsonProperty("StatementDueDays")
	private String statementDueDays;

	@JsonProperty("TreatyLimitOc")
	private String treatyLimitOc;

	@JsonProperty("EpiOfferOc")
	private String epiOfferOc;

	@JsonProperty("OurAssessment")
	private String ourAssessment;

	@JsonProperty("EpiEstimateOc")
	private String epiEstimateOc;

	@JsonProperty("TreatyLimitDc")
	private String treatyLimitDc;

	@JsonProperty("EpiOfferDc")
	private String epiOfferDc;

	@JsonProperty("EpiEstimateDc")
	private String epiEstimateDc;

	@JsonProperty("ShareWritten")
	private String shareWritten;

	@JsonProperty("ShareSigned")
	private String shareSigned;

	@JsonProperty("TreatyLimitShareOc")
	private String treatyLimitShareOc;

	@JsonProperty("EpiOfferShareOc")
	private String epiOfferShareOc;

	@JsonProperty("EpiEstimateShareOc")
	private String epiEstimateShareOc;

	@JsonProperty("TreatyLimitShareDc")
	private String treatyLimitShareDc;

	@JsonProperty("EpiOfferShareDc")
	private String epiOfferShareDc;

	@JsonProperty("EpiEstimateShareDc")
	private String epiEstimateShareDc;

	@JsonProperty("PofolioInoutLoss")
	private String pofolioInoutLoss;

	@JsonProperty("PofolioInoutPremium")
	private String pofolioInoutPremium;

	@JsonProperty("PofolioWithdrawDate")
	private String pofolioWithdrawDate;

	@JsonProperty("OverdueDays")
	private String overdueDays;
	
}
