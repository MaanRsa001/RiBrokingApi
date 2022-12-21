package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;

import lombok.Data;

@Data
public class GetPipelineMovementJvDetailsRes1 {
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
    @JsonProperty("InwrdPiplinUnearnPremiumD")
	private String inwrdPiplinUnearnPremiumD;
    @JsonProperty("InwrdPiplinDefAcqCostBsD")
	private String inwrdPiplinDefAcqCostBsD;
    @JsonProperty("InwrdPiplinDefAcqCostC")
	private String inwrdPiplinDefAcqCostC;
    @JsonProperty("InwrdPiplinUprBsC")
	private String inwrdPiplinUprBsC;
    @JsonProperty("DebitTotal")
	private String debitTotal;	
    @JsonProperty("CreditTotal")
   	private String creditTotal;	
}
