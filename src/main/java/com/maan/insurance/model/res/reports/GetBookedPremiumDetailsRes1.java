package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;

import lombok.Data;

@Data
public class GetBookedPremiumDetailsRes1 {
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
    @JsonProperty("GrsPremiumC")
	private String grsPremiumC;
    @JsonProperty("GrsPiplinPremiumD")
	private String grsPiplinPremiumD;
    @JsonProperty("GrsAcqCostD")
	private String grsAcqCostD;
    @JsonProperty("GrsPiplinAcqCostC")
	private String grsPiplinAcqCostC;
    @JsonProperty("PreResRetainD")
   	private String preResRetainD;	
    @JsonProperty("PreResReleaseC")
	private String preResReleaseC;
    @JsonProperty("LossResRetainD")
	private String lossResRetainD;
    @JsonProperty("LossResReleaseC")
	private String lossResReleaseC;
    @JsonProperty("BkdPreInterestC")
	private String bkdPreInterestC;
    
    @JsonProperty("GrsClaimsPaidD")
	private String grsClaimsPaidD;
    @JsonProperty("PiplinNetAcC")
	private String piplinNetAcC;
    @JsonProperty("BusiPartInwrdNetAcD")
   	private String busiPartInwrdNetAcD;

}
