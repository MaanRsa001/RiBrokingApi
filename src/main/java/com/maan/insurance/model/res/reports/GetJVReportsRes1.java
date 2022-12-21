package com.maan.insurance.model.res.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetJVReportsRes1 {

		@JsonProperty("TransactionNo")
		private String transactionNo;
	    @JsonProperty("TransactionDate")
		private String transactionDate;
	    @JsonProperty("Spc")
		private String spc;
	    @JsonProperty("Currency")
		private String currency;
	    @JsonProperty("Ledger")
		private String ledger;
	    @JsonProperty("DebitOc")
		private String debitOc;
	    @JsonProperty("CreditOc")
		private String creditOc;
	    @JsonProperty("DebitDc")
		private String debitDc;
	    @JsonProperty("CreditDc")
		private String creditDc;
	    @JsonProperty("Narration")
		private String narration;
	    @JsonProperty("ReversalTrxnNo")
		private String reversalTrxnNo;
	    @JsonProperty("JournalId")
		private String journalId;	
	
	
}
