package com.maan.insurance.model.res.reports;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.req.reports.InsertMovementReq;

import lombok.Data;

@Data
public class GetViewJurnelAllRes1 {
	@JsonProperty("AccountPeriod")
	private String accountPeriod;
	@JsonProperty("AccountDate")
	private String accountDate;
	@JsonProperty("UwYear")
	private String uwYear;
	@JsonProperty("Spc")
	private String spc;
	@JsonProperty("Currencyname")
	private String currencyname;
	@JsonProperty("OsLossMovement")
	private String osLossMovement;
	@JsonProperty("OsLossMovementUSD")
	private String osLossMovementUSD;
	@JsonProperty("BsMovement")
	private String bsMovement;
	@JsonProperty("BsMovementUSD")
	private String bsMovementUSD;
	@JsonProperty("JournelClaimPaid")
	private String journelClaimPaid;
	@JsonProperty("JournelClaimPaidUSD")
	private String journelClaimPaidUSD;
	@JsonProperty("DebtorsCreditors")
	private String debtorsCreditors;
	@JsonProperty("DebtorsCreditorsUSD")
	private String debtorsCreditorsUSD;
	@JsonProperty("OsLossMovementBs")
	private String osLossMovementBs;
	@JsonProperty("OsLossMovementBsUSD")
	private String osLossMovementBsUSD;
	@JsonProperty("OsLossPl")
	private String osLossPl;
	@JsonProperty("OsLossPlUSD")
	private String osLossPlUSD;
	@JsonProperty("OsLM")
	private String osLM;
	@JsonProperty("BsM")
	private String bsM;
	@JsonProperty("JCP")
	private String jCP;
	@JsonProperty("DC")
	private String dC;
	@JsonProperty("OsLMB")
	private String osLMB;
	@JsonProperty("OsLP")
	private String osLP;
	@JsonProperty("BrokerLedCtlOC")
	private String brokerLedCtlOC;
	@JsonProperty("BrokerLedCtlDC")
	private String brokerLedCtlDC;
	@JsonProperty("TotalCROC")
	private String totalCROC;
	@JsonProperty("TotalDROC")
	private String totalDROC;
	@JsonProperty("TotalCRDC")
	private String totalCRDC;
	@JsonProperty("TotalDRDC")
	private String totalDRDC;
	@JsonProperty("SNo")
	private String sNo;
	
}
