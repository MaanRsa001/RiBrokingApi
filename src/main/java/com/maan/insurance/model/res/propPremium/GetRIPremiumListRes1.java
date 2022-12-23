package com.maan.insurance.model.res.propPremium;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.req.propPremium.GetVatInfoReq;
import com.maan.insurance.model.res.facPremium.SettlementstatusRes;

import lombok.Data;

@Data
public class GetRIPremiumListRes1 {
	@JsonProperty("ReInsurerName")
	private String reInsurerName;
	@JsonProperty("BrokerName")
	private String brokerName;
	@JsonProperty("BrokerId")
	private String brokerId;
//	@JsonProperty("Brokerage")
//	private String brokerage;
	@JsonProperty("SignShared")
	private String signShared;
	@JsonProperty("ReinsurerId")
	private String reinsurerId;
//	
//
//	
//	@JsonProperty("Transaction")
//	private String transaction;
//	
//	@JsonProperty("AccountPeriod")
//	private String accountPeriod;
//	
//	@JsonProperty("AccountPeriodyear")
//	private String accountPeriodyear;
//	
//	@JsonProperty("CurrencyId")
//	private String currencyId;
//	
//	@JsonProperty("Currency")
//	private String currency;
//	
//	@JsonProperty("ExchRate")
//	private String exchRate;
//	
//
//	
//	@JsonProperty("Tax")
//	private String tax;
//	
//	@JsonProperty("PremiumQuotaShare")
//	private String premiumQuotaShare;
//	
//	@JsonProperty("CommissionQuotaShare")
//	private String commissionQuotaShare;
//	
//	@JsonProperty("PremiumSurplus")
//	private String premiumSurplus;
//	
//	@JsonProperty("CommissionSurplus")
//	private String commissionSurplus;
//	
//	@JsonProperty("PremiumportifolioIn")
//	private String premiumportifolioIn;
//	
//	@JsonProperty("CliamPortfolioin")
//	private String cliamPortfolioin;
//	
//	@JsonProperty("Premiumportifolioout")
//	private String premiumportifolioout;
//	
//	@JsonProperty("LossReserveReleased")
//	private String lossReserveReleased;
//	
//	@JsonProperty("PremiumReserveQuotaShare")
//	private String premiumReserveQuotaShare;
//	
//	@JsonProperty("CashLossCredit")
//	private String cashLossCredit;
//	
//	@JsonProperty("LossReserveRetained")
//	private String lossReserveRetained;
//	
//	@JsonProperty("ProfitCommission")
//	private String profitCommission;
//	
//	@JsonProperty("CashLossPaid")
//	private String cashLossPaid;
//	
//	@JsonProperty("Status")
//	private String status;
//	
//	@JsonProperty("NetDue")
//	private String netDue;
//	
//	@JsonProperty("EnteringMode")
//	private String enteringMode;
//	
//	@JsonProperty("Receiptno")
//	private String receiptno;
//	
//	@JsonProperty("Claimspaid")
//	private String claimspaid;
//	
//	@JsonProperty("Mdpremium")
//	private String mdpremium;
//	
//	@JsonProperty("Adjustmentpremium")
//	private String adjustmentpremium;
//	
//	@JsonProperty("Recuirementpremium")
//	private String recuirementpremium;
//	
//	@JsonProperty("Commission")
//	private String commission;
//	
//	@JsonProperty("InstlmentNo")
//	private String instlmentNo;
//	
//	@JsonProperty("InceptionDate")
//	private String inceptionDate;
//	
//	@JsonProperty("XlCost")
//	private String xlCost;
//	
//	@JsonProperty("Cliamportfolioout")
//	private String cliamportfolioout;
//	
//	@JsonProperty("PremiumReserveReleased")
//	private String premiumReserveReleased;
//	
//	@JsonProperty("OtherCost")
//	private String otherCost;
//	
//	@JsonProperty("CedentRef")
//	private String cedentRef;
//	
//	@JsonProperty("Remarks")
//	private String remarks;
//	
//	@JsonProperty("Interest")
//	private String interest;
//	
//	@JsonProperty("OsClaimsLossUpdateOC")
//	private String osClaimsLossUpdateOC;
//	
//	@JsonProperty("Overrider")
//	private String overrider;
//	
//	@JsonProperty("OverriderUSD")
//	private String overriderUSD;
//	
//	@JsonProperty("AmendmentDate")
//	private String amendmentDate;
//	
//	@JsonProperty("WithHoldingTaxOC")
//	private String withHoldingTaxOC;
//	
//	@JsonProperty("WithHoldingTaxDC")
//	private String withHoldingTaxDC;
//	
//	@JsonProperty("Ricession")
//	private String ricession;
//	
//	@JsonProperty("TaxDedectSource")
//	private String taxDedectSource;
//	
//	@JsonProperty("TaxDedectSourceDc")
//	private String taxDedectSourceDc;
//	
	@JsonProperty("VatPremiumOc")
	private String vatPremiumOc;
	
