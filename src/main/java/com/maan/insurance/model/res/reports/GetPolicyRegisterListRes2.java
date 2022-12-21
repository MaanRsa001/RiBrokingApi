package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;

import lombok.Data;

@Data
public class GetPolicyRegisterListRes2 {
	@JsonProperty("ProposalNo")
	private String proposalNo; 
	@JsonProperty("CreaseStatus")
	private String creaseStatus; 
	@JsonProperty("RskReinstateNo")
	private String rskReinstateNo; 
	@JsonProperty("RskSladscaleComm")
	private String rskSladscaleComm; 
	@JsonProperty("RskLossPartCarridor")
	private String rskLossPartCarridor; 
	@JsonProperty("LayerNo")
	private String layerNo; 
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
	@JsonProperty("TerritoryDesc")
	private String territoryDesc; 
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
	@JsonProperty("ProposalStatus")
	private String proposalStatus; 
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
	@JsonProperty("RskPremiumQuotaShareDc")
	private String rskPremiumQuotaShareDc; 
	@JsonProperty("RskPremiumSurpuls")
	private String rskPremiumSurpuls; 
	@JsonProperty("RskPremiumSurpulsDc")
	private String rskPremiumSurpulsDc; 
	@JsonProperty("RskCommQuotashare")
	private String rskCommQuotashare; 
	@JsonProperty("RskCommSurplus")
	private String rskCommSurplus; 
	@JsonProperty("CommissionQsAmount")
	private String commissionQsAmount; 
	@JsonProperty("CommissionSurpAmount")
	private String commissionSurpAmount; 
	@JsonProperty("RskOverriderPerc")
	private String rskOverriderPerc; 
	@JsonProperty("RskBrokerage")
	private String rskBrokerage; 
	@JsonProperty("RskTax")
	private String rskTax; 
	@JsonProperty("RskOtherCost")
	private String rskOtherCost; 
	@JsonProperty("OtherAcqCostAmount")
	private String otherAcqCostAmount; 
	@JsonProperty("AcqCostAmount")
	private String acqCostAmount; 
	@JsonProperty("RskProfitComm")
	private String rskProfitComm; 
	@JsonProperty("RskPremiumReserve")
	private String rskPremiumReserve; 
	@JsonProperty("RskLossReserve")
	private String rskLossReserve; 
	@JsonProperty("RskInterest")
	private String rskInterest; 
	@JsonProperty("RskPfInoutLoss")
	private String rskPfInoutLoss; 
	@JsonProperty("RskPfInoutPrem")
	private String rskPfInoutPrem; 
	@JsonProperty("RskLossadvice")
	private String rskLossadvice; 
	@JsonProperty("RskCashlossLmtOc")
	private String rskCashlossLmtOc; 
	@JsonProperty("RskLeadUw")
	private String rskLeadUw; 
	@JsonProperty("RskLeadUwShare")
	private String rskLeadUwShare; 
	@JsonProperty("RskExclusion")
	private String rskExclusion; 
	@JsonProperty("RskRemarks")
	private String rskRemarks; 
	@JsonProperty("RskUwRecomm")
	private String rskUwRecomm; 
	@JsonProperty("LoginId")
	private String loginId; 
	@JsonProperty("BrokerId")
	private String brokerId; 
	@JsonProperty("CedingCompanyId")
	private String cedingCompanyId; 
	@JsonProperty("UwYear")
	private String uwYear; 
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
	@JsonProperty("RskAggregateLiabOc")
	private String rskAggregateLiabOc; 
	@JsonProperty("RskReinstateAddlPremPct")
	private String rskReinstateAddlPremPct; 
	@JsonProperty("RskReinstateAddlPremOc")
	private String rskReinstateAddlPremOc; 
	@JsonProperty("RskBurningCostPct")
	private String rskBurningCostPct; 
	@JsonProperty("RskAggregateLiabDc")
	private String rskAggregateLiabDc; 
	@JsonProperty("RskReinstateAddlPremDc")
	private String rskReinstateAddlPremDc; 
	@JsonProperty("RskAcquistionCostOc")
	private String rskAcquistionCostOc; 
	@JsonProperty("RskAcquistionCostDc")
	private String rskAcquistionCostDc; 
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
	@JsonProperty("RskCashlossLmtDc")
	private String rskCashlossLmtDc; 
	@JsonProperty("LimitPerVesselOc")
	private String limitPerVesselOc; 
	@JsonProperty("LimitPerVesselDc")
	private String limitPerVesselDc; 
	@JsonProperty("LimitPerLocationOc")
	private String limitPerLocationOc; 
	@JsonProperty("LimitPerLocationDc")
	private String limitPerLocationDc; 
	@JsonProperty("TreatyRetro")
	private String treatyRetro; 
	@JsonProperty("TreatyRetroPerc")
	private String treatyRetroPerc; 
	@JsonProperty("CommQsAmt")
	private String commQsAmt; 
	@JsonProperty("CommSurplusAmt")
	private String commSurplusAmt; 
	@JsonProperty("CommQsAmtDc")
	private String commQsAmtDc; 
	@JsonProperty("CommSurplusAmtDc")
	private String commSurplusAmtDc; 
	@JsonProperty("RskLossadviceDc")
	private String rskLossadviceDc; 
	@JsonProperty("RskCommQuotashareDc")
	private String rskCommQuotashareDc; 
	@JsonProperty("CommissionSurpAmountDc")
	private String commissionSurpAmountDc; 
	@JsonProperty("OtherAcqCostAmountDc")
	private String otherAcqCostAmountDc; 
	@JsonProperty("EndorsementDate")
	private String endorsementDate; 
	@JsonProperty("EgpniAsOffer")
	private String egpniAsOffer; 
	@JsonProperty("Ourassessment")
	private String ourassessment; 
	@JsonProperty("InwardBusType")
	private String inwardBusType; 
	@JsonProperty("Treatytype")
	private String treatytype; 
	@JsonProperty("RskEventLimitOc")
	private String rskEventLimitOc; 
	@JsonProperty("RskEventLimitDc")
	private String rskEventLimitDc; 
	@JsonProperty("RskAggregateLimitOc")
	private String rskAggregateLimitOc; 
	@JsonProperty("RskAggregateLimitDc")
	private String rskAggregateLimitDc; 
	@JsonProperty("RskOccurrentLimitOc")
	private String rskOccurrentLimitOc; 
	@JsonProperty("RskOccurrentLimitDc")
	private String rskOccurrentLimitDc; 
	@JsonProperty("RskTreatySurpLimitOc")
	private String rskTreatySurpLimitOc; 
	@JsonProperty("RskTreatySurpLimitDc")
	private String rskTreatySurpLimitDc; 
	@JsonProperty("RskTreatySurpLimitOsOc")
	private String rskTreatySurpLimitOsOc; 
	@JsonProperty("RskTreatySurpLimitOsDc")
	private String rskTreatySurpLimitOsDc; 
	@JsonProperty("RskPml")
	private String rskPml; 
	@JsonProperty("RskPmlPercent")
	private String rskPmlPercent; 

