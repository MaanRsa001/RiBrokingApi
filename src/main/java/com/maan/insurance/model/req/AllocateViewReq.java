package com.maan.insurance.model.req;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllocateViewReq {
	
	@JsonProperty("PayRecNo")
	private String payRecNo;
	
	@JsonProperty("SerialNo")
	private String serialNo;
	
	@JsonProperty("Flag")
	private String flag;
	
	@JsonProperty("CedingCo")
	private String cedingCo;
	
	@JsonProperty("AlloccurrencyId")
	private String alloccurrencyId;
	
	@JsonProperty("AllTillDate")
	private String allTillDate;
	
	@JsonProperty("ExchangeRate")
	private String exchangeRate;
	
	@JsonProperty("Status")
	private String status;
	
	@JsonProperty("CurrecncyValue")
	private String currecncyValue;
	
	@JsonProperty("CurrecncyName")
	private String currecncyName;
	
	@JsonProperty("BranchCode")
	private String branchCode;
	
	@JsonProperty("BrokerId")
	private String brokerId;
}

