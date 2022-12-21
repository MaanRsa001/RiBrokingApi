package com.maan.insurance.model.res.propPremium;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetOpenPeriodRes1 {
	@JsonProperty("OpstartDate")
	private String opstartDate;
	@JsonProperty("OpendDate")
	private String opendDate;
}
