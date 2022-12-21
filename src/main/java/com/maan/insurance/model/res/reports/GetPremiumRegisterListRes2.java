package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;

import lombok.Data;

@Data
public class GetPremiumRegisterListRes2 {

	@JsonProperty("ContractNo")
	private String contractNo;
    @JsonProperty("TransactionNo")
	private String transactionNo;
    @JsonProperty("TransactionDate")
	private String transactionDate;
    @JsonProperty("TmasPfcName")
	private String tmasPfcName;
    @JsonProperty("RskTreatyid")
	private String rskTreatyid;
    @JsonProperty("RskDeptid")
	private String rskDeptid;
    @JsonProperty("TmasSpfcName")
	private String tmasSpfcName;
    @JsonProperty("UwYear")
	private String uwYear;
    @JsonProperty("CompanyName")
	private String companyName;
    @JsonProperty("Broker")
	private String broker;
    @JsonProperty("InceptionDate")
	private String inceptionDate;
    @JsonProperty("ExpiryDate")
	private String expiryDate  ;
    @JsonProperty("ShortName")
	private String shortName;
    @JsonProperty("CedantReference")
	private String cedantReference;
    @JsonProperty("PremiumClass")
	private String premiumClass;
    @JsonProperty("PremiumSubclass")
	private String premiumSubclass;
    @JsonProperty("RiCession")
	private String riCession;
    @JsonProperty("StatementDate")
	private String statementDate;
    @JsonProperty("StatmentRecdDate")
	private String statmentRecdDate;
    @JsonProperty("PremiumQuotashareOc")
	private String premiumQuotashareOc;
    @JsonProperty("PremiumSurplusOc")
	private String premiumSurplusOc;
    @JsonProperty("PremiumPortfolioinOc")
	private String premiumPortfolioinOc;
    @JsonProperty("ClaimPortfolioinOc")
	private String claimPortfolioinOc;
    @JsonProperty("PremiumReserveRealsedOc")
	private String premiumReserveRealsedOc;
    @JsonProperty("LossReserveReleasedOc")
	private String lossReserveReleasedOc;
    @JsonProperty("InterestOc")
	private String interestOc;
    @JsonProperty("CashLossCreditOc")
	private String cashLossCreditOc;
    @JsonProperty("TdsOc")
	private String tdsOc;
    @JsonProperty("StOc")
	private String stOc;
    @JsonProperty("LpcOc")
	private String lpcOc;
    @JsonProperty("TotalCrOc")
	private String totalCrOc;
    @JsonProperty("CommissionQuotashareOc")
	private String commissionQuotashareOc;
    @JsonProperty("CommissionSurplusOc")
	private String commissionSurplusOc;
    @JsonProperty("BrokerageAmtOc")
	private String brokerageAmtOc;
    @JsonProperty("TaxAmtOc")
	private String taxAmtOc;
    @JsonProperty("OtherCostOc")
	private String otherCostOc;
    @JsonProperty("OverriderAmtOc")
	private String overriderAmtOc;
    @JsonProperty("XlCostOc")
	private String xlCostOc;
    @JsonProperty("PremiumReserveRetainedOc")
	private String premiumReserveRetainedOc;
    @JsonProperty("LossReserveretainedOc")
	private String lossReserveretainedOc;
    @JsonProperty("PremiumPortfoliooutOc")
	private String premiumPortfoliooutOc;
    @JsonProperty("ClaimPortfolioOutOc")
	private String claimPortfolioOutOc;
    @JsonProperty("WithHoldingTaxOc")
	private String withHoldingTaxOc;
    @JsonProperty("ScCommOc")
	private String scCommOc;
    @JsonProperty("ProfitCommissionOc")
	private String profitCommissionOc;
    @JsonProperty("ClaimsPaidOc")
	private String claimsPaidOc;
    @JsonProperty("CashLosspaidOc")
	private String cashLosspaidOc;
    @JsonProperty("TotalDrOc")
	private String totalDrOc;
    @JsonProperty("NetdueOc")
	private String netdueOc;
    @JsonProperty("AllocatedTillDateOc")
	private String allocatedTillDateOc;
    @JsonProperty("AllcationPendingOc")
	private String allcationPendingOc;
    @JsonProperty("ExchangeRate")
	private String exchangeRate;
    @JsonProperty("PremiumQuotashareDc")
	private String premiumQuotashareDc;
    @JsonProperty("PremiumSurplusDc")
	private String premiumSurplusDc;
    @JsonProperty("PremiumPortfolioinDc")
	private String premiumPortfolioinDc;
    @JsonProperty("ClaimPortfolioinDc")
	private String claimPortfolioinDc;
    @JsonProperty("PremiumReserveRealsedDc")
	private String premiumReserveRealsedDc;
    @JsonProperty("LossReserveReleasedDc")
	private String lossReserveReleasedDc;
    @JsonProperty("InterestDc")
	private String interestDc;
    @JsonProperty("CashLossCreditDc")
	private String cashLossCreditDc;
    @JsonProperty("TdsDc")
	private String tdsDc;
    @JsonProperty("StDc")
	private String stDc;
    @JsonProperty("LpcDc")
	private String lpcDc;
    @JsonProperty("TotalCrDc")
	private String totalCrDc;
    @JsonProperty("CommissionQuotashareDc")
	private String commissionQuotashareDc;
    @JsonProperty("CommissionSurplusDc")
	private String commissionSurplusDc;
    @JsonProperty("BrokerageAmtDc")
	private String brokerageAmtDc;
    @JsonProperty("TaxAmtDc")
	private String taxAmtDc;
    @JsonProperty("OtherCostDc")
	private String otherCostDc;
    @JsonProperty("OverriderAmtDc")
	private String overriderAmtDc;
    @JsonProperty("XlCostDc")
	private String xlCostDc;
    @JsonProperty("PremiumReserveRetainedDc")
	private String premiumReserveRetainedDc;
    @JsonProperty("LossReserveretainedDc")
	private String lossReserveretainedDc;
    @JsonProperty("PremiumPortfoliooutDc")
	private String premiumPortfoliooutDc;
    @JsonProperty("ClaimPortfolioOutDc")
	private String claimPortfolioOutDc;
    @JsonProperty("WithHoldingTaxDc")
	private String withHoldingTaxDc;
    @JsonProperty("ScCommDc")
	private String scCommDc;
    @JsonProperty("ProfitCommissionDc")
	private String profitCommissionDc;
    @JsonProperty("ClaimsPaidDc")
	private String claimsPaidDc;
    @JsonProperty("CashLosspaidDc")
	private String cashLosspaidDc;
    @JsonProperty("TotalDrDc")
	private String totalDrDc;
    @JsonProperty("NetdueDc")
	private String netdueDc;
    @JsonProperty("AllocatedTillDateDc")
	private String allocatedTillDateDc;
    @JsonProperty("AllcationPendingDc")
	private String allcationPendingDc;
    @JsonProperty("Remarks")
	private String remarks;
    @JsonProperty("SettlementStatus")
	private String settlementStatus;
    @JsonProperty("LoginId")
	private String loginId;
    @JsonProperty("BranchCode")
	private String branchCode;
    @JsonProperty("InstallmentDate")
	private String installmentDate;
    @JsonProperty("Osbyn")
	private String osbyn;
    @JsonProperty("OsclaimLossupdateOc")
	private String osclaimLossupdateOc;
    @JsonProperty("OsclaimLossupdateDc")
	private String osclaimLossupdateDc;
    @JsonProperty("EndorsementDate")
	private String endorsementDate;
}
