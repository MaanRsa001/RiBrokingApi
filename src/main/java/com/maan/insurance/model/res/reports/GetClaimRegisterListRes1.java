package com.maan.insurance.model.res.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetClaimRegisterListRes1 {

	@JsonProperty("ClaimNo")
	private String claimNo;
	@JsonProperty("SlNo")
	private String slNo;
	@JsonProperty("ContractNo")
	private String contractNo;
	@JsonProperty("InceptionDate")
	private String inceptionDate;
	@JsonProperty("ExpiryDate")
	private String expiryDate;
	@JsonProperty("UwYear")
	private String uwYear;
	@JsonProperty("CompanyName")
	private String companyName;
	@JsonProperty("Broker")
	private String broker;
	@JsonProperty("LayerNo")
	private String layerNo;
	@JsonProperty("StatusOfClaim")
	private String statusOfClaim;
	@JsonProperty("ContractClass")
	private String contractClass;
	@JsonProperty("ClaimClass")
	private String claimClass;
	@JsonProperty("ClaimSubclass")
	private String claimSubclass;
	@JsonProperty("DateOfLoss")
	private String dateOfLoss;
	@JsonProperty("ReportDate")
	private String reportDate;
	@JsonProperty("CreatedDate")
	private String createdDate;
	@JsonProperty("ClaimClosedDate")
	private String claimClosedDate;
	@JsonProperty("ReopenedDate")
	private String reopenedDate;
	@JsonProperty("LossDetails")
	private String lossDetails;
	@JsonProperty("CauseOfLoss")
	private String causeOfLoss;
	@JsonProperty("Location")
	private String location;
	@JsonProperty("CedentClaimNo")
	private String cedentClaimNo;
	@JsonProperty("Currency")
	private String currency;
	@JsonProperty("InsuredName")
	private String insuredName;
	@JsonProperty("GrosslossFguOc")
	private String grosslossFguOc;
	@JsonProperty("LossEstimateOc")
	private String lossEstimateOc;
	@JsonProperty("RecordFeesCreReserve")
	private String recordFeesCreReserve;
	@JsonProperty("Saf100Oc")
	private String saf100Oc;
	@JsonProperty("OthFee100Oc")
	private String othFee100Oc;
	@JsonProperty("RecordIbnr")
	private String recordIbnr;
	@JsonProperty("CIbnr100Oc")
	private String cIbnr100Oc;
	@JsonProperty("ShareSigned")
	private String shareSigned;
	@JsonProperty("LossEstimateOsOc")
	private String lossEstimateOsOc;
	@JsonProperty("SafOsOc")
	private String safOsOc;
	@JsonProperty("OthFeeOsOc")
	private String othFeeOsOc;
	@JsonProperty("CIbnrOsOc")
	private String cIbnrOsOc;
	@JsonProperty("RegExchangeRate")
	private String regExchangeRate;
	@JsonProperty("LossEstimateDc")
	private String lossEstimateDc;
	@JsonProperty("Saf100Dc")
	private String saf100Dc;
	@JsonProperty("OthFee100Dc")
	private String othFee100Dc;
	@JsonProperty("CIbnr100Dc")
	private String cIbnr100Dc;
	@JsonProperty("LossEstimateOsDc")
	private String lossEstimateOsDc;
	@JsonProperty("SafOsDc")
	private String safOsDc;
	@JsonProperty("OthFeeOsDc")
	private String othFeeOsDc;
	@JsonProperty("CIbnrOsDc")
	private String cIbnrOsDc;
	@JsonProperty("Remarks")
	private String remarks;
	@JsonProperty("RiRecovery")
	private String riRecovery;
	@JsonProperty("RiskCode")
	private String riskCode;
	@JsonProperty("AccumulationCode")
	private String accumulationCode;
	@JsonProperty("EventCode")
	private String eventCode;
	@JsonProperty("LoginId")
	private String loginId;
	@JsonProperty("ProductId")
	private String productId;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("PtdClaimOsOc")
	private String ptdClaimOsOc;
	@JsonProperty("PtdSafOsOc")
	private String ptdSafOsOc;
	@JsonProperty("PtdOthOsOc")
	private String ptdOthOsOc;
	@JsonProperty("PtdTotClaimOsOc")
	private String ptdTotClaimOsOc;
	@JsonProperty("ResClaimOc")
	private String resClaimOc;
	@JsonProperty("ResSafOc")
	private String resSafOc;
	@JsonProperty("ResOthOc")
	private String resOthOc;
	@JsonProperty("ResPosOc")
	private String resPosOc;
	@JsonProperty("BkdExchangeRate")
	private String bkdExchangeRate;
	@JsonProperty("PtdClaimOsDc")
	private String ptdClaimOsDc;
	@JsonProperty("PtdSafOsDc")
	private String ptdSafOsDc;
	@JsonProperty("PtdOthOsDc")
	private String ptdOthOsDc;
	@JsonProperty("PtdTotClaimOsDc")
	private String ptdTotClaimOsDc;
	@JsonProperty("ResClaimDc")
	private String resClaimDc;
	@JsonProperty("ResSafDc")
	private String resSafDc;
	@JsonProperty("ResOthDc")
	private String resOthDc;
	@JsonProperty("ResPosDc")
	private String resPosDc;
	@JsonProperty("RestExchangeRate")
	private String restExchangeRate;
	@JsonProperty("RestClaimDc")
	private String restClaimDc;
	@JsonProperty("RestSafDc")
	private String restSafDc;
	@JsonProperty("RestOthDc")
	private String restOthDc;
	@JsonProperty("RestPosDc")
	private String restPosDc;
	@JsonProperty("ProposalNo")
	private String proposalNo;
}
