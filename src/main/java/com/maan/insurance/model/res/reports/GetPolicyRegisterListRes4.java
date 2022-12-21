package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;

import lombok.Data;

@Data
public class GetPolicyRegisterListRes4 {
	
	@JsonProperty("ProposalNo")
	private String proposalNo; 
	@JsonProperty("ContractNo")
	private String contractNo; 
	@JsonProperty("TmasPfcName")
	private String tmasPfcName; 
	@JsonProperty("Underwritter")
	private String underwritter; 
	@JsonProperty("TmasDepartmentName")
	private String tmasDepartmentName; 
	@JsonProperty("TmasSpfcName")
	private String tmasSpfcName; 
	@JsonProperty("RskUwyear")
	private String rskUwyear; 
	@JsonProperty("RskMaxLmtCover")
	private String rskMaxLmtCover; 
	@JsonProperty("TmasPolBranchName")
	private String tmasPolBranchName; 
	@JsonProperty("CompanyName")
	private String companyName; 
	@JsonProperty("Broker")
	private String broker; 
	@JsonProperty("RskTreatyid")
	private String rskTreatyid; 
	@JsonProperty("ProposalType")
	private String proposalType; 
	@JsonProperty("InceptionDate")
	private String inceptionDate; 
	@JsonProperty("ExpiryDate")
	private String expiryDate; 
	@JsonProperty("AccountDate")
	private String accountDate; 
	@JsonProperty("ShortName")
	private String shortName; 
	@JsonProperty("RskExchangeRate")
	private String rskExchangeRate; 
	@JsonProperty("RskPeriodOfNotice")
	private String rskPeriodOfNotice; 
	@JsonProperty("RskRiskCovered")
	private String rskRiskCovered; 
	@JsonProperty("RskTerritoryScope")
	private String rskTerritoryScope; 
	@JsonProperty("AccountingPeriod")
	private String accountingPeriod; 
	@JsonProperty("RskReceiptStatement")
	private String rskReceiptStatement; 
	@JsonProperty("RskReceiptPayement")
	private String rskReceiptPayement; 
	@JsonProperty("RskSpRetro")
	private String rskSpRetro; 
	@JsonProperty("RskNoOfInsurers")
	private String rskNoOfInsurers; 
	@JsonProperty("RskLimitOc")
	private String rskLimitOc; 
	@JsonProperty("RskEpiOfferOc")
	private String rskEpiOfferOc; 
	@JsonProperty("RskEpiEstimate")
	private String rskEpiEstimate; 
	@JsonProperty("RskEpiEstOc")
	private String rskEpiEstOc; 
	@JsonProperty("RskXlcostOc")
	private String rskXlcostOc; 
	@JsonProperty("RskCedretType")
	private String rskCedretType; 
	@JsonProperty("RskCedantRetention")
	private String rskCedantRetention; 
	@JsonProperty("ProposalStatu")
	private String proposalStatu; 
	@JsonProperty("RskShareWritten")
	private String rskShareWritten; 
	@JsonProperty("RskShareSigned")
	private String rskShareSigned; 
	@JsonProperty("RskLimitOsOc")
	private String rskLimitOsOc; 
	@JsonProperty("RskLimitOsDc")
	private String rskLimitOsDc; 
	@JsonProperty("RskEpiOsofOc")
	private String rskEpiOsofOc; 
	@JsonProperty("RskEpiOsofDc")
	private String rskEpiOsofDc; 
	@JsonProperty("RskEpiOsoeOc")
	private String rskEpiOsoeOc; 
	@JsonProperty("RskEpiOsoeDc")
	private String rskEpiOsoeDc; 
	@JsonProperty("RskXlcostOsOc")
	private String rskXlcostOsOc; 
	@JsonProperty("RskXlcostOsDc")
	private String rskXlcostOsDc; 
	@JsonProperty("RskPremiumQuotaShare")
	private String rskPremiumQuotaShare; 
	@JsonProperty("RskPremiumSurpuls")
	private String rskPremiumSurpuls; 
	@JsonProperty("LoginId")
	private String loginId; 
	@JsonProperty("BrokerId")
	private String brokerId; 
	@JsonProperty("CedingCompanyId")
	private String cedingCompanyId; 
	@JsonProperty("UwYear")
	private String uwYear; 
	@JsonProperty("RskLayerNo")
	private String rskLayerNo; 
	@JsonProperty("RskBasis")
	private String rskBasis; 
	@JsonProperty("RskDeducOc")
	private String rskDeducOc; 
	@JsonProperty("RskSubjPremiumOc")
	private String rskSubjPremiumOc; 
	@JsonProperty("RskAdjrate")
	private String rskAdjrate; 
	@JsonProperty("RskMdPremOc")
	private String rskMdPremOc; 
	@JsonProperty("MndInstallments")
	private String mndInstallments; 
	@JsonProperty("RskMdPremOsOc")
	private String rskMdPremOsOc; 
	@JsonProperty("RskMdPremOsDc")
	private String rskMdPremOsDc; 
	@JsonProperty("ProductId")
	private String productId; 
	@JsonProperty("LayerNo")
	private String layerNo; 
	@JsonProperty("RskSubjPremiumDc")
	private String rskSubjPremiumDc; 
	@JsonProperty("RskLimitDc")
	private String rskLimitDc; 
	@JsonProperty("RskDeducDc")
	private String rskDeducDc; 
	@JsonProperty("RskEpiEstDc")
	private String rskEpiEstDc; 
	@JsonProperty("RskXlpremDc")
	private String rskXlpremDc; 
	@JsonProperty("RskMdPremDc")
	private String rskMdPremDc; 
	@JsonProperty("RskPfCovered")
	private String rskPfCovered; 
	@JsonProperty("RskEpiOfferDc")
	private String rskEpiOfferDc; 
	@JsonProperty("RskXlcostDc")
	private String rskXlcostDc; 
	@JsonProperty("RskRetroType")
	private String rskRetroType; 
	@JsonProperty("RetroCessionaries")
	private String retroCessionaries; 
	@JsonProperty("RskRemarks")
	private String rskRemarks; 
	
}
