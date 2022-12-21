package com.maan.insurance.model.res.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetPolicyRegisterListRes1 {

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
    @JsonProperty("MaximumLimitOc")
	private String maximumLimitOc;
    @JsonProperty("UwCapacityUgx")
	private String uwCapacityUgx;
    @JsonProperty("TmasPolBranchName")
	private String tmasPolBranchName;
    @JsonProperty("TerritoryDesc")
	private String territoryDesc;
    @JsonProperty("CompanyName")
	private String companyName;
    @JsonProperty("Broker")
	private String broker;
    @JsonProperty("IncDate")
	private String incDate;
    @JsonProperty("ExpDate")
	private String expDate;
    @JsonProperty("AccDate")
	private String accDate;
    @JsonProperty("ShortName")
	private String shortName;
    @JsonProperty("RskExchangeRate")
	private String rskExchangeRate;
    @JsonProperty("RskInsuredName")
	private String rskInsuredName;
    @JsonProperty("RskCity")
	private String rskCity;
    @JsonProperty("RskLocation")
	private String rskLocation;
    @JsonProperty("CoverLimitOc")
	private String coverLimitOc;
    @JsonProperty("CoverLimitDc")
	private String coverLimitDc;
    @JsonProperty("DeductibleOc")
	private String deductibleOc;
    @JsonProperty("DeductibleDc")
	private String deductibleDc;
   @JsonProperty("MLop")
	private String mLop;
    @JsonProperty("ALop")
	private String aLop;
    @JsonProperty("RskCedretType")
	private String rskCedretType;
    @JsonProperty("RskCedantRetention")
	private String rskCedantRetention;
    @JsonProperty("RskPremiumRate")
	private String rskPremiumRate;
    @JsonProperty("SpRetro")
	private String spRetro;
    @JsonProperty("SpecficRetro")
	private String specficRetro;
    @JsonProperty("SpecficRetroPerc")
	private String specficRetroPerc;
    @JsonProperty("TreatyRetro")
	private String treatyRetro;
    @JsonProperty("TreatyRetroPerc")
	private String treatyRetroPerc;
    @JsonProperty("NoOfInstallment")
	private String noOfInstallment;
    @JsonProperty("NoOfInsurers")
	private String noOfInsurers;
    @JsonProperty("SiPml")
	private String siPml;
    @JsonProperty("SiPmlDc")
	private String siPmlDc;
    @JsonProperty("Pml")
	private String pml;
    @JsonProperty("SumInsuredOc")
	private String sumInsuredOc;
    @JsonProperty("PmlOc")
	private String pmlOc;
    @JsonProperty("GwpiOc")
	private String gwpiOc;
    @JsonProperty("TplOc")
	private String tplOc;
    @JsonProperty("ProposalStatus")
	private String proposalStatus;
    @JsonProperty("RskInterest")
	private String rskInterest;
    @JsonProperty("ShareWritten")
	private String shareWritten;
    @JsonProperty("ShareSigned")
	private String shareSigned;
    @JsonProperty("SumInsuredOurShareOc")
	private String sumInsuredOurShareOc;
    @JsonProperty("SumInsuredOurShareDc")
	private String sumInsuredOurShareDc;
    @JsonProperty("PmlOurShareOc")
	private String pmlOurShareOc;
    @JsonProperty("PmlOurShareDc")
	private String pmlOurShareDc;
    @JsonProperty("GwpiOurShareOc")
	private String gwpiOurShareOc;
    @JsonProperty("GwpiOurShareDc")
	private String gwpiOurShareDc;
    @JsonProperty("TplOurShareOc")
	private String tplOurShareOc;
    @JsonProperty("TplOurShareDc")
	private String tplOurShareDc;
    @JsonProperty("GradeDesc")
	private String gradeDesc;
    @JsonProperty("occupationCode")
	private String occupationCode;
    @JsonProperty("RiskDetails")
	private String riskDetails;
    @JsonProperty("FirePort")
	private String firePort;
    @JsonProperty("Scope")
	private String scope;
    @JsonProperty("MbInd")
	private String mbInd;
    @JsonProperty("CategoryZone")
	private String categoryZone;
    @JsonProperty("EarthquakeWsInd")
	private String earthquakeWsInd;
    @JsonProperty("EqThreat")
	private String eqThreat;
    @JsonProperty("RskComm")
	private String rskComm;
    @JsonProperty("RskBrokerage")
	private String rskBrokerage;
    @JsonProperty("RskTax")
	private String rskTax;
    @JsonProperty("RskOtherCost")
	private String rskOtherCost;
    @JsonProperty("AccPercentage")
	private String accPercentage;
    @JsonProperty("RskAcquistionCostOc")
	private String rskAcquistionCostOc;
    @JsonProperty("Cu")
	private String cu;
    @JsonProperty("CuRsn")
	private String cuRsn;
    @JsonProperty("RskLossRecord")
	private String rskLossRecord;
    @JsonProperty("RskRefToHo")
	private String rskRefToHo;
    @JsonProperty("RskUwRecommendation")
	private String rskUwRecommendation;
    @JsonProperty("RskRemarks")
	private String rskRemarks;
    @JsonProperty("RskOthAccep")
	private String rskOthAccep;
    @JsonProperty("LoginId")
	private String loginId;
    @JsonProperty("BrokerId")
	private String brokerId;
    @JsonProperty("CedingCompanyId")
	private String cedingCompanyId;
    @JsonProperty("UwYear")
	private String uwYear;
    @JsonProperty("DeptId")
	private String deptId;
    @JsonProperty("SumInsuredDc")
	private String sumInsuredDc;
     @JsonProperty("TplDc")
	private String tplDc;
    @JsonProperty("GwpiDc")
	private String gwpiDc;
     @JsonProperty("PmlDc")
	private String pmlDc;
    @JsonProperty("RskAcquistionCostDc")
	private String rskAcquistionCostDc;
    @JsonProperty("BranchCode")
	private String branchCode;
    @JsonProperty("LimitPerVesselOc")
	private String limitPerVesselOc;
    @JsonProperty("LimitPerVesselDc")
	private String limitPerVesselDc;
    @JsonProperty("LimitPerLocationOc")
	private String limitPerLocationOc;
    @JsonProperty("LimitPerLocationDc")
	private String limitPerLocationDc;
    @JsonProperty("ModeOfTransport")
	private String modeOfTransport;
    @JsonProperty("VesselName")
	private String vesselName;
    @JsonProperty("VesselAge")
	private String vesselAge;
    @JsonProperty("Type")
	private String type;
    @JsonProperty("DeductibleOurshareOc")
	private String deductibleOurshareOc;
    @JsonProperty("DeductibleOurshareUgx")
	private String deductibleOurshareUgx;
    @JsonProperty("CoverlimitOurshareOc")
	private String coverlimitOurshareOc;
    @JsonProperty("CoverlimitOurshareUgx")
	private String coverlimitOurshareUgx;
    @JsonProperty("EndorsementDate")
	private String endorsementDate;
    @JsonProperty("RskBonusType")
	private String rskBonusType;
     @JsonProperty("RskBonusPer")
	private String rskBonusPer;
    @JsonProperty("InwardBusType")
	private String inwardBusType;
    @JsonProperty("RskLatitude")
	private String rskLatitude;
    @JsonProperty("RskLongitude")
	private String rskLongitude;
    @JsonProperty("RskLocIssued")
	private String rskLocIssued;
     @JsonProperty("RskVessalTonnage")
	private String rskVessalTonnage;
     @JsonProperty("CoverLimitPmlOc")
	private String coverLimitPmlOc;
    @JsonProperty("RskReceiptPayement")
	private String rskReceiptPayement;
    @JsonProperty("PslOc")
	private String pslOc;
    @JsonProperty("PllOc")
	private String pllOc;
    @JsonProperty("PblOc")
	private String pblOc;
    @JsonProperty("PslOsOc")
	private String pslOsOc;
    @JsonProperty("PllOsOc")
	private String pllOsOc;
    @JsonProperty("PblOsOc")
	private String pblOsOc;
    @JsonProperty("PslDc")
	private String pslDc;
    @JsonProperty("PllDc")
	private String pllDc;
    @JsonProperty("PblDc")
	private String pblDc;
    @JsonProperty("RskCreastaStatus")
	private String rskCreastaStatus;
    @JsonProperty("RskExclusion")
	private String rskExclusion;
    @JsonProperty("RskLeadUw")
	private String rskLeadUw;
    @JsonProperty("RskLeadUnderwriterCountry")
	private String rskLeadUnderwriterCountry;
    @JsonProperty("RskLeadUwShare")
	private String rskLeadUwShare;
    @JsonProperty("PslOsDc")
	private String pslOsDc;
    @JsonProperty("PllOsDc")
	private String pllOsDc;
    @JsonProperty("CoverLimitPmlOsOc")
	private String coverLimitPmlOsOc;
    @JsonProperty("CoverLimitPmlDc")
	private String coverLimitPmlDc;
    @JsonProperty("CoverLimitPmlOsDc")
	private String coverLimitPmlOsDc;
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
    @JsonProperty("RskLocBnkName")
	private String rskLocBnkName;
    @JsonProperty("RskLocCrdtPrd")
	private String rskLocCrdtPrd;
    @JsonProperty("RskLocCrdtAmt")
	private String rskLocCrdtAmt;
    @JsonProperty("RskLocBenfcreName")
	private String rskLocBenfcreName;
    @JsonProperty("PblOsDc")
	private String pblOsDc;
	
	
}
