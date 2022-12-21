package com.maan.insurance.model.req.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetTreatyWithdrawRegisterListReq {
	@JsonProperty("TreatyType")
	private String treatyType;
	@JsonProperty("EndDate")
	private String endDate;
	@JsonProperty("BrokerId")
	private String brokerId;
	@JsonProperty("TreatyMainClass")
	private String treatyMainClass;
	@JsonProperty("CedingId")
	private String cedingId;
	@JsonProperty("UwYear")
	private String uwYear;
}
