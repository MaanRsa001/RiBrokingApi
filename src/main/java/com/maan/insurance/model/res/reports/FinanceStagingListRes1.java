package com.maan.insurance.model.res.reports;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.req.reports.GetViewJurnelAllReq;

import lombok.Data;

@Data
public class FinanceStagingListRes1 {

	@JsonProperty("Reference")
	private String reference;
	@JsonProperty("VoucherSubtype")
	private String voucherSubtype;
	@JsonProperty("Vouchertype")
	private String vouchertype;
	@JsonProperty("Ledger")
	private String ledger;
	@JsonProperty("DC")
	private String dC;
	@JsonProperty("Debitoc")
	private String debitoc;
	@JsonProperty("Creditoc")
	private String creditoc;
	@JsonProperty("PostingDebit")
	private String postingDebit;
	@JsonProperty("PostingCredit")
	private String postingCredit;
	@JsonProperty("ExchangeRate")
	private String exchangeRate;
	@JsonProperty("Currency")
	private String currency;
	@JsonProperty("Narration")
	private String narration;
	@JsonProperty("BaseCurrency")
	private String baseCurrency;
	@JsonProperty("JvDate")
	private String jvDate;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("LogicDate")
	private String logicDate;
	@JsonProperty("TransferType")
	private String transferType;
	@JsonProperty("InstrumentNo")
	private String instrumentNo;
	@JsonProperty("InstrumentDate")
	private String instrumentDate;
	
}
