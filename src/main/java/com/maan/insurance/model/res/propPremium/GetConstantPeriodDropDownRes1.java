package com.maan.insurance.model.res.propPremium;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetConstantPeriodDropDownRes1 {

	@JsonProperty("PremiumType")
	private String premiumType;
	@JsonProperty("DetailName")
	private String detailName;
}
