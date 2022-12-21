package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;

import lombok.Data;

@Data
public class GetClaimPaidRegisterListRes1 {
	 @JsonProperty("ClaimNo")
		private String claimNo;
	    @JsonProperty("ClaimPaymentNo")
		private String claimPaymentNo;
	    @JsonProperty("Claimpaiddate")
		private String claimpaiddate;
	    @JsonProperty("ContractNo")
		private String contractNo;
	    @JsonProperty("UwYear")
		private String uwYear;
	    @JsonProperty("LayerNo")
		private String layerNo;
	    @JsonProperty("CedingCompany")
		private String cedingCompany;
	    @JsonProperty("BrokerId")
		private String brokerId;
	    @JsonProperty("ContractClass")
		private String contractClass;
	    @JsonProperty("ClaimClass")
		private String claimClass;
	    @JsonProperty("ClaimSubclass")
		private String claimSubclass;
	    @JsonProperty("InsuredName")
		private String insuredName;
	    @JsonProperty("CedentClaimNo")
		private String cedentClaimNo;
	    @JsonProperty("TreatyName")
		private String treatyName;
	    @JsonProperty("ReserveId")
		private String reserveId;
	    @JsonProperty("ReserveDate")
		private String reserveDate;
	    @JsonProperty("DateOfLoss")
		private String dateOfLoss;
	    @JsonProperty("LossDetails")
		private String lossDetails;
	    @JsonProperty("CauseOfLoss")
		private String causeOfLoss;
	    @JsonProperty("Location")
		private String location;
	    @JsonProperty("RiRecovery")
		private String riRecovery;
	    @JsonProperty("RiskCode")
		private String riskCode;
	    @JsonProperty("AccumulationCode")
		private String accumulationCode;
	    @JsonProperty("EventCode")
		private String eventCode;
	    @JsonProperty("Currency")
		private String currency;
	    @JsonProperty("ExchangeRate")
		private String exchangeRate;
	    @JsonProperty("PaymentRequestNo")
		private String paymentRequestNo;
	    @JsonProperty("PaidClaimOsOc")
		private String paidClaimOsOc;
	    @JsonProperty("SafOsOc")
		private String safOsOc;
	    @JsonProperty("OthFeeOsOc")
		private String othFeeOsOc;
	    @JsonProperty("TotClaimAmountOc")
		private String totClaimAmountOc;
	    @JsonProperty("ReinstatementType")
		private String reinstatementType;
	    @JsonProperty("ReinspremiumOurshareOc")
		private String reinspremiumOurshareOc;
	    @JsonProperty("PaidAmountOc")
		private String paidAmountOc;
	    @JsonProperty("PaidClaimOsDc")
		private String paidClaimOsDc;
	    @JsonProperty("SafOsDc")
		private String safOsDc;
	    @JsonProperty("OthFeeOsDc")
		private String othFeeOsDc;
	    @JsonProperty("TotClaimAmountDc")
		private String totClaimAmountDc;
	    @JsonProperty("ReinspremiumOurshareDc")
		private String reinspremiumOurshareDc;
	    @JsonProperty("PaidAmountDc")
		private String paidAmountDc;
	    @JsonProperty("Allocationstatus")
		private String allocationstatus;
	    @JsonProperty("AllocatedTillDateOc")
		private String allocatedTillDateOc;
	    @JsonProperty("AllocatedTillDateDc")
		private String allocatedTillDateDc;
	    @JsonProperty("ClaimNoteRecomm")
		private String claimNoteRecomm;
	    @JsonProperty("PaymentReference")
		private String paymentReference;
	    @JsonProperty("AdviceTreasury")
		private String adviceTreasury;
	    @JsonProperty("Remarks")
		private String remarks;
	    @JsonProperty("LoginId")
		private String loginId;
}
