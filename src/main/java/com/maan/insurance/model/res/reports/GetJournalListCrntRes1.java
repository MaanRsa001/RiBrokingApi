package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;

import lombok.Data;

@Data
public class GetJournalListCrntRes1 {

	@JsonProperty("Spfcid")
	private String spfcid;
	@JsonProperty("TmasSpfcName")
	private String tmasSpfcName;
	@JsonProperty("ProductId")
	private String productId;
	@JsonProperty("TmasProductName")
	private String tmasProductName;
	@JsonProperty("CurrencyName")
	private String currencyName;
	@JsonProperty("CurrencyId")
	private String currencyId;
	@JsonProperty("UwYear")
	private String uwYear;

}