	@JsonProperty("RskCreastaStatus")
	private String rskCreastaStatus; 

	@JsonProperty("RskProNotes")
	private String rskProNotes; 
	@JsonProperty("RskLocIssued")
	private String rskLocIssued; 
	@JsonProperty("RskPerilsCovered")
	private String rskPerilsCovered; 
	@JsonProperty("RskLeadUnderwriterCountry")
	private String rskLeadUnderwriterCountry; 
	@JsonProperty("RetenPer")
	private String retenPer; 
	@JsonProperty("RetroPer")
	private String retroPer; 
	@JsonProperty("RskTerritory")
	private String rskTerritory; 
	@JsonProperty("CountriesInclude")
	private String countriesInclude; 
	@JsonProperty("CountriesExclude")
	private String countriesExclude; 
	@JsonProperty("RskIncludeAcqCost")
	private String rskIncludeAcqCost; 
	@JsonProperty("RskOurAssAcqCost")
	private String rskOurAssAcqCost; 
	@JsonProperty("RskOurAcqOurShareOc")
	private String rskOurAcqOurShareOc; 
	@JsonProperty("RskNoOfLine")
	private String rskNoOfLine; 
	@JsonProperty("RskRate")
	private String rskRate; 
	@JsonProperty("RskProCommType")
	private String rskProCommType; 
	@JsonProperty("RskProSupProCom")
	private String rskProSupProCom; 
	@JsonProperty("RskProCommPer")
	private String rskProCommPer; 
	@JsonProperty("RskProSetUp")
	private String rskProSetUp; 
	@JsonProperty("RskProLossCaryType")
	private String rskProLossCaryType; 
	@JsonProperty("RskProLossCaryYear")
	private String rskProLossCaryYear; 
	@JsonProperty("RskProProfitCaryType")
	private String rskProProfitCaryType; 
	@JsonProperty("RskProProfitCaryYear")
	private String rskProProfitCaryYear; 
	@JsonProperty("RskProFirstPfoCom")
	private String rskProFirstPfoCom; 
	@JsonProperty("RskProFirstPfoComPrd")
	private String rskProFirstPfoComPrd; 
	@JsonProperty("RskProSubPfoComPrd")
	private String rskProSubPfoComPrd; 
	@JsonProperty("RskProSubPfoCom")
	private String rskProSubPfoCom; 
	@JsonProperty("RskProSubSeqCal")
	private String rskProSubSeqCal; 