	@JsonProperty("VatPremiumDc")
	private String vatPremiumDc;
//	
//	@JsonProperty("LossParticipation")
//	private String lossParticipation;
//	
//	@JsonProperty("LossParticipationDC")
//	private String lossParticipationDC;
//	
//	@JsonProperty("SlideScaleCom")
//	private String slideScaleCom;
//	
//	@JsonProperty("SlideScaleComDC")
//	private String slideScaleComDC;
//	
//	@JsonProperty("SubProfitId")
//	private String subProfitId;
//	
//	@JsonProperty("PrAllocatedAmount")
//	private String prAllocatedAmount;
//	
//	@JsonProperty("LrAllocatedAmount")
//	private String lrAllocatedAmount;
//	
//	@JsonProperty("StatementDate")
//	private String statementDate;
//	
//	@JsonProperty("OsbYN")
//	private String osbYN;
//	
//	@JsonProperty("SectionName")
//	private String sectionName;
//	
////	@JsonProperty("SectionType")
////	private String sectionType;
//	
//	@JsonProperty("AccountPeriodDate")
//	private String accountPeriodDate;
//	
//	@JsonProperty("Predepartment")
//	private String predepartment;
	
	

	
	


	@JsonProperty("NetDue")
	private String netDue;
	@JsonProperty("Transaction")
	private String transaction;

	@JsonProperty("CurrencyId")
	private String currencyId;
	@JsonProperty("ExchRate")
	private String exchRate;

	@JsonProperty("Brokerageusd")
	private String brokerageusd;
	@JsonProperty("Taxusd")
	private String taxusd;

	@JsonProperty("Netdueusd")
	private String netdueusd;
	@JsonProperty("OtherCostUSD")
	private String otherCostUSD;
	@JsonProperty("Remarks")
	private String remarks;
	@JsonProperty("TotalCredit")
	private String totalCredit;
	@JsonProperty("TotalDebit")
	private String totalDebit;
	@JsonProperty("TotalDebitDC")
	private String totalDebitDC;


	@JsonProperty("TransactionNo")
	private String transactionNo;

	@JsonProperty("AmendmentDate")
	private String amendmentDate;
	@JsonProperty("Brokerage")
	private String brokerage;

	@JsonProperty("Tax")
	private String tax;
	@JsonProperty("WithHoldingTaxOC")
	private String withHoldingTaxOC;
	@JsonProperty("Ricession")
	private String ricession;
	@JsonProperty("WithHoldingTaxDC")
	private String withHoldingTaxDC;
	@JsonProperty("DueDate")
	private String dueDate;
	@JsonProperty("Creditsign")
	private String Creditsign;
	@JsonProperty("TaxDedectSource")
	private String taxDedectSource;
	@JsonProperty("TaxDedectSourceDc")
	private String taxDedectSourceDc;
//	@JsonProperty("ServiceTax")
//	private String serviceTax;
//	@JsonProperty("ServiceTaxDc")
//	private String serviceTaxDc;
	@JsonProperty("InceptionDate")
	private String inceptionDate;
	@JsonProperty("Bonus")
	private String 	bonus;
	@JsonProperty("OtherCost")
	private String otherCost;
	@JsonProperty("CedentRef")
	private String cedentRef; 
	@JsonProperty("TotalCreditDC")
	private String totalCreditDC;
	@JsonProperty("BonusDc")
	private String bonusDc;

	@JsonProperty("ChooseTransaction")
	private String chooseTransaction;
	@JsonProperty("TransDropDownVal")
	private String transDropDownVal;
	@JsonProperty("StatementDate")
	private String statementDate;

	@JsonProperty("Currency")
	private String currency;
	@JsonProperty("CurrencyName")
	private String 	currencyName;
	@JsonProperty("AccountPeriod")
	private String accountPeriod;
	@JsonProperty("SettlementstatusList")
	private List<SettlementstatusRes> SettlementstatusRes;

	@JsonProperty("ContNo")
	private String contNo;
	@JsonProperty("Mdpremium")
	private String mdpremium;
	@JsonProperty("Adjustmentpremium")
	private String adjustmentpremium;
	@JsonProperty("Recuirementpremium")
	private String recuirementpremium;
	@JsonProperty("Layerno")
	private String layerno;
	@JsonProperty("EnteringMode")
	private String enteringMode;
	@JsonProperty("Mdpremiumusd")
	private String mdpremiumusd;
	@JsonProperty("Adjustmentpremiumusd")
	private String adjustmentpremiumusd;
	@JsonProperty("Recuirementpremiumusd")
	private String recuirementpremiumusd;
	@JsonProperty("Predepartment")
	private String predepartment;
	@JsonProperty("DepartmentId")
	private String departmentId;
	@JsonProperty("GnpiDate")
	private String gnpiDate;
	

}
