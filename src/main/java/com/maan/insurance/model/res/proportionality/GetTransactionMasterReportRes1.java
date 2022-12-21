package com.maan.insurance.model.res.proportionality;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetTransactionMasterReportRes1 {

	@JsonProperty("TransactionNo")
	private String  transactionNo;
	@JsonProperty("TransactionDate")
	private String  transactionDate;
	@JsonProperty("AmendmentDate")
	private String  amendmentDate;
	@JsonProperty("TransactionMonth")
	private String  transactionMonth;
	@JsonProperty("TransactionYear")
	private String  transactionYear;
	@JsonProperty("DocType")
	private String  docType;
	@JsonProperty("CedantReference")
	private String  cedantReference;
	@JsonProperty("ContractNo")
	private String  contractNo;
	@JsonProperty("UwYear")
	private String  uwYear;
	@JsonProperty("RskSpfcid")
	private String  rskSpfcid;
	@JsonProperty("InceptionDate")
	private String  inceptionDate;
	@JsonProperty("ExpiryDate")
	private String  expiryDate;
	@JsonProperty("CompanyCode")
	private String  companyCode;
	@JsonProperty("BrokerCode")
	private String  brokerCode;
	@JsonProperty("RskInsuredName")
	private String  rskInsuredName;
	@JsonProperty("LossDate")
	private String  lossDate;
	@JsonProperty("ClaimNo")
	private String  claimNo;
	@JsonProperty("AccountPeriodQtr")
	private String  accountPeriodQtr;
	@JsonProperty("Currency")
	private String currency;
	@JsonProperty("PremiumQuotashareOc")
	private String  premiumQuotashareOc;
	@JsonProperty("PremiumSurplusOc")
	private String  premiumSurplusOc;
	@JsonProperty("PremiumTotal")
	private String  premiumTotal;
	@JsonProperty("XlCostOc")
	private String  xlCostOc;
	@JsonProperty("PremiumPortfolioinOc")
	private String  premiumPortfolioinOc;
	@JsonProperty("PremiumPortfoliooutOc")
	private String  premiumPortfoliooutOc;
	@JsonProperty("NetPremium")
	private String  netPremium;
	@JsonProperty("CommissionQuotashareOc")
	private String  commissionQuotashareOc;
	@JsonProperty("CommissionSurplusOc")
	private String  commissionSurplusOc;
	@JsonProperty("CommissionTotal")
	private String  commissionTotal;
	@JsonProperty("BrokerageAmtOc")
	private String  brokerageAmtOc;
	@JsonProperty("TaxAmtOc")
	private String  taxAmtOc;
	@JsonProperty("OverriderAmtOc")
	private String  overriderAmtOc;
	@JsonProperty("OtherCostOc")
	private String  otherCostOc;
	@JsonProperty("ProfitCommissionOc")
	private String  profitCommissionOc;
	@JsonProperty("TotalCost")
	private String  totalCost;
	@JsonProperty("WithHoldingTaxOc")
	private String  withHoldingTaxOc;
	@JsonProperty("PremiumReserveRetainedOc")
	private String  premiumReserveRetainedOc;
	@JsonProperty("PremiumReserveRealsedOc")
	private String  premiumReserveRealsedOc;
	@JsonProperty("LossReserveretainedOc")
	private String  lossReserveretainedOc;
	@JsonProperty("LossReserveReleasedOc")
	private String  lossReserveReleasedOc;
	@JsonProperty("InterestOc")
	private String  interestOc;
	@JsonProperty("ClaimsPaidOc")
	private String  claimsPaidOc;
	@JsonProperty("ClaimRecoveryOc")
	private String  claimRecoveryOc;
	@JsonProperty("ClaimPortfolioinOc")
	private String  claimPortfolioinOc;
	@JsonProperty("ClaimPortfolioOutOc")
	private String  claimPortfolioOutOc;
	@JsonProperty("CashLosspaidOc")
	private String  cashLosspaidOc;
	@JsonProperty("CashLossCreditOc")
	private String  cashLossCreditOc;
	@JsonProperty("NetClaims")
	private String  netClaims;
	@JsonProperty("ReceiptAmount")
	private String  receiptAmount;
	@JsonProperty("PaymentAmount")
	private String  paymentAmount;
	@JsonProperty("NetDueOc")
	private String  netDueOc;
	@JsonProperty("AllocatedTillDate")
	private String  allocatedTillDate;
	@JsonProperty("PendingAllocation")
	private String  pendingAllocation;
	@JsonProperty("ExchangeRate")
	private String  exchangeRate;
	@JsonProperty("PremiumQuotashareDc")
	private String  premiumQuotashareDc;
	@JsonProperty("PremiumSurplusDc")
	private String  premiumSurplusDc;
	@JsonProperty("PremiumTotalDc")
	private String  premiumTotalDc;

	@JsonProperty("PremiumPortfolioinDc")
	private String  premiumPortfolioinDc;
	@JsonProperty("PremiumPortfoliooutDc")
	private String  premiumPortfoliooutDc;
	@JsonProperty("NetPremiumDc")
	private String  netPremiumDc;
	@JsonProperty("CommissionQuotashareDc")
	private String  commissionQuotashareDc;
	@JsonProperty("CommissionSurplusDc")
	private String  commissionSurplusDc;
	@JsonProperty("CommissionTotalDc")
	private String  commissionTotalDc;
	@JsonProperty("BrokerageAmtDc")
	private String  brokerageAmtDc;
	@JsonProperty("TaxAmtDc")
	private String  taxAmtDc;
	@JsonProperty("OverriderAmtDc")
	private String  overriderAmtDc;
	@JsonProperty("OtherCostDc")
	private String  otherCostDc;
	@JsonProperty("XlCostDc")
	private String  xlCostDc;
	@JsonProperty("ProfitCommissionDc")
	private String  profitCommissionDc;
	@JsonProperty("TotalCostDc")
	private String  totalCostDc;
	@JsonProperty("WithHoldingTaxDc")
	private String  withHoldingTaxDc;
	@JsonProperty("PremiumReserveRetainedDc")
	private String  premiumReserveRetainedDc;
	@JsonProperty("PremiumReserveRealsedDc")
	private String  premiumReserveRealsedDc;
	@JsonProperty("LossReserveretainedDc")
	private String  lossReserveretainedDc;
	@JsonProperty("LossReserveReleasedDc")
	private String  lossReserveReleasedDc;
	@JsonProperty("InterestDc")
	private String  interestDc;
	@JsonProperty("ClaimsPaidDc")
	private String  claimsPaidDc;
	@JsonProperty("ClaimRecoveryDc")
	private String  claimRecoveryDc;
	@JsonProperty("ClaimPortfolioinDc")
	private String  claimPortfolioinDc;
	@JsonProperty("ClaimPortfolioOutDc")
	private String  claimPortfolioOutDc;
	@JsonProperty("CashLosspaidDc")
	private String  cashLosspaidDc;
	@JsonProperty("CashLossCreditDc")
	private String  cashLossCreditDc;
	@JsonProperty("NetClaimsDc")
	private String  netClaimsDc;
	@JsonProperty("ReceiptAmountDc")
	private String  receiptAmountDc;
	@JsonProperty("PaymentAmountDc")
	private String  paymentAmountDc;
	@JsonProperty("NetDueDc")
	private String netDueDc;
	@JsonProperty("AllocatedTillDateDc")
	private String  allocatedTillDateDc;
	@JsonProperty("PendingAllocationDc")
	private String  pendingAllocationDc;
	@JsonProperty("AllocationNumberDc")
	private String  allocationNumberDc;
	@JsonProperty("BranchCode")
	private String  branchCode;
	@JsonProperty("ReinstatementpremiumOc")
	private String  reinstatementpremiumOc;
	@JsonProperty("ReinstatementpremiumDc")
	private String  reinstatementpremiumDc;
	@JsonProperty("CustomerId")
	private String  customerId;
	@JsonProperty("BrokerId")
	private String  brokerId;
	@JsonProperty("Sno")
	private String  sno;
	@JsonProperty("LoginId")
	private String  loginId;
	@JsonProperty("TdsOc")
	private String  tdsOc;
	@JsonProperty("TdsDc")
	private String  tdsDc;
	@JsonProperty("StOc")
	private String  stOc;
	@JsonProperty("StDc")
	private String  stDc;
	@JsonProperty("ScCommOc")
	private String  scCommOc;
	@JsonProperty("ScCommDc")
	private String  scCommDc;
	@JsonProperty("BonusOc")
	private String  bonusOc;
	@JsonProperty("BonusDc")
	private String  bonusDc;
	@JsonProperty("LpcOc")
	private String  lpcOc;
	@JsonProperty("LpcDc")
	private String  lpcDc;
	@JsonProperty("PrdAllocatedTillDate")
	private String  prdAllocatedTillDate;
	@JsonProperty("LrdAllocatedTillDate")
	private String  lrdAllocatedTillDate;
	@JsonProperty("StatementDate")
	private String  statementDate;
	@JsonProperty("Osbyn")
	private String  osbyn;
	@JsonProperty("DepartmentName")
	private String  departmentName;
	@JsonProperty("OsclaimLossupdateOc")
	private String  osclaimLossupdateOc;
	@JsonProperty("OsclaimLossupdateDc")
	private String  osclaimLossupdateDc;
	@JsonProperty("LayerNo")
	private String  layerNo;
	@JsonProperty("SafOsOc")
	private String  safOsOc;
	@JsonProperty("SafOsDc")
	private String  safOsDc;
	@JsonProperty("OthFeeOsDc")
	private String  othFeeOsDc;


}
