package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;

import lombok.Data;

@Data
public class GetJournelPageRes1 {
	@JsonProperty("SNo")
	private String sNo;
    @JsonProperty("SpcName")
	private String spcName;
    @JsonProperty("UwYear")
	private String uwYear;
    @JsonProperty("Currencyname")
	private String currencyname;
    @JsonProperty("SumofPrm")
	private String sumofPrm;
    @JsonProperty("SumofPrmDC")
	private String sumofPrmDC;
  
}