	@JsonProperty("RskRunOffYear")
	private String rskRunOffYear; 
	@JsonProperty("RskTrtyLmtPmlOc")
	private String rskTrtyLmtPmlOc; 
	@JsonProperty("RskTrtyLmtPmlOsOc")
	private String rskTrtyLmtPmlOsOc; 
	@JsonProperty("RskTrtyLmtPmlDc")
	private String rskTrtyLmtPmlDc; 
	@JsonProperty("RskTrtyLmtPmlOsDc")
	private String rskTrtyLmtPmlOsDc; 
	@JsonProperty("RskTrtyLmtSurPmlOc")
	private String rskTrtyLmtSurPmlOc; 
	@JsonProperty("RskTrtyLmtSurPmlOsOc")
	private String rskTrtyLmtSurPmlOsOc; 
	@JsonProperty("RskTrtyLmtSurPmlDc")
	private String rskTrtyLmtSurPmlDc; 
	@JsonProperty("RskTrtyLmtSurPmlOsDc")
	private String rskTrtyLmtSurPmlOsDc; 
	@JsonProperty("RskLocBnkName")
	private String rskLocBnkName; 
	@JsonProperty("RskLocCrdtPrd")
	private String rskLocCrdtPrd; 
	@JsonProperty("RskLocCrdtAmt")
	private String rskLocCrdtAmt; 
	@JsonProperty("RskLocBenfcreName")
	private String rskLocBenfcreName; 
	@JsonProperty("Type")
	private String type; 
	@JsonProperty("RskCoverLimitUxlOc")
	private String rskCoverLimitUxlOc; 
	@JsonProperty("RskCoverLimitUxlOsOc")
	private String rskCoverLimitUxlOsOc; 
	@JsonProperty("RskCoverLimitUxlDc")
	private String rskCoverLimitUxlDc; 
	@JsonProperty("RskCoverLimitUxlOsDc")
	private String rskCoverLimitUxlOsDc; 
	@JsonProperty("RskDeductableUxlOc")
	private String rskDeductableUxlOc; 
	@JsonProperty("RskDeductableUxlOsOc")
	private String rskDeductableUxlOsOc; 
	@JsonProperty("RskDeductableUxlDc")
	private String rskDeductableUxlDc; 
	@JsonProperty("RskDeductableUxlOsDc")
	private String rskDeductableUxlOsDc; 
	@JsonProperty("RskMinimumPremiumOc")
	private String rskMinimumPremiumOc; 
	@JsonProperty("RskMinimumPremiumOsOc")
	private String rskMinimumPremiumOsOc; 
	@JsonProperty("RskMinimumPremiumDc")
	private String rskMinimumPremiumDc; 
	@JsonProperty("RskMinimumPremiumOsDc")
	private String rskMinimumPremiumOsDc; 
	@JsonProperty("EgpniAsOfferDc")
	private String egpniAsOfferDc; 
	@JsonProperty("RskAggregateDeductOc")
	private String rskAggregateDeductOc; 
	@JsonProperty("RskAggregateDeductDc")
	private String rskAggregateDeductDc; 
	@JsonProperty("RskPremiumBasis")
	private String rskPremiumBasis; 
	@JsonProperty("RskMinimumRate")
	private String rskMinimumRate; 
	@JsonProperty("RskMaxiimumRate")
	private String   rskMaxiimumRate; 
	@JsonProperty("RskBurningCostLf")
	private String rskBurningCostLf; 
	@JsonProperty("RskReinstatementPremium")
	private String rskReinstatementPremium; 
	@JsonProperty("RskBonusId")
	private String rskBonusId; 
	@JsonProperty("RskNoclaimbonusPrcent")
	private String rskNoclaimbonusPrcent; 
	@JsonProperty("RskUmbrellaXl")
	private String rskUmbrellaXl ;


}
