package com.maan.insurance.model.res.reports;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.req.reports.GetPortfolioOutPendingReportReq;

import lombok.Data;

@Data
public class GetTrailBalanceReportRes1 {

	@JsonProperty("Ledger")
	private String ledger;
	@JsonProperty("MainGroup")
	private String mainGroup;
	@JsonProperty("Grouped")
	private String grouped;
	@JsonProperty("Currency")
	private String currency;
	@JsonProperty("Debitoc")
	private String debitoc;
	@JsonProperty("Creditoc")
	private String creditoc;
	@JsonProperty("Netoc")
	private String netoc;
	@JsonProperty("Debitdc")
	private String debitdc;
	@JsonProperty("Creditdc")
	private String creditdc;
	@JsonProperty("Netdc")
	private String netdc;
   	@JsonProperty("PostingDebit")
	private String postingDebit;
   	@JsonProperty("PostingCredit")
	private String postingCredit;
   	@JsonProperty("PostingNet")
	private String postingNet;
	
}
