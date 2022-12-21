package com.maan.insurance.model.req.DropDown;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.res.premium.ContractidetifierlistRes1;

import lombok.Data;

@Data
public class SetTerritoryCountryListReq {
	@JsonProperty("CountryExclude")
	private List<String> countryExclude;
	
	@JsonProperty("TerritoryId")
	private List<String> territoryId;
	
	@JsonProperty("Type")
	private String type;
	
	@JsonProperty("BranchCode")
	private String branchCode;
}
