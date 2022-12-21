package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;

import lombok.Data;

@Data
public class GetPremiumRegisterListRes1 {
    @JsonProperty("ContractNo")
	private String contractNo;
    @JsonProperty("TransactionNo")
	private String transactionNo;
    @JsonProperty("TransactionDate")
	private String transactionDate;
    @JsonProperty("TmasPfcName")
	private String tmasPfcName;
    @JsonProperty("RskInsuredName")
	private String rskInsuredName;
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
    @JsonProperty("TdsOc")
	private String tdsOc;
    @JsonProperty("StOc")
	private String stOc;
    @JsonProperty("TotalCrOc")
	private String totalCrOc;
    @JsonProperty("CommissionQuotashareOc")
	private String commissionQuotashareOc;
    @JsonProperty("BrokerageAmtOc")
	private String brokerageAmtOc;
    @JsonProperty("TaxAmtOc")
	private String taxAmtOc;
    @JsonProperty("OtherCostOc")
	private String otherCostOc;
    @JsonProperty("WithHoldingTaxOc")
	private String withHoldingTaxOc;
    @JsonProperty("BonusOc")
	private String bonusOc;
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
    @JsonProperty("TdsDc")
	private String tdsDc;
    @JsonProperty("StDc")
	private String stDc;
    @JsonProperty("TotalCrDc")
	private String totalCrDc;
    @JsonProperty("CommissionQuotashareDc")
	private String commissionQuotashareDc;
    @JsonProperty("BrokerageAmtDc")
	private String brokerageAmtDc;
    @JsonProperty("TaxAmtDc")
	private String taxAmtDc;
    @JsonProperty("OtherCostDc")
	private String otherCostDc;
    @JsonProperty("WithHoldingTaxDc")
	private String withHoldingTaxDc;
    @JsonProperty("BonusDc")
	private String bonusDc;
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
    @JsonProperty("EndorsementDate")
	private String endorsementDate;
}
