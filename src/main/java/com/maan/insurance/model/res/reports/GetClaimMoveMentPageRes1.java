package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetClaimMoveMentPageRes1 {
	@JsonProperty("ContractNo")
	private String contractNo;
    @JsonProperty("SpcName")
	private String spcName;
    @JsonProperty("UwYear")
	private String uwYear;
    @JsonProperty("Currencyname")
	private String currencyname;
    @JsonProperty("ProductName")
	private String productName;
    @JsonProperty("TranNo")
	private String tranNo;
    @JsonProperty("Type")
	private String type;
    @JsonProperty("OsLossupdateOC")
	private String osLossupdateOC;	
    @JsonProperty("OsLossupdateDC")
   	private String osLossupdateDC;	
}
