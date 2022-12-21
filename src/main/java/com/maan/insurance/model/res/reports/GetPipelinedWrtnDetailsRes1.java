package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;

import lombok.Data;

@Data
public class GetPipelinedWrtnDetailsRes1 {
	@JsonProperty("AccountDate")
	private String accountDate;
    @JsonProperty("SpcName")
	private String spcName;
    @JsonProperty("UwYear")
	private String uwYear;
    @JsonProperty("Currencyname")
	private String currencyname;
    @JsonProperty("ProductName")
	private String productName;
    @JsonProperty("DebitTotal")
	private String debitTotal;	
    @JsonProperty("CreditTotal")
   	private String creditTotal;	
    @JsonProperty("PiplinPremiumC")
	private String piplinPremiumC;
    @JsonProperty("PiplinAcqCostD")
   	private String piplinAcqCostD;
    @JsonProperty("PiplinNetAcD")
	private String piplinNetAcD;
   
    
}
