package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetMoveMentPageRes1 {
	@JsonProperty("Accper")
	private String accper;
	@JsonProperty("AccountDate")
	private String accountDate;
	@JsonProperty("UwYear")
	private String uwYear;
	@JsonProperty("Spc")
	private String spc;
	@JsonProperty("Currencyname")
	private String currencyname;
	@JsonProperty("PreviousPremium")
	private String previousPremium;
	@JsonProperty("CurrentPremium")
	private String currentPremium;
	@JsonProperty("DiffPremium")
	private String diffPremium;
	@JsonProperty("CurrentGrossPre")
	private String currentGrossPre;
	@JsonProperty("PreGrossPre")
	private String preGrossPre;
	@JsonProperty("DiffGrossPre")
	private String diffGrossPre;
	@JsonProperty("CurrentGrossAcq")
	private String currentGrossAcq;
	@JsonProperty("PreviousGrossAcq")
	private String previousGrossAcq;
	@JsonProperty("DiffGrossAcq")
	private String diffGrossAcq;
	@JsonProperty("CurrentPremiumDepositRet")
	private String currentPremiumDepositRet;
	@JsonProperty("PreviousPremiumDepositRet")
	private String previousPremiumDepositRet;
	@JsonProperty("DiffPremiumDepositRet")
	private String diffPremiumDepositRet;
	@JsonProperty("CurrentPremiumDepositRel")
	private String currentPremiumDepositRel;
	@JsonProperty("PreviousPremiumDepositRel")
	private String previousPremiumDepositRel;
	@JsonProperty("DiffPremiumDepositRel")
	private String diffPremiumDepositRel;
	@JsonProperty("CurrentClaimDepositRet")
	private String currentClaimDepositRet;
	@JsonProperty("PreviousClaimDepositRet")
	private String previousClaimDepositRet;
	@JsonProperty("DiffClaimDepositRet")
	private String diffClaimDepositRet;
	@JsonProperty("CurrentClaimDepositRel")
	private String currentClaimDepositRel;
	@JsonProperty("PreviousClaimDepositRel")
	private String previousClaimDepositRel;
	@JsonProperty("DiffClaimDepositRel")
	private String diffClaimDepositRel;
	@JsonProperty("CurrentInterestDeposit")
	private String currentInterestDeposit;
	@JsonProperty("PreviousInterestDeposit")
	private String previousInterestDeposit;
	@JsonProperty("DiffInterestDeposit")
	private String diffInterestDeposit;
	@JsonProperty("CurrentGrossClaimPaid")
	private String currentGrossClaimPaid;
	@JsonProperty("PreviousGrossClaimPaid")
	private String previousGrossClaimPaid;
	@JsonProperty("DiffGrossClaimPaid")
	private String diffGrossClaimPaid;
	@JsonProperty("SNo")
	private String sNo;
	@JsonProperty("SumofPrm")
	private String sumofPrm;
	@JsonProperty("SumofPrmDC")
	private String sumofPrmDC;
}
