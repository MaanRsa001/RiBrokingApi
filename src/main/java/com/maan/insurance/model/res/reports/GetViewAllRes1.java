package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;

import lombok.Data;

@Data
public class GetViewAllRes1 {
	@JsonProperty("AccountDate")
	private String accountDate;
	@JsonProperty("Spc")
	private String spc;
	@JsonProperty("UwYear")
	private String uwYear;
	@JsonProperty("Currencyname")
	private String currencyname;
	@JsonProperty("AccountPeriod")
	private String accountPeriod;
	@JsonProperty("SNo")
	private String sNo;
	@JsonProperty("GrossPremiumOC")
	private String grossPremiumOC;
	@JsonProperty("GrossPremiumDC")
	private String grossPremiumDC;
	@JsonProperty("GrossAcqCostOC")
	private String grossAcqCostOC;
	@JsonProperty("GrossAcqCostDC")
	private String grossAcqCostDC;
	@JsonProperty("PremiumDepositRetainedDC")
	private String premiumDepositRetainedDC;
	@JsonProperty("PremiumDepositRetainedOC")
	private String premiumDepositRetainedOC;
	@JsonProperty("PremiumDepositReleasedOC")
	private String premiumDepositReleasedOC;
	@JsonProperty("PremiumDepositReleasedDC")
	private String premiumDepositReleasedDC;
	@JsonProperty("ClaimDepositRetainedOC")
	private String claimDepositRetainedOC;
	@JsonProperty("ClaimDepositRetainedDC")
	private String claimDepositRetainedDC;
	@JsonProperty("ClaimDepositReleasedOC")
	private String claimDepositReleasedOC;
	@JsonProperty("ClaimDepositReleasedDC")
	private String claimDepositReleasedDC;
	@JsonProperty("InterestOnDepositOC")
	private String interestOnDepositOC;
	@JsonProperty("InterestOnDepositDC")
	private String interestOnDepositDC;
	@JsonProperty("GrossClaimPaidOC")
	private String grossClaimPaidOC;
	@JsonProperty("GrossClaimPaidDC")
	private String grossClaimPaidDC;
	@JsonProperty("GpOC")
	private String gpOC;
	@JsonProperty("GAOC")
	private String gAOC;
	@JsonProperty("PdRetOC")
	private String pdRetOC;
	@JsonProperty("PdRelOC")
	private String pdRelOC;
	@JsonProperty("CdRelOC")
	private String cdRelOC;
	@JsonProperty("CdRetOC")
	private String cdRetOC;
	@JsonProperty("IntOC")
	private String intOC;
	@JsonProperty("GcpOC")
	private String gcpOC;
	@JsonProperty("BrokerLedCtlOC")
	private String brokerLedCtlOC;
	@JsonProperty("BrokerLedCtlDC")
	private String brokerLedCtlDC;
	@JsonProperty("BlOC")
	private String blOC;
	@JsonProperty("TotalCROC")
	private String totalCROC;
	@JsonProperty("TotalDROC")
	private String totalDROC;
	@JsonProperty("TotalCRDC")
	private String totalCRDC;
	@JsonProperty("TotalDRDC")
	private String totalDRDC;
}
