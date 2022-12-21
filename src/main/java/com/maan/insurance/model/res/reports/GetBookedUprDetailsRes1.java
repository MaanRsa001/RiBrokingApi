package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;

import lombok.Data;

@Data
public class GetBookedUprDetailsRes1 {
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
    @JsonProperty("StartDate")
	private String startDate;
    @JsonProperty("EndDate")
	private String endDate;
    @JsonProperty("DebitTotal")
	private String debitTotal;	
    @JsonProperty("CreditTotal")
   	private String creditTotal;	
    @JsonProperty("BkdUprD")
	private String bkdUprD;
    @JsonProperty("InwrdBkdDefAcqCostBsD")
	private String inwrdBkdDefAcqCostBsD;
    @JsonProperty("PiplinAcqCostD")
	private String piplinAcqCostD;
    @JsonProperty("InwrdPiplinUprBSD")
	private String inwrdPiplinUprBSD;
    @JsonProperty("BkdDefAcqCostC")
   	private String bkdDefAcqCostC;	
    @JsonProperty("InwrdBkdUprBsC")
	private String inwrdBkdUprBsC;
    @JsonProperty("PiplinUprC")
	private String piplinUprC;
    @JsonProperty("InwrdPiplinDefAcqCostBsC")
	private String inwrdPiplinDefAcqCostBsC;
    
}
