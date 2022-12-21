package com.maan.insurance.model.req.DropDown;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetExcludeCountryReq {
	@JsonProperty("CountryExclude")
	private List<String> countryExclude;
	
	@JsonProperty("TerritoryId")
	private List<String> territoryId;
	
	@JsonProperty("CountryMode")
	private String countryMode;
	
	@JsonProperty("BranchCode")
	private String branchCode;
}
