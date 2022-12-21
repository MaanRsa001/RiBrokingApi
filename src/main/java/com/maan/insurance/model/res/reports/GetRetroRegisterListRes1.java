package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.res.DropDown.GetCedingCompanyRes;
import com.maan.insurance.model.res.DropDown.GetCedingCompanyRes1;

import lombok.Data;

@Data
public class GetRetroRegisterListRes1 {
	
	@JsonProperty("BranchCode")
	private String branchCode;

	@JsonProperty("TransactionMonthYear")
	private String transactionMonthYear;

	@JsonProperty("Retrocontractnumber")
	private String retrocontractnumber;

	@JsonProperty("Retrobrokername")
	private String retrobrokername;

	@JsonProperty("RetroBroker")
	private String retroBroker;

	@JsonProperty("Retrocessionairename")
	private String retrocessionairename;

	@JsonProperty("Retrocessionaire")
	private String retrocessionaire;

	@JsonProperty("Inwardcontractno")
	private String inwardcontractno;

	@JsonProperty("Layerno")
	private String layerno;

	@JsonProperty("Uwy")
	private String uwy;

	@JsonProperty("Inwardbusiness")
	private String inwardbusiness;

	@JsonProperty("Subclass")
	private String subclass;

	@JsonProperty("Transactionno")
	private String transactionno;

	@JsonProperty("Transactiondate")
	private String transactiondate;

	@JsonProperty("Transactiontype")
	private String transactiontype;

	@JsonProperty("Inwardcurrencyname")
	private String inwardcurrencyname;

	@JsonProperty("Inwardexchangerate")
	private String inwardexchangerate;

	@JsonProperty("RetrocessionPercentage")
	private String retrocessionPercentage;

	@JsonProperty("Retrocurrencyname")
	private String retrocurrencyname;

	@JsonProperty("Retroexchangerate")
	private String retroexchangerate;

	@JsonProperty("PremiumIc")
	private String premiumIc;

	@JsonProperty("CommissionIc")
	private String commissionIc;

	@JsonProperty("BrokerageIc")
	private String brokerageIc;

	@JsonProperty("InterestIc")
	private String interestIc;

	@JsonProperty("TaxIc")
	private String taxIc;

	@JsonProperty("InwardoverriderIc")
	private String inwardoverriderIc;

	@JsonProperty("OthercostIc")
	private String othercostIc;

	@JsonProperty("ClamspaidIc")
	private String clamspaidIc;

	@JsonProperty("PremiumreserveretainedIc")
	private String premiumreserveretainedIc;

	@JsonProperty("PremiumreservereleasedIc")
	private String premiumreservereleasedIc;

	@JsonProperty("LossreserveretainedIc")
	private String lossreserveretainedIc;

	@JsonProperty("LossreservereleasedIc")
	private String lossreservereleasedIc;

	@JsonProperty("OutwardoverriderIc")
	private String outwardoverriderIc;

	@JsonProperty("NetdueIc")
	private String netdueIc;

	@JsonProperty("PremiumInr")
	private String premiumInr;

	@JsonProperty("CommissionInr")
	private String commissionInr;

	@JsonProperty("BrokerageInr")
	private String brokerageInr;

	@JsonProperty("InterestInr")
	private String interestInr;

	@JsonProperty("TaxInr")
	private String taxInr;

	@JsonProperty("InwardoverriderInr")
	private String inwardoverriderInr;

	@JsonProperty("OthercostInr")
	private String othercostInr;

	@JsonProperty("ClamspaidInr")
	private String clamspaidInr;

	@JsonProperty("PremiumreserveretainedInr")
	private String premiumreserveretainedInr;

	@JsonProperty("PremiumreservereleasedInr")
	private String premiumreservereleasedInr;

	@JsonProperty("LossreserveretainedInr")
	private String lossreserveretainedInr;

	@JsonProperty("LossreservereleasedInr")
	private String lossreservereleasedInr;

	@JsonProperty("OutwardoverriderInr")
	private String outwardoverriderInr;

	@JsonProperty("NetdueInr")
	private String netdueInr;

	@JsonProperty("PremiumRc")
	private String premiumRc;

	@JsonProperty("CommissionRc")
	private String commissionRc;

	@JsonProperty("BrokerageRc")
	private String brokerageRc;

	@JsonProperty("InterestRc")
	private String interestRc;

	@JsonProperty("TaxRc")
	private String taxRc;

	@JsonProperty("InwardoverriderRc")
	private String inwardoverriderRc;

	@JsonProperty("OthercostRc")
	private String othercostRc;

	@JsonProperty("ClamspaidRc")
	private String clamspaidRc;

	@JsonProperty("PremiumreserveretainedRc")
	private String premiumreserveretainedRc;

	@JsonProperty("PremiumreservereleasedRc")
	private String premiumreservereleasedRc;

	@JsonProperty("LossreserveretainedRc")
	private String lossreserveretainedRc;

	@JsonProperty("LossreservereleasedRc")
	private String lossreservereleasedRc;

	@JsonProperty("OutwardoverriderRc")
	private String outwardoverriderRc;

	@JsonProperty("NetdueRc")
	private String netdueRc;

	@JsonProperty("Soafrequency")
	private String soafrequency;

	@JsonProperty("OsclaimLossInr")
	private String osclaimLossInr;

	@JsonProperty("OsclaimLossIc")
	private String osclaimLossIc;

	@JsonProperty("OsclaimLossRc")
	private String osclaimLossRc;

	@JsonProperty("AmendId")
	private String amendId;

	@JsonProperty("TreatyInsuredName")
	private String treatyInsuredName;
}
