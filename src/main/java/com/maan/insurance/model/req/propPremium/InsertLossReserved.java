package com.maan.insurance.model.req.propPremium;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class InsertLossReserved {
	@JsonProperty("PRTransNo")
	private String pRTransNo;;
	@JsonProperty("PRAmount")
	private String pRAmount;
	@JsonProperty("PREAmount")
	private String pREAmount;
	
	@JsonProperty("PRERate")
	private String pRERate;
}
