package com.maan.insurance.model.res.propPremium;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.req.propPremium.GetVatInfoReq;

import lombok.Data;

@Data
public class GetRIPremiumListRes1 {
	@JsonProperty("ReInsurerName")
	private String reInsurerName;
	@JsonProperty("BrokerName")
	private String brokerName;
	@JsonProperty("BrokerId")
	private String brokerId;
	@JsonProperty("Brokerage")
	private String brokerage;
	@JsonProperty("SignShared")
	private String signShared;
	@JsonProperty("ReinsurerId")
	private String reinsurerId;
}
